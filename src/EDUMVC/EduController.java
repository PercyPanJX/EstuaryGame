package EDUMVC;
import SUPERMVC.Controller;
import WholeGameControl.GamePicked;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import COVERMVC.GameData;

/**
 * Educational game controller controlling the education game
 * @author percypan
 *
 */
public class EduController extends Controller implements Serializable{
	private EduModel eduModel;
	private EduView eduView;
	
	Timer t;
	
	/**
	 * Constructor including initialize the model and the view, and begin the game
	 */
	public EduController(){
		eduModel = new EduModel();
		eduView = new EduView();
		
		//add button listeners for the buttons
		addButtonListeners();
		
		//initialize the timer
		int gameDelay = 10;
		t = new Timer(gameDelay, gameAction);
	}
	
	//game Action for the time running and time control
	@SuppressWarnings("serial")
	Action gameAction = new AbstractAction(){
		public void actionPerformed(ActionEvent e){
					
			//update the model
			eduModel.updateModel();
			
			//store all elements the view needed
			EduElements elements = eduModel.getElements();
			
			//update the view
			eduView.updateView(elements);
			}
	};

	//add button listeners
	public void addButtonListeners() {
		ActionListener button4Next = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				//go to the next state
				eduModel.nextState();	
			}
		};
		
		//the list of button listeners for answers
		ArrayList<ActionListener> button4Answer = new ArrayList<ActionListener>();
		for(int i=0; i <4; i++) {
			int j = i;
			ActionListener AL = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					eduModel.pickAnAnswer(j);
				}
			};
			button4Answer.add(AL);
		}
		
		ActionListener saveListener = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					saveGame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		//adding all listeners for the buttons
		eduView.setButtonListener(button4Next,button4Answer, saveListener);
	}
	
	/**
	 * Start the game including start the educational view and the model,
	 * Start the timer
	 */
	public void startTheGame() {
		
		eduView.startTheGame();
		eduModel.startTheGame();
		t.start();
	}
	
	/**
	 * Stop the timer, stop the game, and stop the educational view
	 */
	public void stopTheGame() {
		t.stop();
		eduView.stop();
	}
	
	/**
	 * Get the game state for the current status
	 * @return the current game state
	 */
	public EducationalGameState getGameState() {
		return eduModel.getElements().GameState;
	}
	
	/**
	 * save the current game data 
	 * @throws IOException if it cannot read in
	 */
	public void saveGame() throws IOException{
		GameData GameDataOutput = new GameData();
		
		GameDataOutput.gamePicked = GamePicked.EDUCATIONALGAME;
		GameDataOutput.saveEduGameData(eduModel.getElements());
		
		FileOutputStream fileout = new FileOutputStream("GameData.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		out.writeObject(GameDataOutput);
		out.close();
	}
	
	/**
	 * load the saved game data
	 * @param eduGameData saved educational game data
	 */
	public void loadGame(EduElements eduGameData) {
		eduModel.loadGame(eduGameData);
	}
}

