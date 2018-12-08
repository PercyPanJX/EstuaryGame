package MNGMVC;

import java.io.Serializable;

public enum ManageGameState implements Serializable{
	
	WAITING("waitingtime"),
	
	COVERTIME("covertime"),
	
	RULETIME("ruletime"),
	
	RULETIME2("ruletime2"),
	
	RULETIME3("ruletime3"),
	
	BEGINNING("beginning"),
	
	GAMETIME("gametime"),
	
	ENDTIME("endtime"),
	
	SCORETIME("scoretime"),
	
	GAMESTOP("stop");
	
	
	
	private String name = null;
	
	private ManageGameState(String s){
		name = s;
	}
	public String getName() {
		return name;
	}
}
