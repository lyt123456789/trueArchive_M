package cn.com.trueway.ldbook.service;

public interface SocketListener
{

	public void finishConnect();

	public void reConnect();

	public void disConnectIM();
	
	public void connectSuccess();
}
