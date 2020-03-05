package cn.com.trueway.base.io.socket;


import org.json.JSONObject;

import cn.com.trueway.sys.util.SystemParamConfigUtil;


public class IoSocketForIos implements IOCallback{
	
	private static SocketIO socket;
	
	public static void main(String[] args) {
		try {
			new IoSocketForIos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IoSocketForIos() throws Exception {
		socket = new SocketIO();
		socket.connect(SystemParamConfigUtil.getParamValueByParam("sockIp"), this);
	}


	public void on(String event, IOAcknowledge ack, Object... args) {
		// TODO Auto-generated method stub
		
	}

	public void onConnect() {
		// TODO Auto-generated method stub
		
	}

	public void onDisconnect() {
		// TODO Auto-generated method stub
	}

	public void onError(SocketIOException socketIOException) {
		// TODO Auto-generated method stub
		socket.disconnect();
	}

	public void onMessage(String data, IOAcknowledge ack) {
		// TODO Auto-generated method stub
	}

	public void onMessage(JSONObject json, IOAcknowledge ack) {
		// TODO Auto-generated method stub
		
	}
	public static void send(String str){
		if(socket==null || !socket.isConnected()){
			try {
				IoSocketForIos s = new IoSocketForIos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		socket.emit("service_ios", str);
	}
	
}
