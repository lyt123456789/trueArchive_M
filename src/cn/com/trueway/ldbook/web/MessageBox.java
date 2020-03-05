package cn.com.trueway.ldbook.web;

public class MessageBox
{
	private String message;
	private String ip;
	private int port;

	public MessageBox(String message, String address, int port)
	{
		this.message = message;
		this.ip = address;
		this.port = port;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

}
