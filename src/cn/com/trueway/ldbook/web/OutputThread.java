/**
 * 
 */
package cn.com.trueway.ldbook.web;

import java.io.BufferedOutputStream;
import java.nio.ByteBuffer;

import cn.com.trueway.ldbook.tools.SM4Tool;

public class OutputThread extends Thread {

	private BotConnection connection;

	OutputThread(Queue outQueue, BotConnection connection) {
		_outQueue = outQueue;
		this.connection = connection;
		this.setName(this.getClass() + "-Thread");
	}

	static void sendRawLine(BufferedOutputStream bwriter, byte[] bytes) {
		// if (line.length() > bot.getMaxLineLength() - 2) {
		// line = line.substring(0, bot.getMaxLineLength() - 2);
		// }
		synchronized (bwriter) {
			try {
				//信息加密
				//byte[] bytes = SM4Tool.encode(byt, byt.length);
				
				byte[] lengthByte = BaseStream.Change2Bytes(bytes.length, 4);
				ByteBuffer byteBuffer = ByteBuffer.allocate(lengthByte.length
						+ bytes.length);
				byteBuffer.put(lengthByte);
				byteBuffer.put(bytes);
				bwriter.write(byteBuffer.array());
				bwriter.flush();
			} catch (Exception e) {
				// Silent response - just lose the line.
				e.printStackTrace();
			}
		}
	}

	boolean running = true;

	public void run() {
		try {

			while (running) {
				// Small delay to prevent spamming of the channel
				// Thread.sleep(_bot.getMessageDelay());

				byte[] line = (byte[]) _outQueue.next();
				if (line != null) {
					connection.sendRawLine(line);
				} else {
					running = false;
				}
			}
		} catch (Exception e) {
		}
	}

	private Queue _outQueue = null;

	public void close() {
		running = false;
		_outQueue.add("");
		interrupt();
	}
}
