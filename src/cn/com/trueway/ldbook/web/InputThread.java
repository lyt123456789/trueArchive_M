/**
 * ��ȡ�Ķ���
 */
package cn.com.trueway.ldbook.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class InputThread extends Thread {

	private BufferedInputStream _breader = null;
	private BufferedOutputStream _bwriter = null;
	private boolean _isConnected = true;
	private IReceiveCallback listener;
	// private WakeLock mWakeLock;
	private BotConnection connection;
	public static final int MAX_LINE_LENGTH = 512;

	public InputThread(IReceiveCallback listener, BotConnection connection,
			BufferedInputStream breader, BufferedOutputStream bwriter) {
		this.listener = listener;
		this.connection = connection;
		// this.mWakeLock=clientlock;
		_breader = breader;
		_bwriter = bwriter;
	}

	void sendRawLine(byte[] bytes) {
		OutputThread.sendRawLine(_bwriter, bytes);
	}

	/**
	 * Returns true if this InputThread is connected to an IRC server. The
	 * result of this method should only act as a rough guide, as the result may
	 * not be valid by the time you act upon it.
	 * 
	 * @return True if still connected.
	 */
	boolean isConnected() {
		return _isConnected;
	}

	private byte[] parse(byte[] target) {
		if (target.length > 3) {
			byte[] cc = new byte[4];
			ByteBuffer byteBuffer = ByteBuffer.wrap(target);
			byteBuffer.get(cc);
			int length = BaseStream.Change2Int(cc);
			if (length <= target.length - 4) {
				byte[] message = new byte[length];
				byteBuffer.get(message);
				//此处解密
				//byte[] bytes = SM4Tool.decode(message, message.length);
				ReceiveCallback.Parse(listener, message);
				// ����ȥ����,ȥ��ǰ���ж������
				byte[] other = new byte[target.length - 4 - length];
				byteBuffer.get(other);
				return parse(other);
			} else {
				// �ȴ���һ��
				return target;
			}
		} else {
			// �ȴ���һ��
			return target;
		}
	}

	private byte[] lastByte;
	private byte[] bytes = new byte[1024 * 4];

	public void run() {
		try {
			connection.getSocketListener().connectSuccess();
			boolean running = true;
			while (running) {
				try {
					while (true) {
						int end = _breader.read(bytes);
						if (end == -1) {
							running = false;
							break;
						}
						byte[] target = new byte[end];
						System.arraycopy(bytes, 0, target, 0, end);
						if (lastByte != null && lastByte.length != 0) {
							// ��ȥ�ϲ����
							ByteBuffer bb = ByteBuffer.allocate(lastByte.length
									+ target.length);
							bb.put(lastByte);
							bb.put(target);
							lastByte = parse(bb.array());
						} else {
							lastByte = parse(target);
						}
					}
				} catch (Exception iioe) {
					running = false;
					iioe.printStackTrace();
					System.out.println("socket������Ϣ���: " + iioe.getMessage());
					connection.close();
					// ���͹㲥��l
					connection.reconnect();
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		connection.close();
	}

	public void close() throws IOException {
		_isConnected = false;
		_breader.close();
		_bwriter.close();
		interrupt();
	}
}
