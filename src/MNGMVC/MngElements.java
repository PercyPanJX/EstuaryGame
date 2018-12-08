package MNGMVC;

import java.io.Serializable;

public class MngElements implements Serializable {
	//elements the view needed from the model		

		//game state
		public ManageGameState GameState;
		
		//time for the game
		public double EstuaryTime;
		
		//Tool States mouse
		public Tool tool;
		
		
		//fish health
		public double FishHealthRate = 0.1;
		
		//plant health
		public double PlantHealthRate = 0.1;
		
		
		public double DarkCoverTimer;

		public int trashNum, invasiveNum, spotsNum;
		
		public boolean isGameOver;
		
		
		MngElements(ManageGameState GS, double ET,
				Tool T, double FH, double PH,
				int TN, int IN, int SN,
				double DCT, boolean GO){
			GameState = GS;
			EstuaryTime = ET;
			tool = T;
			FishHealthRate = FH;
			PlantHealthRate = PH;
			trashNum = TN;
			invasiveNum = IN;
			spotsNum = SN;
			DarkCoverTimer = DCT;
			isGameOver = GO;
		}
	}