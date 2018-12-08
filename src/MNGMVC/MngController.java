package MNGMVC;
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
import javax.swing.JButton;
import javax.swing.Timer;

import COVERMVC.GameData;
import EDUMVC.EduElements;
import EDUMVC.EducationalGameState;

/**
 * Management game controller
 * @author Acer
 *
 */
public class MngController extends Controller implements Serializable {
	private MngModel mngModel;
	private MngView mngView;
	
	Timer t;
	

	
	/**
	 * Constructor including initialize the model and the view, and begin the game
	 */
	public MngController(){
		mngModel = new MngModel();
		mngView = new MngView(); 
		
		//add button listeners for the buttons
		addButtonListeners();
		
		addButtonArrayListeners();
		
		//initialize the timer
		int gameDelay = 10;
		t = new Timer(gameDelay, gameAction);
	}
	
	//game Action for the time running and time control
	@SuppressWarnings("serial")
	Action gameAction = new AbstractAction(){
		public void actionPerformed(ActionEvent e){
			
			mngModel.updateModel();
			
			//store all elements the view needed
			MngElements elements = mngModel.getElements();
			
			
			
			//update the view
			mngView.updateView(elements);
			}
	};

	/**
	 * adds button lister for next button
	 */
	public void addButtonListeners() {
		ActionListener button4Next = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				//go to the next state
				mngModel.nextState();	
				
			}
		};
		/**
		 * adds button lister for TrashCan tool button
		 */
		ActionListener TCtool = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				mngModel.chooseTool(Tool.TRASH);
			}
		};
		/**
		 * adds button lister for Spray tool button
		 */
		ActionListener SPtool = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				mngModel.chooseTool(Tool.SPRAY);
			}
		};
		/**
		 * adds button lister for Clipboard tool button
		 */
		ActionListener CBtool = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				mngModel.chooseTool(Tool.REPORT);
			}
		};
		
		ActionListener saveListener = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					saveGame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		
		
		mngView.setButtonListener(button4Next, saveListener, TCtool, SPtool, CBtool);
		
		
	}
	/**
	 * add button listeners for the hazard components
	 */
	public void addButtonArrayListeners() {
		ActionListener Trash = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				mngModel.checkSelection(Tool.TRASH);
				
			}
		};
		ActionListener Invasive = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				mngModel.checkSelection(Tool.SPRAY);
			}
		};
		ActionListener Spot = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				mngModel.checkSelection(Tool.REPORT);
			}
		};
		
		//tool buttons
		
		mngView.setArrayButtonListener(Trash, Invasive, Spot);
		
		
	}
	
	
	/**
	 * Start the game including start the manage view and the model,
	 * Start the timer
	 */
	public void startTheGame() {
		
		mngView.startTheGame();
		mngModel.startTheGame();
		t.start();
	}
	
	/**
	 * Stop the timer, stop the game, and stop the management view
	 */
	public void stopTheGame() {
		t.stop();
		mngView.stop();
	}
	

	
	/**
	 * Get the game state for the current status
	 * @return the current game state
	 */
	public ManageGameState getGameState() {
		return mngModel.getElements().GameState;
	}

	/**
	 * Creates a new GameData from the cover
	 * gets the elements from the model and
	 * store it in a new FileOutputStream		
	 * @throws IOException if it coudln't write in file
	 */
	public void saveGame() throws IOException{
		GameData GameDataOutput = new GameData();
		
		GameDataOutput.gamePicked = GamePicked.MANAGEGAME;
		GameDataOutput.saveMngGameData(mngModel.getElements());
		
		FileOutputStream fileout = new FileOutputStream("GameData.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		out.writeObject(GameDataOutput);
		out.close();
	}
	
	/**
	 * loads the game using the stored elements	
	 * @param mngData the stored elements from the saved game
	 */
	public void loadGame(MngElements mngData) {
		mngModel.loadGame(mngData);
	}
}


	

