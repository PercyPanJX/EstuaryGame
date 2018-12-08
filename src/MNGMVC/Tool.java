package MNGMVC;

import java.io.Serializable;

public enum Tool implements Serializable {
	
	MOUSE("mouse"),
	SPRAY("spray"),
	REPORT("report"),
	TRASH("trash");

	private String name = null;
	
	private Tool(String s){
		name = s;
	}
	public String getName() {
		return name;
	}


}
