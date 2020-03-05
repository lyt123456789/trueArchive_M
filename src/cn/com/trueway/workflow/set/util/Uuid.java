package cn.com.trueway.workflow.set.util;

import java.util.UUID;

public class Uuid {
	public static void main(String[] args)
	  {
	    Uuid uuid = new Uuid();
	    System.out.println(uuid.get32Uuid());
	  }
	  public String get32Uuid() {
	    return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	  }
}
