package cn.com.trueway.ldbook.web;

public class OutStream extends BaseStream
{
	public OutStream(byte[] v)
	{
		_Stream = v;
	}

	private void _Read(byte[] arr) throws Exception
	{
		if (arr.length > _Stream.length)
		{
			throw new Exception("Exceeds the size of the buf.");
		}
		System.arraycopy(_Stream, _nPointer, arr, 0, arr.length);
		_nPointer += arr.length;
	}

	public boolean ReadBool()
	{
		byte v = ReadByte();
		return v > 0;
	}

	public long ReadLong()
	{
		byte[] bytes = new byte[8];
		try
		{
			_Read(bytes);
		}
		catch (Exception e)
		{
		}
		return Change2Int(bytes);
	}

	public byte ReadByte()
	{
		byte[] bytes = new byte[1];
		try
		{
			_Read(bytes);
		}
		catch (Exception e)
		{
		}
		return (byte) Change2Int(bytes);
	}

	public byte[] ReadBytes()
	{
		short l = ReadShort();
		byte[] bytes = new byte[l];
		try
		{
			_Read(bytes);
		}
		catch (Exception e)
		{
		}
		return bytes;
	}

	public short ReadShort()
	{
		byte[] bytes = new byte[2];
		try
		{
			_Read(bytes);
		}
		catch (Exception e)
		{
		}
		return (short) Change2Int(bytes);
	}

	public int ReadInt()
	{
		byte[] bytes = new byte[4];
		try
		{
			_Read(bytes);
		}
		catch (Exception e)
		{
		}
		return Change2Int(bytes);
	}

	public String ReadString()
	{
		int cnt = ReadInt();
		byte[] bytes = new byte[cnt];
		try
		{
			_Read(bytes);
			return new String(bytes, "UTF-8");
		}
		catch (Exception e)
		{
		}
		return "";
	}
}
