package COVERMVC;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import EDUMVC.EduController;
import EDUMVC.EducationalGameState;
import SUPERMVC.Controller;
import WholeGameControl.GamePicked;
import RESEARCHMVC.ReaController;
import RESEARCHMVC.ResearchGameState;
import MNGMVC.*;
/**
 * the cover controller for choosing the game
 * @author percypan
 *
 */
public class CoverController extends Controller{
	
	private CoverView Coverview;

	EduController eduGame;
	ReaController resGame;
	MngController mngGame;
	
	Timer t;
		
	//picked the game
	private GamePicked PickGame;

	/**
	 * Cover Controller constructor
	 * Cover controller control the game picking for all games
	 * 
	 */
	public CoverController() {

		
		PickGame = GamePicked.COVER;
		
		Coverview = new CoverView();
		
		addButtonListeners();
		
		t = new Timer(40, gameAction);
		t.start();
	}
	
	//action for the game
	Action gameAction = new AbstractAction(){
		public void actionPerformed(ActionEvent e){
			//once the educational game stop, show the cover
			if(PickGame == GamePicked.COVER ) {
				show();
			}else if(eduGame != null 
					&& eduGame.getGameState() == EducationalGameState.GAMESTOP
					&& PickGame == GamePicked.EDUCATIONALGAME) {
				eduGame.stopTheGame();
				PickGame = GamePicked.COVER;
				
			}else if(resGame != null 
					&& resGame.getGameState() == ResearchGameState.GAMESTOP
					&& PickGame == GamePicked.RESEARCHGAME) {
				resGame.stopTheGame();
				PickGame = GamePicked.COVER;
			}else if(mngGame != null 
					&& mngGame.getGameState() == ManageGameState.GAMESTOP
					&& PickGame == GamePicked.MANAGEGAME) {
				mngGame.stopTheGame();
				PickGame = GamePicked.COVER;
			}
		}
	};

	
	/**
	 * Add all button listeners,
	 * including three games picking buttons
	 */
	public void addButtonListeners() {
		
		//educational game button
		ActionListener button4Game1 = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				startEduGame();
				
			}
		};
		
		//research game button
		ActionListener button4Game2 = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				startReaGame();
			}
		};
		
		//management game button
		ActionListener button4Game3 = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				startMngGame();
			}
		};
		
		//voice button
		ActionListener voiceButton = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				Coverview.clickVoice();
			}
		};
		
		//voice button
		ActionListener loadButton = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					readTheGameState();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		//set the buttons
		Coverview.setButtonListener(button4Game1,button4Game2, button4Game3, voiceButton, loadButton);
		
	}
	
	/**
	 * Read the game state
	 * @throws IOException if there is no gile 
	 * @throws ClassNotFoundException if the class is not found
	 */
	public void readTheGameState() throws IOException, ClassNotFoundException{
		try{
			GameData storedGame = null;
			FileInputStream fileIn = new FileInputStream("GameData.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			storedGame = (GameData) in.readObject();
			
			switch(storedGame.gamePicked){
			case EDUCATIONALGAME:
				startEduGame();
				eduGame.loadGame(storedGame.eduGameData);
				break;
			case RESEARCHGAME:
				startReaGame();
				resGame.loadGame(storedGame.reaGameData);
				break;
			case MANAGEGAME:
				startMngGame();
				mngGame.loadGame(storedGame.mngGameData);
				break;
			}
			
			in.close();
			fileIn.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * hide the cover frame when the game picked
	 */
	public void hide() {
		Coverview.hideAll();
	}
	
	/**
	 * show the cover when no game picked
	 */
	public void show() {
		Coverview.showCover();
		Coverview.update();
	}
	
	/**
	 * the method for starting the educational game
	 */
	public void startEduGame() {
		//initialize the new educational game and start the game
		PickGame = GamePicked.EDUCATIONALGAME;
		if (eduGame == null) eduGame = new EduController();
		eduGame.startTheGame();

		//hide the cover
		hide();
	}
	
	/**
	 * the method for starting the research game
	 */
	public void startReaGame() {
		PickGame = GamePicked.RESEARCHGAME;
		if (resGame == null) resGame = new ReaController();
		resGame.startTheGame();
		hide();
	}
	
	/**
	 * the method for starting the management game
	 */
	public void startMngGame() {
		//initialize the new educational game and start the game
		PickGame = GamePicked.MANAGEGAME;
		if (mngGame == null) mngGame = new MngController();
		mngGame.startTheGame();
		hide();
	}
	
}
