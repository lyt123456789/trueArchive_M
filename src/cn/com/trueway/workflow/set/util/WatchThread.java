package cn.com.trueway.workflow.set.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WatchThread extends Thread {
	InputStream is;
	String type;

	public WatchThread(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		InputStreamReader isr = null; 
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(type + ">" + line);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}finally{
			try {
				isr.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}

/*public class GoodWindowsExec {
	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("USAGE: java GoodWindowsExec <cmd>");
			System.exit(1);
		}

		try {
			String osName = System.getProperty("os.name");
			String[] cmd = new String[3];
			if (osName.equals("Windows NT")) {
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = args[0];
			} else if (osName.equals("Windows 95")) {
				cmd[0] = "command.com";
				cmd[1] = "/C";
				cmd[2] = args[0];
			}

			Runtime rt = Runtime.getRuntime();
			System.out.println("Execing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
			Process proc = rt.exec(cmd);

			WatchThread errorGobbler = new WatchThread(proc.getErrorStream(), "ERROR");

			WatchThread outputGobbler = new WatchThread(proc.getInputStream(), "OUTPUT");

			errorGobbler.start();
			outputGobbler.start();

			int exitVal = proc.waitFor();
			System.out.println("ExitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
*/