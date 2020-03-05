package cn.com.trueway.ldbook.web;

public class InStream extends BaseStream
{
	private void _Write(byte[] v)
	{
		byte[] temp = new byte[_Stream.length + v.length];
		System.arraycopy(_Stream, 0, temp, 0, _Stream.length);
		System.arraycopy(v, 0, temp, _Stream.length, v.length);
		_Stream = temp;
	}

	public void Write(int v)
	{
		_Write(Change2Bytes(v, 4));
	}

	public void Write(long v)
	{
		_Write(Change2Bytes(0, 8));
	}

	public void Write(short v)
	{
		_Write(Change2Bytes(v, 2));
	}

	public void Write(byte v)
	{
		_Write(Change2Bytes(v, 1));
	}

	public void Write(boolean v)
	{
		int t = v ? 1 : 0;
		_Write(Change2Bytes(t, 1));
	}

	public void Write(String v)
	{
		try
		{
			byte[] t = v.getBytes("UTF-8");
			Write(t.length);
			_Write(t);
		}
		catch (Exception e)
		{
			System.out.println("tmp exception");
		}
	}

	public void Write(byte[] v)
	{
		short l = (short) v.length;
		_Write(Change2Bytes(l, 2));
		_Write(v);
	}
}
