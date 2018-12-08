package RESEARCHMVC;

import java.util.Random;
import SUPERMVC.Model;

/**
 * research game model for the game logic
 */
public class ReaModel extends Model{
	Random rand = new Random();
	
	private double TICK = 0.04;
	
	//time for the game
	public double gameTime;
	
	//game state and frame size
	public ResearchGameState GameState;
	
	public int score;
	
	sample[] sampleList = new sample[10];
	
	private int rolly, hooky, roll;

	public int barPercent;

	private int sampleLayer;

	private int hookLayer;

	private int hookPosition;
	
	private double hookRate;
	
	public boolean isClicked = false;
	
	int infoNum = 0;
	
	public double xRate;

	public double yRate;
	
	int tutorialTime;
	
	
	
	public sample sample1 = new sample(1,rand.nextInt(100)+1,"a",false);
	public sample sample2 = new sample(1,rand.nextInt(100)+1,"b",false);
	public sample sample3 = new sample(1,rand.nextInt(100)+1,"c",false);
	public sample sample4 = new sample(1,rand.nextInt(100)+1,"d",false);
	public sample sample5 = new sample(1,rand.nextInt(100)+1,"e",false);
	public sample sample6 = new sample(1,rand.nextInt(100)+1,"f",false);
	public sample sample7 = new sample(1,rand.nextInt(100)+1,"g",false);
	public sample sample8 = new sample(1,rand.nextInt(100)+1,"h",false);
	public sample sample9 = new sample(1,rand.nextInt(100)+1,"i",false);
	public sample sample10 = new sample(1,rand.nextInt(100)+1,"g",false);

	
	
	//initiate the game state and the frame size
	/**
	 * Constructor to initialize the attributes
	 */
	public ReaModel(){	

		GameState = ResearchGameState.WAITING;
		
	}
	
	//start the game
	/**
	 * set the initial state of the game and give the initial game time
	 */
	public void startTheGame() {
		tutorialTime = 0;
		gameTime = 0;
		score = 0;
		sampleList[0] = sample1;
		sampleList[1] = sample2;
		sampleList[2] = sample3;
		sampleList[3] = sample4;
		sampleList[4] = sample5;
		sampleList[5] = sample6;
		sampleList[6] = sample7;
		sampleList[7] = sample8;
		sampleList[8] = sample9;
		sampleList[9] = sample10;
		rolly = 0;
		hooky = 0;
		hookRate = 1;
		roll = 0;
		barPercent = 0;
		sampleLayer = 0;
		hookLayer = 0;
		hookPosition = 0;
		yRate = 1;
		xRate = 1;
		gameTime = 45;
		GameState = ResearchGameState.COVERTIME;
	}
	
	//change to the next state of the game
	/**
	 * go to the next state
	 */
	public void nextState() {
		switch(GameState) {
			case COVERTIME:
				GameState = ResearchGameState.CONSTRUCTION;
				break;
			case CONSTRUCTION:
				GameState = ResearchGameState.TUTORIAL;
				break;
			case TUTORIAL:
				GameState = ResearchGameState.RULETIME;
				break;
			case RULETIME:
				GameState = ResearchGameState.GAMETIME;
				break;
			case GAMETIME:
				GameState = ResearchGameState.SCORETIME;
				break;
			case SCORETIME:
				GameState = ResearchGameState.GAMESTOP;
				break;
			case INFOTIME:
				GameState = ResearchGameState.SCORETIME;
				break;
		}
	}
	
	// when mouse is clicking 
	/**
	 * things to do while player is clicking the mouse during the game time 
	 */
	public void mouseClicking() {
		if (GameState == ResearchGameState.GAMETIME) {
			isClicked = true;
		}
	}
	
	//update the game
	/**
	 * update the model according to the different game state
	 */
	public void updateModel() {
		switch(GameState) {
			case COVERTIME:
				break;
			case CONSTRUCTION:
				break;
			case TUTORIAL:
				updateTutorial();
				break;
			case RULETIME:
				break;
			case GAMETIME:
				updateGame();
				break;
			case SCORETIME:
				break;
			case INFOTIME:
				break;
		}
	}
	
	public void updateTutorial() {
		tutorialTime = (tutorialTime + 1) % 70;
	}
	
	
	/**
	 * update the game during the game time
	 */
	public void updateGame() {
		if (isClicked) {
			isClicked = false;
			hookRate += ((double) 16 / (double)43);
			hooky = 16;
			
			if (rolly < 600) {
				roll = 8;
			} else {
				roll = 0;
			}
			rolly += hooky;
		} else {
			hooky = -2;
			
			if (hookRate > 1) hookRate -= ((double) 2 / (double)43);
			if (rolly > 0) {
				roll = -1;
			} else {
				roll = 0;
			}
			if(rolly > 0) {
				rolly += hooky;
			}
		}
		 
		gameTime -= TICK;
		// self back
		sampleLayer = getSampleLayer(sampleList[score].depth);
		hookLayer = getHookLayer(rolly);
		
		// update progress bar percent
		if (barPercent >= 100) {
			score++;
			barPercent = 0;
			sampleLayer = 0;
		} else if (hookLayer == sampleLayer){
			barPercent += 1;
		}
		
		// when time is up
		if (gameTime <= 0 ) {
			nextState();
		}
		
	}
	
	/**
	 * get the current layer of hook during the game time
	 * @param rollNum the depth of the hook in pixel
	 * @return an integer showing the layer at which the hook is 
	 */
	private int getHookLayer(int rollNum) {
		if (rollNum == 0) return 0;
		double num = (double) rollNum / (double) (80 * (1 + ((yRate - 1) /(double) 2)));
		return (int) num + 1;
	}
	

	/**
	 * get the layer of the sample
	 * @param number an integer showing the depth of the sample 
	 * @return an integer representing the layer according to the depth of the sample
	 */
	public int getSampleLayer(int number) {
		int layerNum = 0;
		if (number > 80) {
			layerNum = 5;
		} else if (number > 50) {
			layerNum = 4;
		} else if (number > 30) {
			layerNum = 3;
		} else if (number > 10){
			layerNum = 2;
		} else {
			layerNum = 1;
		}
		return layerNum;
	}
	
	/**
	 * get all elements that are needed to give to View class
	 * @return all the elements are needed
	 */
	public ReaElements getElements() {
		return new ReaElements(GameState, score, (int)gameTime, sampleList,rolly, hooky, hookRate, roll,
				barPercent, infoNum, tutorialTime);	
	}

	/**
	 * during the information time, we need to know which sample's information is needed
	 * @param i the number representing a specific sample
	 */
	public void showInfo(int i) {
		GameState = ResearchGameState.INFOTIME;
		infoNum = i;
	}

	/**
	 * get scale rate for the whole graph from View
	 * @param getxRate scale rate in width
	 * @param getyRate scale rate in height
	 */
	public void scale(double getxRate, double getyRate) {
		xRate = getxRate;
		yRate = getyRate;
	}
	
	/**
	 * load the game data
	 * @param reaData the saved research game data 
	 */
	public void loadGame(ReaElements reaData) {
		this.GameState = reaData.gameState;
		this.score = reaData.score;
		this.gameTime = reaData.time;
		this.sampleList = reaData.sampleList;
		this.rolly = reaData.rolly;
		this.hooky = reaData.hooky;
		this.hookRate = reaData.rollRate;
		this.roll = reaData.roll;
		this.barPercent = reaData.barPercent;
		this.infoNum = reaData.infoNum;
		this.tutorialTime = reaData.tutorialTime;
		
	}

	

}
