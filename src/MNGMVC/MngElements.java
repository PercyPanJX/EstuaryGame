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
		public double FishHealthRate;
		
		//plant health
		public double PlantHealthRate;
		
		
		public double DarkCoverTimer;

		public int trashNum, invasiveNum, spotsNum;
		
		public boolean isGameOver;
		
		
		/**
		 * management elements saving
		 * @param GS game state
		 * @param ET estuary time
		 * @param T tool
		 * @param FH fishhealth rate
		 * @param PH plant health rate
		 * @param TN trash number
		 * @param IN invasive number
		 * @param SN spots number
		 * @param DCT dark cover timer 
		 * @param GO is game over or not
		 */
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