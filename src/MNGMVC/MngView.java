package MNGMVC;
import SUPERMVC.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EDUMVC.EduElements;

/**
 * the management view
 * @author percypan
 *
 */
public class MngView extends View{
	
	//whole frame
	JFrame MngFrame;

	//main panel for the painting
	MainPanel mainPanel;
	
	//store the game state
	private ManageGameState GameState;
	
	
	/**
	 * Constructor including initializing all elements
	 */
	MngView() {
		
		//initial game state is waiting for the game begin
		GameState = ManageGameState.WAITING;
		
		//initial the frame
		initialTheFrame();
		
	}
	
	/**
	 * initializes the main panel and the frame
	 */
	private void initialTheFrame(){
		//initialize the frame
		MngFrame = new JFrame("Estuary Management Game");
		MngFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		MngFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MngFrame.setBackground(Color.BLACK);
		
		//set the panel for the background
		mainPanel = new MainPanel();
		
		//set the background panel for the frame
		MngFrame.setContentPane(mainPanel);
		
		pack();
		
	}

	
	/**
	 * Start the game, show the panel and set to the cover time
	 */
	protected void startTheGame() {
		//show the cover and change the game state to cover time
		MngFrame.setVisible(true);
		GameState = ManageGameState.COVERTIME;
		mainPanel.initValues();
		mainPanel.hideCenterAndSouthText();
	}

	/**
	 * Stop the game, hide the last elements and set invisible for the frame
	 */
	protected void stop() {
		MngFrame.setVisible(true);
	}
	
	/**
	 * update the view according to the elements from the model
	 * @param elements all elements the view need from the model
	 */
	protected void updateView(MngElements elements) {
		mainPanel.repaint();
		

		GameState = elements.GameState;
		switch(GameState) {
		case WAITING:
			break;
		case COVERTIME:
			showCover();
			hideAllEstuaryElements();
			break;
		case RULETIME:
			hideCover();
			showRule();
			break;
		case RULETIME2:
			showSecondRule();
			break;
		case BEGINNING:
			hideRule();
			showTheToolBar();
			showTheEstuaryEnvironment();
			beginningTime(elements);
			break;
		case GAMETIME:
			hideDarkCover();
			updateTheEstuary(elements);
			break;
		case ENDTIME:
			showEndingCover(elements);
			setBackCursor();
			break;
		case SCORETIME:
			hideAllEstuaryElements();
			showScoreCover(elements);
			break;
		case GAMESTOP:
			hideAllElements();
			stop();
			break;	
		}
	}
	
	/**
	 * show the cover
	 */
	private void showCover() {
		mainPanel.showCover();
	}
	
	/**
	 * hide the cover element
	 */
	private void hideCover() {
		mainPanel.hideCover();
	}
	
	
	/**
	 * show the Tool bar element
	 */
	private void showTheToolBar() {
		mainPanel.showToolBar();
	}

	
	/**
	 * show the estuary environment
	 */
	private void showTheEstuaryEnvironment() {
		mainPanel.showTheEstuaryEnvironment();
		
	}
	

	/**
	 * Beginning dark cover showing
	 * @param e elements from the model
	 */
	private void beginningTime(MngElements e) {
		mainPanel.hideNextButton();
		mainPanel.setDarkCoverTime(e.DarkCoverTimer);
		mainPanel.refreshWater(e.trashNum, e.invasiveNum, e.spotsNum);
	}
	
	/**
	 * hide the dark cover
	 */
	private void hideDarkCover() {
		mainPanel.showNextButton();
		mainPanel.hideDarkCoverTime();
	}
	
	/**
	 * hide all elements about the estuary
	 */
	private void hideAllEstuaryElements() {
		mainPanel.hideTheEstuaryEnvironment();
	}
	
	/**
	 * update the elements of the estuary
	 * @param elements elements from the model
	 */
	private void updateTheEstuary(MngElements elements) {
		
		//mainPanel.showTheTool(elements.tool);
		mainPanel.updateTime((int)elements.EstuaryTime);
		mainPanel.tool = elements.tool;
		mainPanel.refreshWater(elements.trashNum, elements.invasiveNum, elements.spotsNum);
		mainPanel.changeFishHpAndPlantHP(elements.FishHealthRate,elements.PlantHealthRate);
		mainPanel.showNextButton();
		mainPanel.showSaveButton();
	}
	
	/**
	 * when the time is up or isGameOver = true, show the correct end screen
	 */
	private void showEndingCover(MngElements elements) {
		mainPanel.showGameEndCover(elements.isGameOver);
	}
	
	/**
	 * when the congratulations screen is shown
	 */
	private void showScoreCover(MngElements elements) {
		mainPanel.showEndScoreCover(elements.isGameOver);
	}
	
	
	/**
	 * Set the button listeners on the panel
	 * @param ac4Next buttons listener for the 'next' button
	 * @param Save button for saving the game 
	 * @param TCtool listeners are set for TC tool
	 * @param SPtool listeners are set for TC tool
	 * @param CBtool listeners are set for TC tool
	 */
	protected void setButtonListener(ActionListener ac4Next, ActionListener Save, ActionListener TCtool,
			ActionListener SPtool, ActionListener CBtool) {
		mainPanel.setButtonListener(ac4Next, Save, TCtool, SPtool, CBtool);
		
	}
	
	/**
	 * 
	 * @param Trash listeners for each trash bag are made
	 * @param Invasive listeners for each invasive plant are made
	 * @param Spot listeners for each spot are made
	 */
	protected void setArrayButtonListener(ActionListener Trash, ActionListener Invasive,
			ActionListener Spot) {
		mainPanel.setArrayButtonListener(Trash, Invasive, Spot);
		
	}
	
	/**
	 * shows the rule panel
	 */
	protected void showRule() {
		mainPanel.showRule();
	}
	
	/**
	 * 2nd part of rule
	 */
	
	protected void showSecondRule() {
		mainPanel.changeRule();
	}
	
	
	/**
	 * rule panel hidden
	 */
	protected void hideRule() {
		mainPanel.hideRule();
	}
	
	/**
	 * hides the Center and South text to remove them after loading game
	 */
	protected void hideAllElements() {
		mainPanel.hideCenterAndSouthText();
	}
	/**
	 * returns the cursor to normal
	 */
	
	protected void setBackCursor() {
		MngFrame.setCursor(null);
	}
	
////////////////// Panel /////////////////////////////////
	/**
	 * mainPanel contains all elements for the display
	 * @author Acer
	 *
	 */
	private class MainPanel extends JPanel{
		private JLabel CoverLabel = new JLabel();
		private JLabel RuleLabel = new JLabel();
		private JLabel ToolBarLabel = new JLabel();
		private JLabel TimeLabel = new JLabel();
		private JLabel CenterText = new JLabel();
		private JLabel SouthText = new JLabel();
		
		
		private JLabel FishLabel = new JLabel();
		private JLabel PlantLabel = new JLabel();
		private JLabel DarkCover = new JLabel();
		
		private JLabel FishHPLabel = new JLabel();
		private JLabel PlantHPLabel = new JLabel();
		
		ToolBarPanel ToolBarPane;
		
		//buttons for the interaction
		JButton NextButton;
		JButton TrashcanButton;
		JButton SprayButton;
		JButton ClipboardButton;
		JButton SaveButton = new JButton();
		
		//buttons for the invasive plants and trashbags and spots
		int trashNum = 10; int invasiveNum = 6; int spotNum = 4;
		JButton [] TrashButtons = new JButton[trashNum];
		JButton [] InvasiveButtons = new JButton[invasiveNum];
		JButton [] SpotButtons = new JButton[spotNum];
		
		JButton ruleToolButton = new JButton();
		JButton ruleTrashButton = new JButton();
		
		boolean clickRuleTool = false;
		boolean clickedTrashRule = false;
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		private int windowW = (int)bounds.getWidth();
		private int windowH = (int)bounds.getHeight();
		private double scaleRateW;
		private double scaleRateH;
		
		int cursorImg = 0; // 0: initial, 1: trashCan, 2:spraycan, 3: clipboard
		
		Tool tool;
		
		//All images for the management game
		BufferedImage MngCoverImg, NextButtonImg, ToolBarImg;
		BufferedImage RiverFlowing1, RiverFlowing2, RiverFlowing3, RiverFlowing4; 
		BufferedImage TrashcanImg, SprayImg, ClipboardImg;
		BufferedImage TrashImg,HealthBarImg, PlantBarImg;
		BufferedImage CursorImg;
		BufferedImage FishImg, PlantImg, InvasiveImg;
		BufferedImage [] SpotImg = new BufferedImage[2];
		BufferedImage [] ruleImg = new BufferedImage[3];
		
		//iterator for background river pictures
		int riverImgNum = 0;
		
		//counter to trigger river pictures to switch
		double timeBG = 0;
		
		/**
		 * constructor for initialize all elements and hide all of them
		 */
		public MainPanel() {
			
			//stores all images 
			addedImages();
			
			//scales all the pictures to the window size
			scaleAllElements();
			
			//background color
			setBackground(null);
			
		
			//initializes and shows the Next button 
			createNextButton();
			
			//initial the cover
			createCover();
			
			//initializes the rule panel
			createRule();
			
			
			//initializes the elements for the estuary 
			createEstuaryEnvironment();
			
			//initializes save button
			createSaveButton();
			
		}
		/**
		 * Creates booleans for JButton in the tutorial
		 */
		protected void initValues() {
			clickRuleTool = false;
			clickedTrashRule = false;
		}
		
		
		
		/**
		 * paint component for background painting and the question label showing
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			//add the background in the center
			Graphics2D g2d = (Graphics2D) g;
			int x = (windowW - MngCoverImg.getWidth(null)) / 2;
			int y = (windowH - MngCoverImg.getHeight(null)) / 2;
			
			//switches background images to "animate" river
			if(timeBG <= 0.5) timeBG += 0.01;
			else {
				timeBG = 0;
				if(riverImgNum == 0) riverImgNum = 1;
				else if (riverImgNum == 1) riverImgNum = 2;
				else if (riverImgNum == 2) riverImgNum = 3;
				else if (riverImgNum == 3) riverImgNum = 0;
				
			};
			
			if(riverImgNum == 0) {
				g2d.drawImage(RiverFlowing1, x, y, null);
			}
			else if(riverImgNum == 1){
				g2d.drawImage(RiverFlowing2, x, y, null);
			}
			else if(riverImgNum == 2){
				g2d.drawImage(RiverFlowing3, x, y, null);
			}
			else if(riverImgNum == 3){
				g2d.drawImage(RiverFlowing4, x, y, null);
			}

		}
		
		
		/**
		 * Initialize the 'next' button panel
		 * 
		 */
		private void createNextButton() {
			//first layout set for the frame
			this.setLayout(new GridBagLayout());
			
			//next button added and properties set
			NextButton = new JButton();
			NextButton.setIcon(new ImageIcon(NextButtonImg));
			NextButton.setBackground(null);
			NextButton.setOpaque(false);
			NextButton.setBorder(null);
			NextButton.setContentAreaFilled(false);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Cursor c = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
			NextButton.setCursor(c);
			NextButton.setVisible(true);

			GridBagConstraints gbc4Buttons = createGridConstraints(0,0,0,0,GridBagConstraints.SOUTHEAST);			
			this.add(NextButton,gbc4Buttons);
		}
		/**
		 * Initializes the 'save' button panel
		 */
		private void createSaveButton() {
			
			SaveButton.setBackground(null);
			SaveButton.setOpaque(false);
			SaveButton.setBorder(null);
			SaveButton.setForeground(Color.WHITE);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Cursor c = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
			SaveButton.setCursor(c);
			SaveButton.setVisible(true);
			
			SaveButton.setText("SAVE");

			GridBagConstraints gbc4SaveButton = createGridConstraints(0,0,0,0,GridBagConstraints.SOUTHWEST);			
			this.add(SaveButton,gbc4SaveButton);
		}
		
		/**
		 * Initialize the rule elements
		 */
		private void createRule() {
			
			ruleToolButton.setIcon(new ImageIcon(TrashcanImg));
			ruleToolButton.setBackground(null);
			ruleToolButton.setOpaque(false);
			ruleToolButton.setBorder(null);
			ruleToolButton.setVisible(false);
			
			
			ActionListener action = new ActionListener(){
				public void actionPerformed(ActionEvent actionEvent) {
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Cursor t = toolkit.createCustomCursor(TrashcanImg, new Point(0,0), "custom cursor");
					MngFrame.setCursor(t);
					NextButton.setVisible(false);
					RuleLabel.setIcon(new ImageIcon(ruleImg[2]));
					clickRuleTool = true;
					
				}
			};
			
			ActionListener actionTrash = new ActionListener(){
				public void actionPerformed(ActionEvent actionEvent) {
					if(clickRuleTool == true) {
						ruleTrashButton.setVisible(false);
						MngFrame.setCursor(null);
						NextButton.setVisible(true);
						clickedTrashRule = true;
					}
					
				}
			};
			
			ruleToolButton.addActionListener(action);
			
		
			
			ruleTrashButton.setIcon(new ImageIcon(TrashImg));
			ruleTrashButton.setBackground(null);
			ruleTrashButton.setOpaque(false);
			ruleTrashButton.setBorder(null);
			ruleTrashButton.setVisible(false);
			
			ruleTrashButton.addActionListener(actionTrash);
			
			GridBagConstraints gbc4RuleTool = createGridConstraints(0,0,(int)(240*scaleRateH),(int)(190*scaleRateW),GridBagConstraints.CENTER);
			this.add(ruleToolButton,gbc4RuleTool);
			
			gbc4RuleTool = createGridConstraints(0,(int)(200*scaleRateW),(int)(240*scaleRateH),0,GridBagConstraints.CENTER);
			this.add(ruleTrashButton,gbc4RuleTool);
			
			
			
			
			
			RuleLabel.setIcon(new ImageIcon(ruleImg[0]));
			RuleLabel.setOpaque(false);
			RuleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 6));
			GridBagConstraints gbc4Rule = createGridConstraints(0,0,0,0,GridBagConstraints.CENTER);
			this.add(RuleLabel,gbc4Rule);
			RuleLabel.setVisible(false);
			
			
		}
		/**
		 * shows the rule panel and 1st image
		 */
		private void showRule() {
			RuleLabel.setIcon(new ImageIcon(ruleImg[0]));
			RuleLabel.setVisible(true);

		}
		/**
		 * changes the rule states
		 */
		private void changeRule() {
			if(clickRuleTool == false) {
				NextButton.setVisible(false);
				RuleLabel.setIcon(new ImageIcon(ruleImg[1]));
			}
			RuleLabel.setVisible(true);
			
			if(clickedTrashRule == false) ruleTrashButton.setVisible(true);
			else ruleTrashButton.setVisible(false);
			
			ruleToolButton.setVisible(true);
		}	
		
		/** 
		 * hides the rule panel
		 */
		private void hideRule() {
			RuleLabel.setVisible(false);
			ruleTrashButton.setVisible(false);
			ruleToolButton.setVisible(false);
		}
		
		/**
		 * Initialize the cover elements
		 */
		private void createCover() {
			CoverLabel.setIcon(new ImageIcon(MngCoverImg));
			CoverLabel.setOpaque(false);
			GridBagConstraints gbc4Cover = createGridConstraints(0,0,(int)(40*scaleRateH),0,GridBagConstraints.CENTER);
			this.add(CoverLabel,gbc4Cover);
			CoverLabel.setVisible(false);
			
		}

		/**
		 * Set the button listeners on the panel
		 * @param ac4Next buttons listener for the 'next' button
		 * @param AL Action listener for the tools on the toolbar
		 */
		protected void setButtonListener(ActionListener ac4Next, ActionListener Save, ActionListener TCtool,
				ActionListener SPtool, ActionListener CBtool) {
			
			//next button and save game listeners
			NextButton.addActionListener(ac4Next);
			SaveButton.addActionListener(Save);
			//tool action listeners
			TrashcanButton.addActionListener(TCtool);
			SprayButton.addActionListener(SPtool);
			ClipboardButton.addActionListener(CBtool);
			
		}
		/**
		 * Set button listeners on the random hazards that spawn
		 */
		protected void setArrayButtonListener(ActionListener Trash, ActionListener Invasive,
				ActionListener Spot) {
			
			for(JButton b : TrashButtons) {
				b.addActionListener(Trash);
			}

			for(JButton i : InvasiveButtons) {
				i.addActionListener(Invasive);
			}

			for(JButton s : SpotButtons) {
				s.addActionListener(Spot);
			}
		}
		
		/**
		 * initializes the tool bar panel 
		 */
		private void createToolBarPanel() {
			ToolBarPane = new ToolBarPanel();
			GridBagConstraints gbc4ToolBar = createGridConstraints(0,0,0,0, GridBagConstraints.NORTHEAST);
			this.add(ToolBarPane,gbc4ToolBar);
			ToolBarPane.setVisible(false);
			
		}
		
		 /**
		  * initializes Health bar panels, with plant and fish hp bars
		  */
		private void createHealthBarPanel() {
			FishHPLabel.setPreferredSize(new Dimension(HealthBarImg.getWidth(), HealthBarImg.getHeight()));
			FishHPLabel.setIcon(new ImageIcon(HealthBarImg));
			FishHPLabel.setBackground(Color.BLACK);
			FishHPLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			
			FishHPLabel.setVisible(true);
			
			PlantBarImg = HealthBarImg;
			
			PlantHPLabel.setPreferredSize(new Dimension(PlantBarImg.getWidth(), PlantBarImg.getHeight()));
			PlantHPLabel.setIcon(new ImageIcon(PlantBarImg));
			PlantHPLabel.setBackground(null);
			PlantHPLabel.setOpaque(false);
			PlantHPLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			
			PlantHPLabel.setVisible(true);
			
			
			int position4PlanHP = 100;
			GridBagConstraints gbc4FishHPLabel = createGridConstraints(0,0,(int)(position4PlanHP*2*scaleRateH),0, GridBagConstraints.SOUTHWEST);
			GridBagConstraints gbc4PlantHPLabel = createGridConstraints(0,0,(int)(position4PlanHP*scaleRateH),0, GridBagConstraints.SOUTHWEST);
	
	        //add buttons
	        this.add(FishHPLabel, gbc4FishHPLabel);
	        this.add(PlantHPLabel, gbc4PlantHPLabel);
	        
	        FishLabel.setIcon(new ImageIcon(FishImg));
	        FishLabel.setBorder(null);
	        FishLabel.setBackground(null);
	        FishLabel.setVisible(false);
	        
	        PlantLabel.setIcon(new ImageIcon(PlantImg));
	        PlantLabel.setBorder(null);
	        PlantLabel.setBackground(null);
	        PlantLabel.setVisible(false);
	        
	        int space = (int)((HealthBarImg.getWidth())* scaleRateW);
	        GridBagConstraints gbc4Fish = createGridConstraints(0,space,(int)(position4PlanHP*2*scaleRateH),0, GridBagConstraints.SOUTHWEST);
			GridBagConstraints gbc4Plant = createGridConstraints(0,space,(int)(position4PlanHP*scaleRateH),0, GridBagConstraints.SOUTHWEST);
			
			this.add(FishLabel, gbc4Fish);
			this.add(PlantLabel, gbc4Plant);
	        
	        
		}
		
		/**
		 * This changes the "fullness" of the healthbar
		 * @param rateHealth the amount that the fish hp bar changes per trash or spot
		 * @param ratePlant the amount that the plant hp changes per invasive
		 */
		protected void changeFishHpAndPlantHP(double rateHealth, double ratePlant) {
			FishHPLabel.setIcon(new ImageIcon(scaleImage(HealthBarImg, rateHealth, 1)));
		
			if(ratePlant <= 0.1) ratePlant = 0.1;
			PlantHPLabel.setIcon(new ImageIcon(scaleImage(PlantBarImg, ratePlant, 1)));
			
		}
		
		
		/**
		 * Initialize all estuary environment elements
		 */
		private void createEstuaryEnvironment() {	
			

			//Dark Cover
			DarkCover.setBorder(BorderFactory.createEmptyBorder(windowH/2, windowW/2, windowH/2-(int)(60*scaleRateH), windowW/2));
			DarkCover.setBackground(new Color(64,64,64,125));
			DarkCover.setOpaque(true);
			GridBagConstraints gbc4DarkCover = createGridConstraints(0,0,0,0,GridBagConstraints.NORTH);
			this.add(DarkCover,gbc4DarkCover);
			DarkCover.setVisible(false);
			GridBagConstraints gbc4CenterText = createGridConstraints(0,0,0,0,GridBagConstraints.CENTER);
			this.add(CenterText,gbc4CenterText);
			CenterText.setOpaque(false);
			CenterText.setVisible(false);
			GridBagConstraints gbc4SouthText = createGridConstraints(0,0,(int)(100*scaleRateH),0,GridBagConstraints.SOUTH);
			this.add(SouthText, gbc4SouthText);
			SouthText.setOpaque(false);
			SouthText.setVisible(false);
			
			
			//set the font for center texts 
			try {
			    //create the font to use. Specify the size!
			    Font customFontForTime = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf").openStream())).deriveFont(Font.BOLD, (float)(35 * scaleRateH));
			    Font customFontForCenterText = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf").openStream())).deriveFont(Font.BOLD, (float)(100 * scaleRateH));
			    Font customFontForSouthText = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf").openStream())).deriveFont(Font.BOLD, (float)(50 * scaleRateH));
			    Font customFontSaveButton = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf").openStream())).deriveFont(Font.BOLD, (float)(40 * scaleRateH));

			    
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf").openStream())));
			    
			    TimeLabel.setFont(customFontForTime);
			    CenterText.setFont(customFontForCenterText);
			    SouthText.setFont(customFontForSouthText);
			    SaveButton.setFont(customFontSaveButton);
			    
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
			
			//time
			GridBagConstraints gbc4Time = createGridConstraints(0, 0, (int)(10*scaleRateH), (int)(200*scaleRateW), GridBagConstraints.SOUTH); 
			this.add(TimeLabel,gbc4Time);
			TimeLabel.setVisible(false);
			TimeLabel.setOpaque(false);
			
		
			//create tool bar panel
			createToolBarPanel();
			
			//create health bar panel
			createHealthBarPanel();
			
			//buttons array
			createButtonArrays();
		}
		
		
		/**
		 * Set the cover label be visible
		 */
		protected void showCover() {
			CoverLabel.setVisible(true);
		}
		
		/**
		 * Hide the cover label
		 */
		protected void hideCover() {
			CoverLabel.setVisible(false);
		}
		
		
		/**
		 * Show the tool bar label
		 */
		protected void showToolBar() {
			ToolBarLabel.setVisible(true);
		}
		
		/**
		 * show the estuary environment elements
		 */
		protected void showTheEstuaryEnvironment() {
			CenterText.setVisible(true);
			TimeLabel.setVisible(true);
			ToolBarPane.setVisible(true);
			FishHPLabel.setVisible(true);
			PlantHPLabel.setVisible(true);
			FishLabel.setVisible(true);
			PlantLabel.setVisible(true);
			DarkCover.setVisible(true);
		}
		
		
	
		/**
		 * Show the end of the game label
		 */
		protected void showGameEndCover(boolean isOver) {
			CenterText.setText("TIME'S UP");
			if(isOver) {
				CenterText.setText("GAME OVER!");
			}
			CenterText.setVisible(true);
			
			
		}
		/**
		 * Sets conditions for the end cover
		 * @param isOver determines if there is a game over or not
		 */
		protected void showEndScoreCover(boolean isOver) {
			CenterText.setText("CONGRATULATIONS!");
			CenterText.setVisible(true);
			SouthText.setText("YOU SAVED THE ESTUARY!");
			
			if(isOver) {
				CenterText.setText("GAME OVER!");
				SouthText.setText("");
			}
			
			SouthText.setVisible(true);
			NextButton.setVisible(true);
		}
		/**
		 * hides the text in the center and south grid constraints
		 */
		protected void hideCenterAndSouthText() {
			SouthText.setVisible(false);
			CenterText.setVisible(false);
		}
		
		
		
		/**
		 * Hide all class environment elements
		 */
		protected void hideTheEstuaryEnvironment() {
			CenterText.setVisible(false);
			TimeLabel.setVisible(false);
			ToolBarPane.setVisible(false);
			FishHPLabel.setVisible(false);
			PlantHPLabel.setVisible(false);
			FishLabel.setVisible(false);
			PlantLabel.setVisible(false);
			
			for(JButton j : TrashButtons) {
				j.setVisible(false);
			}
			for(JButton j : InvasiveButtons) {
				j.setVisible(false);
			}
			for(JButton j : SpotButtons) {
				j.setVisible(false);
			}
		}
		
		/**
		 * Update the timer
		 * @param time remaining for the player
		 */
		protected void updateTime(int time) {
			TimeLabel.setText("Time Left: " + Integer.toString(time));
		}
		

		/**
		 * Set the dark cover text
		 * @param time The remaining timer which will show on the dark cover
		 */
		protected void setDarkCoverTime(double time) {
			CenterText.setIcon(null);
			if((int)time == 0) 	CenterText.setText("START!");
			else CenterText.setText(Integer.toString((int)time));
			
		}

		/**
		 * The method is for hiding dark cover
		 */
		protected void hideDarkCoverTime() {
			CenterText.setVisible(false);
			DarkCover.setVisible(false);
		}
		
		/**
		 * Method creates all buttons for each hazard and sets their locations
		 * Mouse listeners are added to each
		 */

		private void createButtonArrays() {
			
			for(int i = 0; i < trashNum; i++) {
				TrashButtons[i]= new JButton();
				TrashButtons[i].setIcon(new ImageIcon(TrashImg));
				TrashButtons[i].setBorder(null);
				TrashButtons[i].setBackground(null);
				TrashButtons[i].setOpaque(false);
				TrashButtons[i].setContentAreaFilled(false);
				TrashButtons[i].setVisible(false);
				
				
				int j = i;
				TrashButtons[i].addMouseListener(new java.awt.event.MouseAdapter() {
				    public void mouseClicked(MouseEvent evt) {
				    	if(tool == Tool.TRASH) TrashButtons[j].setVisible(false);
				    }
				});
			}
			

			GridBagConstraints gbc4TrashButtons = createGridConstraints(0, (int)(50*scaleRateW),(int)(50*scaleRateH), 0, GridBagConstraints.NORTHWEST);
			this.add(TrashButtons[0],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints((int)(50*scaleRateH), (int)(75*scaleRateW), 0, 0, GridBagConstraints.NORTHWEST);
			this.add(TrashButtons[1],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints((int)(100*scaleRateH), (int)(300*scaleRateW),0, 0, GridBagConstraints.NORTHWEST);
			this.add(TrashButtons[2],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints(0, (int)(50*scaleRateW),(int)(100*scaleRateH), 0, GridBagConstraints.CENTER);
			this.add(TrashButtons[3],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints(0, 0,(int)(100*scaleRateH), (int)(100*scaleRateW), GridBagConstraints.CENTER);
			this.add(TrashButtons[4],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints(0, 0, (int)(150*scaleRateH), (int)(150*scaleRateW), GridBagConstraints.CENTER);
			this.add(TrashButtons[5],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints(0, (int)(150*scaleRateW),(int)(150*scaleRateH), 0, GridBagConstraints.CENTER);
			this.add(TrashButtons[6],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints(0, (int)(100*scaleRateW),(int)(200*scaleRateH), 0, GridBagConstraints.CENTER);
			this.add(TrashButtons[7],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints(0, (int)(300*scaleRateW),(int)(150*scaleRateH), 0, GridBagConstraints.CENTER);
			this.add(TrashButtons[8],gbc4TrashButtons);
			gbc4TrashButtons = createGridConstraints(0, (int)(350*scaleRateW),(int)(50*scaleRateH), 0, GridBagConstraints.CENTER);
			this.add(TrashButtons[9],gbc4TrashButtons);
		
			
			for(int i = 0; i < invasiveNum; i++ ) {
				InvasiveButtons[i] = new JButton();
				InvasiveButtons[i]  = new JButton();
				InvasiveButtons[i] .setIcon(new ImageIcon(InvasiveImg));
				InvasiveButtons[i] .setBorder(null);
				InvasiveButtons[i] .setBackground(null);
				InvasiveButtons[i] .setOpaque(false);
				InvasiveButtons[i] .setContentAreaFilled(false);
				InvasiveButtons[i].setVisible(false);
				
				int j = i;
				InvasiveButtons[i].addMouseListener(new java.awt.event.MouseAdapter() {
				    public void mouseClicked(MouseEvent evt) {
				    	if(tool == Tool.SPRAY) InvasiveButtons[j].setVisible(false);
				    }
				});
			}
			
			GridBagConstraints gbc4InvasiveButtons = createGridConstraints(0, (int)(300*scaleRateW),0, 0, GridBagConstraints.WEST);
			this.add(InvasiveButtons[0],gbc4InvasiveButtons);
			gbc4InvasiveButtons = createGridConstraints(0, 0, 0, 0, GridBagConstraints.CENTER);
			this.add(InvasiveButtons[1],gbc4InvasiveButtons);
			gbc4InvasiveButtons = createGridConstraints(0, 0,(int)(200*scaleRateH), (int)(200*scaleRateW), GridBagConstraints.SOUTHEAST);
			this.add(InvasiveButtons[2],gbc4InvasiveButtons);
			gbc4InvasiveButtons = createGridConstraints((int)(-300*scaleRateH), (int)(50*scaleRateW),0, 0, GridBagConstraints.EAST);
			this.add(InvasiveButtons[3],gbc4InvasiveButtons);
			gbc4InvasiveButtons = createGridConstraints((int)(100*scaleRateH), (int)(50*scaleRateW),0, 0, GridBagConstraints.NORTHWEST);
			this.add(InvasiveButtons[4],gbc4InvasiveButtons);
			gbc4InvasiveButtons = createGridConstraints((int)(50*scaleRateH), 0,0, 0, GridBagConstraints.NORTH);
			this.add(InvasiveButtons[5],gbc4InvasiveButtons);
			
			
			for(int i = 0; i < spotNum; i++) {
				SpotButtons[i] = new JButton();
				SpotButtons[i] = new JButton();
				SpotButtons[i].setIcon(new ImageIcon(SpotImg[i%2]));
				SpotButtons[i].setBorder(null);
				SpotButtons[i].setBackground(null);
				SpotButtons[i].setOpaque(false);
				SpotButtons[i].setContentAreaFilled(false);
				SpotButtons[i].setVisible(false);
				
				int j = i;
				SpotButtons[i].addMouseListener(new java.awt.event.MouseAdapter() {
				    public void mouseClicked(MouseEvent evt) {
				    	if(tool == Tool.REPORT) SpotButtons[j].setVisible(false);
				    }
				});
			}
			
			GridBagConstraints gbc4SpotButtons = createGridConstraints((int)(75*scaleRateH), 0, 0, (int)(275*scaleRateW), GridBagConstraints.EAST);
			this.add(SpotButtons[0],gbc4SpotButtons);
			gbc4SpotButtons = createGridConstraints(0, 0, 0, (int)(50*scaleRateW), GridBagConstraints.EAST);
			this.add(SpotButtons[1],gbc4SpotButtons);
			gbc4SpotButtons = createGridConstraints(0, 0, (int)(25*scaleRateH), (int)(200*scaleRateW), GridBagConstraints.EAST);
			this.add(SpotButtons[2],gbc4SpotButtons);
			gbc4SpotButtons = createGridConstraints((int)(200*scaleRateH), 0, 0,(int)(75*scaleRateW), GridBagConstraints.EAST);
			this.add(SpotButtons[3],gbc4SpotButtons);
		}
		
		/**
		 * Makes the correct hazards appear visible in the water
		 * 
		 * @param trashNumRe this is the amount of trash that is not visible
		 * @param invasiveNumRe this is the amount of invasive species that is not visible
		 * @param spotsNumRe this is the amount of spots that is not visible
		 */
		protected void refreshWater(int trashNumRe, int invasiveNumRe, int spotsNumRe) {
			
			int visibleNumCurr = 0;
			
			for(JButton e : TrashButtons) {
				if (e.isVisible()) visibleNumCurr += 1;
			}
			//if the remaining trash is less than the visible, make one more visible
			int needAdd = trashNumRe - visibleNumCurr;
			
			int i = 0;
			while(needAdd > 0) {
				if(  !TrashButtons[i].isVisible()) {
					TrashButtons[i].setVisible(true);
					needAdd -= 1;
				}
				i++;
			}
			
			
			
			visibleNumCurr = 0;
			
			//if the remaining invasive plants are less than the visible, make one more visible
			for(JButton e : InvasiveButtons) {
				if (e.isVisible()) visibleNumCurr += 1;
			}
			
			needAdd = invasiveNumRe - visibleNumCurr;
			
			i = 0;
			while(needAdd > 0) {
				if(  !InvasiveButtons[i].isVisible()) {
					InvasiveButtons[i].setVisible(true);
					needAdd -= 1;
				}
				i++;
			}
			
			//if the remaining spots are less than the visible, make one more visible
			visibleNumCurr = 0;
			
			for(JButton e : SpotButtons) {
				if (e.isVisible()) visibleNumCurr += 1;
			}
			
			needAdd = spotsNumRe - visibleNumCurr;
			
			i = 0;
			while(needAdd > 0) {
				if(  !SpotButtons[i].isVisible()) {
					SpotButtons[i].setVisible(true);
					needAdd -= 1;
				}
				i++;
			}
			
		}
		/**
		 * hides next button
		 */
		protected void hideNextButton() {
			NextButton.setVisible(false);
		}
		/**
		 * hides save button
		 */
		protected void hideSaveButton() {
			NextButton.setVisible(false);
		}
		/**
		 * shows next button
		 */
		protected void showNextButton() {
			NextButton.setVisible(true);
		}
		
		protected void showSaveButton() {
			SaveButton.setVisible(true);
		}
		
////////////////////////////////////////////////////////////////////
		
		/**
		 * initializes the ToolBarPanel. Used to place each tool in
		 * @author Acer
		 *
		 */
		private class ToolBarPanel extends JPanel{
			
			public ToolBarPanel() {
				
				initialButtons();
				
				setBorder(null); //set the none border
				this.setBackground(null); // set the transparent background
				
				//set the grid bag layout for the panel
				setLayout(new GridBagLayout());
		        GridBagConstraints gbc = new GridBagConstraints();
		        
		        //horizontal showing the buttons
		        gbc.gridheight = GridBagConstraints.REMAINDER;
		        
		        //set the spaces between buttons 
		        gbc.insets = new Insets(0, (int)(40*scaleRateW), 0, 0);
		        
		        //set the transparent
		        setOpaque(false);
		        
		        //add buttons
		        add(TrashcanButton, gbc);
		        add(SprayButton, gbc);
		        add(ClipboardButton, gbc);
			}
			
			
			/**
			 * initialize the buttons, including setting null border and null background, and setting the cursor
			 */
			private void initialButtons() {
				TrashcanButton = new JButton();
				SprayButton = new JButton();
				ClipboardButton = new JButton();
				
				//trashcan tool
				TrashcanButton = new JButton();
				TrashcanButton.setIcon(new ImageIcon(TrashcanImg));
				TrashcanButton.setBorder(null);
				TrashcanButton.setBackground(null);
				TrashcanButton.setOpaque(false);
				TrashcanButton.setContentAreaFilled(false);
				
				
				
				//spray tool
				SprayButton = new JButton();
				SprayButton.setIcon(new ImageIcon(SprayImg));
				SprayButton.setBorder(null);
				SprayButton.setBackground(null);
				SprayButton.setOpaque(false);
				SprayButton.setContentAreaFilled(false);
				
				//Clipboard tool
				ClipboardButton = new JButton();
				ClipboardButton.setIcon(new ImageIcon(ClipboardImg));
				ClipboardButton.setBorder(null);
				ClipboardButton.setBackground(null);
				ClipboardButton.setOpaque(false);
				ClipboardButton.setContentAreaFilled(false);
				
				//set the cursor for each tool 

				
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Cursor t = toolkit.createCustomCursor(TrashcanImg, new Point(0,0), "custom cursor");
				Cursor s = toolkit.createCustomCursor(SprayImg, new Point(0,0), "custom cursor");
				Cursor c = toolkit.createCustomCursor(ClipboardImg, new Point(0,0), "custom cursor");
				TrashcanButton.setCursor(t);
				SprayButton.setCursor(s);
				ClipboardButton.setCursor(c);
				
				ruleToolButton.setCursor(t);
				
				TrashcanButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(cursorImg != 1) {
							Toolkit toolkit = Toolkit.getDefaultToolkit();
							Cursor t = toolkit.createCustomCursor(TrashcanImg, new Point(0,0), "custom cursor");
							MngFrame.setCursor(t);
							cursorImg = 1;
						}else if(cursorImg == 1) {
							cursorImg = 0;
							MngFrame.setCursor(null);
						}
					}
				});
				
				SprayButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(cursorImg != 2) {
							Toolkit toolkit = Toolkit.getDefaultToolkit();
							Cursor t = toolkit.createCustomCursor(SprayImg, new Point(0,0), "custom cursor");
							MngFrame.setCursor(t);
							cursorImg = 2;
						}else if(cursorImg == 2) {
							cursorImg = 0;
							MngFrame.setCursor(null);
						}
					}
				});
				
				ClipboardButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(cursorImg != 3) {
							Toolkit toolkit = Toolkit.getDefaultToolkit();
							Cursor t = toolkit.createCustomCursor(ClipboardImg, new Point(0,0), "custom cursor");
							MngFrame.setCursor(t);
							cursorImg = 3;
						}else if(cursorImg == 3) {
							cursorImg = 0;
							MngFrame.setCursor(null);
						}
					}
				});
				
				TrashcanButton.setVisible(true);
				SprayButton.setVisible(true);
				ClipboardButton.setVisible(true);
				
			}
		}
		
		
///////////////////////////////////////////////////////////
		
		/**
		 * Load all images
		 */
		private void addedImages() {	 
			try {
				
				MngCoverImg = ImageIO.read((getClass().getResource("/Source/Images/Manage/ManageCover.png")).openStream());
				NextButtonImg = ImageIO.read(getClass().getResource("/Source/Images/Education/Next.png").openStream());
				ToolBarImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/toolbar.png").openStream());
				TrashcanImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/trashcan.png").openStream());
				SprayImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/spraycan.png").openStream());
				ClipboardImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/Clipboard.png").openStream());
				TrashImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/Trash.png").openStream());
				FishImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/FishHealth.png").openStream());
				PlantImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/PlantHealth.png").openStream());
				InvasiveImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/reed.png").openStream());
				SpotImg[0] = ImageIO.read(getClass().getResource("/Source/Images/Manage/spot1.png").openStream());
				SpotImg[1] = ImageIO.read(getClass().getResource("/Source/Images/Manage/spot2.png").openStream());
				
				ruleImg[0] = ImageIO.read(getClass().getResource("/Source/Images/Manage/Rule1.PNG").openStream());
				ruleImg[1] = ImageIO.read(getClass().getResource("/Source/Images/Manage/Rule2.PNG").openStream());
				ruleImg[2] = ImageIO.read(getClass().getResource("/Source/Images/Manage/Rule3.PNG").openStream());

				
				RiverFlowing1 = ImageIO.read(getClass().getResource("/Source/Images/Manage/River.png").openStream());
				RiverFlowing2 = ImageIO.read(getClass().getResource("/Source/Images/Manage/River2.png").openStream());
				RiverFlowing3 = ImageIO.read(getClass().getResource("/Source/Images/Manage/River3.png").openStream());
				RiverFlowing4 = ImageIO.read(getClass().getResource("/Source/Images/Manage/River4.png").openStream());
				CursorImg = ImageIO.read((getClass().getResource("/Source/Images/Cursor.png")).openStream());
				HealthBarImg = ImageIO.read(getClass().getResource("/Source/Images/Manage/HealthBar.png").openStream());
			
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Scale all images of element according to the rate between background and the window size
		 */
		private void scaleAllElements() {
			
			scaleRateW =  (double) windowW / (double)RiverFlowing1.getWidth() ;
			scaleRateH = (double) windowH / (double) RiverFlowing1.getHeight() ;	
			
			//resize all images according to scale rates. All four are used for 
			//the background
			
			RiverFlowing1 = scaleImage(RiverFlowing1, scaleRateW,scaleRateH);	
			RiverFlowing2 = scaleImage(RiverFlowing2, scaleRateW,scaleRateH);
			RiverFlowing3 = scaleImage(RiverFlowing3, scaleRateW,scaleRateH);
			RiverFlowing4 = scaleImage(RiverFlowing4, scaleRateW,scaleRateH);

		
			MngCoverImg = scaleImage(MngCoverImg, scaleRateW, scaleRateH);
			
			
			NextButtonImg = scaleImage(NextButtonImg, scaleRateW, scaleRateH);
			ToolBarImg = scaleImage(ToolBarImg, scaleRateW, scaleRateH);
			TrashcanImg = scaleImage(TrashcanImg, scaleRateW, scaleRateH);
			ClipboardImg = scaleImage(ClipboardImg, scaleRateW, scaleRateH);
			TrashImg = scaleImage(TrashImg, scaleRateW, scaleRateH);
			CursorImg = scaleImage(CursorImg,scaleRateW,scaleRateH);
			PlantImg = scaleImage(PlantImg,scaleRateW,scaleRateH);
			FishImg = scaleImage(FishImg,scaleRateW,scaleRateH);
			InvasiveImg = scaleImage(InvasiveImg,scaleRateW,scaleRateH);
			SpotImg[0] = scaleImage(SpotImg[0],scaleRateW,scaleRateH);
			SpotImg[1] = scaleImage(SpotImg[1],scaleRateW,scaleRateH);

			ruleImg[0] = scaleImage(ruleImg[0],scaleRateW*1.2,scaleRateH*1.2);
			ruleImg[1] = scaleImage(ruleImg[1],scaleRateW*1.2,scaleRateH*1.2);
			ruleImg[2] = scaleImage(ruleImg[2],scaleRateW*1.2,scaleRateH*1.2);

		
		}
		
	}
	
} 