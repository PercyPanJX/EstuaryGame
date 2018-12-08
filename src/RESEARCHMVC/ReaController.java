package RESEARCHMVC;

import SUPERMVC.Controller;
import WholeGameControl.GamePicked;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import COVERMVC.GameData;

/**
 * research game controller controlling the research game
 * @author hongbozhan
 *
 */
public class ReaController extends Controller{
	private ReaModel reaModel;
	private ReaView reaView;
	
	Timer t;

	/**
	 * constructor of controller including initializing view and model
	 */
	public ReaController() {
		reaModel = new ReaModel();
		reaView = new ReaView();
		//reaModel.hookx = reaView.getLocx();
		//reaModel.hooky = reaView.getLocy();
		
		// add button listeners
		addButtonListeners();
		
		// give scale rate to model
		reaModel.scale(reaView.getxRate(), reaView.getyRate());
		
		//add mouse listener in the frame
		reaView.setMouseListener(new mouseListener());
		
		//initialize the timer
		int gameDelay = 40;
		t = new Timer(gameDelay, reaAction);
		
	}
	
	
	Action reaAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			
			//update the model
			reaModel.updateModel();
			
			//store all elements the view needed
			ReaElements elements = reaModel.getElements();
			
			//update the view
			reaView.updateView(elements);
			
			
		}
	};
	
	public void addButtonListeners() {
		ActionListener button4Next = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				reaModel.nextState();
				System.out.println(reaModel.getElements().gameState);
			}
		};
		
		ActionListener[] button4Sample = new ActionListener[10];
		
		
			
		button4Sample[0] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(0);
		}};
		
		button4Sample[1] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(1);
		}};
			
		button4Sample[2] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(2);
		}};
		
		button4Sample[3] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(3);
		}};
		
		button4Sample[4] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(4);
		}};
		
		button4Sample[5] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(5);
		}};
		
		button4Sample[6] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(6);
		}};
		
		button4Sample[7] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(7);
		}};
		
		button4Sample[8] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(8);
		}};
		
		button4Sample[9] = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			reaModel.showInfo(9);
		}};
		
		ActionListener saveButton = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					saveGame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		
		reaView.setButtonListener(button4Next, button4Sample, saveButton);
		
		

	}
	
	
	
	
	//start the game
	/**
	 * start the game in model, view and time
	 */
	public void startTheGame() {
		reaView.startTheGame();
		reaModel.startTheGame();
		t.start();
	}
		
	//stop the game
	/**
	 * stop the game
	 */
	public void stopTheGame() {
		t.stop();
		reaView.stop();
	}


	/** 
	 * get current game state from model
	 * @return the current game state
	 */
	 public ResearchGameState getGameState() {
		return reaModel.getElements().gameState;
	}	
	
	
	class mouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			reaModel.mouseClicking();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * save the game data
	 * @throws IOException if is cannot read in the file
	 */
	public void saveGame() throws IOException{
		GameData GameDataOutput = new GameData();
		
		GameDataOutput.gamePicked = GamePicked.RESEARCHGAME;
		GameDataOutput.saveReaGameData(reaModel.getElements());
		
		FileOutputStream fileout = new FileOutputStream("GameData.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		out.writeObject(GameDataOutput);
		out.close();
	}
	
	public void loadGame(ReaElements reaData) {
		reaModel.loadGame(reaData);
	}
}
	
	





	
	
	
	


