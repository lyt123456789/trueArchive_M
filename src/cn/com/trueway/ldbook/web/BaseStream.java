package cn.com.trueway.ldbook.web;

public class BaseStream
{
	protected static byte[] Change2Bytes(int v, int n)
	{
		byte[] t = new byte[n];
		for (int i = 0; i < n; i++)
		{
			t[i] = (byte) (v >> (8 * i));
		}
		return t;
	}

	protected static int Change2Int(byte[] v)
	{
		int t = 0;
		for (int i = 0; i < v.length; i++)
		{
			int number = v[i] >= 0 ? v[i] : (256 + v[i]);
			t += number << (8 * i);
		}
		return t;
	}

	protected static int Change2Long(byte[] v)
	{
		int t = 0;
		for (int i = 0; i < v.length; i++)
		{
			int number = v[i] >= 0 ? v[i] : (256 + v[i]);
			t += number << (8 * i);
		}
		return t;
	}

	public byte[] GetByteStream()
	{
		return _Stream;
	}

	protected byte[] _Stream = new byte[0];
	protected int _nPointer = 0;
	protected static int MAX_STRING_SIZE = 65536;
}
