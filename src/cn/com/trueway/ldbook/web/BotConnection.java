package cn.com.trueway.ldbook.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import cn.com.trueway.ldbook.web.IReceiveCallback;
import cn.com.trueway.ldbook.service.SocketListener;

public class BotConnection
{
	private InputThread _inputThread = null;
	private OutputThread _outputThread = null;
	private Queue _outQueue = null;
	private SocketListener socketListener;
	private static Socket _socket;
	
	public SocketListener getSocketListener() {
		return socketListener;
	}

	public synchronized void connect(String hostname, int port,IReceiveCallback receiveCallback, SocketListener socketListener)
	{
		this.socketListener = socketListener;
		connect(hostname, port, receiveCallback);
	}

	public synchronized void connect(String hostname, int port, IReceiveCallback receiveCallback)
	{
		if (isConnected())
		{
			return;
		}
		if(_socket==null){
			_socket = new Socket();
		}
		// _socket = new Socket();
		try
		{
			closeSocket();
			_socket = new Socket();
			_socket.connect(new InetSocketAddress(hostname, port), 10 * 1000);
			System.out.println("--------------------ip:"+_socket.getLocalAddress().toString()+"-----------------------");
			BufferedInputStream inputStreamReader = null;
			BufferedOutputStream outputStreamWriter = null;
			inputStreamReader = new BufferedInputStream(_socket.getInputStream());
			outputStreamWriter = new BufferedOutputStream(_socket.getOutputStream());
			_inputThread = new InputThread(receiveCallback, this, inputStreamReader, outputStreamWriter);
			_inputThread.start();
			_outQueue = new Queue();
			_outputThread = new OutputThread(_outQueue, this);
			_outputThread.start();
		}
		catch (UnknownHostException e2)
		{
			e2.printStackTrace();
			System.out.println("--------------------socket初始化失败-----------------------");
			socketListener.disConnectIM();
		}
		catch (IOException e2){
			e2.printStackTrace();
			System.out.println("--------------------socket初始化失败-----------------------");
			socketListener.disConnectIM();

		}
		finally
		{
			socketListener.finishConnect();
		}
	}

	public synchronized boolean isConnected()
	{
		return _inputThread != null && _inputThread.isConnected();
	}

	public void sendRawLine(byte[] bytes)
	{
		_inputThread.sendRawLine(bytes);
	}

	public final synchronized void sendMessage(byte[] line)
	{
		if (isConnected())
		{
			if (line != null)
			{
				_outQueue.add(line);
			}
		}
	}

	public void close()
	{
		try
		{
			if (socketListener != null)
			{
				socketListener.finishConnect();
			}
			if (_inputThread != null)
			{
				_inputThread.close();
				_inputThread = null;
			}
			if (_outputThread != null)
			{
				_outputThread.close();
				_outputThread = null;
			}
			_socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void closeSocket()
	{
		try
		{
			if (socketListener != null)
			{
				socketListener.finishConnect();
			}
			if (_inputThread != null)
			{
				_inputThread.close();
				_inputThread = null;
			}
			if (_outputThread != null)
			{
				_outputThread.close();
				_outputThread = null;
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public void reconnect()
	{
		socketListener.reConnect();
	}
}
