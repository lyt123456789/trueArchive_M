package cn.com.trueway.ldbook.tools;

public class SM4Tool {
    static {
        System.loadLibrary("sm4");
    }

    public native static byte[] encode(byte[] data,int length);

    public native static byte[] decode(byte[] data,int length);

}
