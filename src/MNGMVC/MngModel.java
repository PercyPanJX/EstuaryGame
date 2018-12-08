package MNGMVC;



import java.util.Random;

import javax.swing.Timer;

import EDUMVC.EduElements;
import SUPERMVC.Model;

/**
 * management model
 * @author percypan
 *
 */
public class MngModel extends Model{
	
	//Dark Cover starting timer
	private double DarkCoverTimer = 3.00;
	
	//Reduces timer by this amount
	private double Tick = 0.01;
	
	//game timer
	private double EstuaryTime;
	
	//game state and frame size
	ManageGameState GameState;
	
	//Used to generate new visible hazard 
	double EachSecond;
	
	//number of current visible hazards
	private int trashNum, invasiveNum, spotsNum;

	//max number possible for each hazard
	private int trashNumMax, invasiveNumMax, spotsNumMax;
	
	//enumerated type 
	private Tool tool;
	
	//this is the rate each hazard reduces the hp bar
	private double rateTrash, rateSpot, rateInv;
	
	//rate the bars reduce 
	private double fishHPBarRate = 0.1;
	private double plantHPBarRate = 0.1;	
	
	//conditional to control game over
	boolean isGameOver = false;
	
	//initiate the game state and the frame size
	public MngModel(){	
		GameState = ManageGameState.WAITING;		
	}
	
	/**
	 * Begins the game
	 */
	public void startTheGame() {
		GameState = ManageGameState.COVERTIME;
		
		EstuaryTime = 31;
		
		trashNum = 4; // <=10
		invasiveNum = 2; // <=6
		spotsNum = 1; // <=4
		
		DarkCoverTimer = 3.5;
		
		EachSecond = 0;
		
		trashNumMax = 10;
		invasiveNumMax =6;
		spotsNumMax=4;
		
		rateTrash = (double) ((double)1/(double)(trashNumMax + spotsNumMax));
		rateSpot = rateTrash;
		rateInv =(double) ((double)1/(double)(invasiveNumMax)); 
		
		isGameOver = false;
	}
	

	/**
	 * update the game according to the current state
	 */
	public void updateModel() {
		switch(GameState) {
		case BEGINNING:
			updateDarkCoverTimer();
			break;
		case GAMETIME:
			updateGameTime();
			updateBars();
			break;
		case ENDTIME:
			updateDarkCoverTimer();
			break;
		case SCORETIME:
			break;
		default:
			break;
		}
	}
	
	/**
	 * 
	 * switches to the next state of the game
	 */
	public void nextState() {
		
			switch(GameState) {
			case COVERTIME:				
				GameState = ManageGameState.RULETIME;
				break;
			case RULETIME:				
				GameState = ManageGameState.RULETIME2;
				break;
			case RULETIME2:				
				GameState = ManageGameState.BEGINNING;
				break;
			case BEGINNING:
				GameState = ManageGameState.GAMETIME;
				break;
			case GAMETIME:
				GameState = ManageGameState.ENDTIME;
				break;
			case ENDTIME:
				GameState = ManageGameState.SCORETIME;
				break;
			case SCORETIME:
				GameState = ManageGameState.GAMESTOP;
				break;
			default:
				break;
			}
	}
	
	/**
	 * update the dark cover timer.
	 */
	public void updateDarkCoverTimer() {
		DarkCoverTimer -= Tick;
		if(DarkCoverTimer <=0 ) {
			nextState();
			DarkCoverTimer = 0.5;
		}
	}
	
	private void updateGameTime() {
		
		EstuaryTime -= Tick;
		if(EstuaryTime <= 0) {
			nextState();
			return;
		}
		
		if(EachSecond < 1.0) {
			EachSecond += Tick;
		}else {
			EachSecond = 0;

			if( trashNum >= 10 && invasiveNum >= 6 && spotsNum >= 4) {
				return;
			}
			
			Random r = new Random();
			
			int pick = r.nextInt(3);
				
			if(pick == 0) {
				if(trashNum >= 10) pick = 1;
				
			}	
			if(pick == 1) {
				if(invasiveNum >= 6) pick = 2;
			}
			if(pick == 2) {
				if(spotsNum >= 4 && trashNum < 10) pick = 0;
				else if(spotsNum >= 4 && invasiveNum < 6) pick =1;
			}
		
			switch(pick) {
			case 0:
				trashNum += 1;
				break;
			case 1:
				invasiveNum += 1;
				break;
			case 2:
				spotsNum += 1;
				break;
			}
			
		}
		
		
	}
	
	/**
	 * Health bars are altered based on visible
	 * amount of each hazard
	 */
	private void updateBars() {
		fishHPBarRate = 1 - (trashNum * rateTrash + spotsNum*rateSpot);
		plantHPBarRate =  1 - rateInv * invasiveNum;
		if(fishHPBarRate <= 0.1) fishHPBarRate = 0.1;
		if(plantHPBarRate <= 0.1) fishHPBarRate = 0.1;
		
		gameOver();
	}
	
	/**
	 * tool to determine the logic that should follow
	 * @param t the tool
	 */
	public void chooseTool(Tool t) {
		tool = t;
	}
	
	/**
	 * sets the conditions for the tool selection 
	 * @param toolShouldBe sets the tool to using the tool enumerated type
	 */
	public void checkSelection(Tool toolShouldBe) {
		if(tool == Tool.TRASH && toolShouldBe == Tool.TRASH && trashNum > 0) {
				trashNum -=1;
		}else if(tool == Tool.SPRAY && toolShouldBe == Tool.SPRAY && invasiveNum > 0) {
			invasiveNum -=1;
		}else if(tool == Tool.REPORT && toolShouldBe == Tool.REPORT && spotsNum > 0) {
			spotsNum -=1;
		}
		
	}
	/**
	 * sets condition for a game over
	 */
	public void gameOver() {
		if (fishHPBarRate <= 0.1) {
			isGameOver = true;
			nextState();
		}
		else if (plantHPBarRate <= 0.1) {
			isGameOver = true;
			nextState();
		}
	}
	/**
	 * allows player to load their game from previous save
	 * @param mngData stores all the elements of the game 
	 */
	public void loadGame(MngElements mngData) {

		//game state
		this.GameState = mngData.GameState;
		
		//time for game
		this.EstuaryTime = mngData.EstuaryTime;
		
		//tool used
		this.tool = mngData.tool;
		
		//fish hp
		this.fishHPBarRate = mngData.FishHealthRate;
		
		//plant hp
		this.plantHPBarRate = mngData.PlantHealthRate;
		
		//amount of trash visible
		this.trashNum = mngData.trashNum;
		
		//amount of invasive plants visible
		this.invasiveNum = mngData.invasiveNum;
		
		
		//amount of spots visible
		this.spotsNum = mngData.spotsNum;
		
		//is the game over or not
		this.isGameOver = mngData.isGameOver;
		
		//dark cover timer
		this.DarkCoverTimer =  mngData.DarkCoverTimer;
		
	
	}
	

	


	
	/**
	 *  this is the necessary information the view needs to get through controller
	 * @return the elements needed
	 */
	public MngElements getElements() {
		return new MngElements(GameState, EstuaryTime, 
				tool, fishHPBarRate, plantHPBarRate,
				trashNum,
				invasiveNum,
				spotsNum,
				DarkCoverTimer, isGameOver);	
		
	}

}