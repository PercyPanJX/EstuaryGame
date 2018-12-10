package RESEARCHMVC;

import SUPERMVC.View;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.*;

/**
 * research game view including all displays methods
 * @author hongbozhan
 *
 */
public class ReaView extends View{
	
	JFrame ReaFrame;
	 
	// buttons needed
	JButton NextButton;
	JButton saveButton;
	
	int x,y;
	double rolly;
	
	ResearchGameState isGame = ResearchGameState.WAITING;

	// panels that are needed 
	WholePanel wholePanel;
	
	// game state
	private ResearchGameState GameState;
	
	boolean toRoll = false;
	double rateT = 1;
	
	// initial the view
	/**
	 * constructor of the game view
	 */
	ReaView() {
		super();
		
		// initiate the game state
		GameState = ResearchGameState.WAITING;
		
		//store all images 
		addImages();
		
		//initialize the buttons
		initialButtons();

		//initial the frame
		initialFrame();
		
		
		
	}

	/**
	 * give scale rate in width from view to model
	 * @return the scale rate in width
	 */
	public double getxRate() {
		return wholePanel.scaleRateW;
	}
	
	/**
	 * give scale rate in height from view to model
	 * @return the scale rate in height
	 */
	public double getyRate() {
		return wholePanel.scaleRateH;
	}
	
	/**
	 * initialize the frame of research game
	 */
	private void initialFrame() {
		ReaFrame = new JFrame ("Reaserch Game");
		ReaFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ReaFrame.setBackground(Color.BLACK);
		ReaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
		wholePanel = new WholePanel();
		ReaFrame.setContentPane(wholePanel);
		
		
	}
	
	/**
	 * initialize the button
	 */
	private void initialButtons() {
		NextButton = new JButton();		
		NextButton.setIcon(new ImageIcon(nextButtonImg)); //set button images
		NextButton.setBorder(null);	//set the none border
		NextButton.setOpaque(false);
		NextButton.setBackground(null);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor c = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
		NextButton.setCursor(c);
		
		saveButton = new JButton();		
		saveButton.setIcon(new ImageIcon(saveImg)); //set button images
		saveButton.setBorder(null);	//set the none border
		saveButton.setOpaque(false);
		saveButton.setBackground(null);
		saveButton.setCursor(c);
	}
		
	//show the cover for the game
	/**
	 * start the game and show the cover for the game first
	 */
	protected void startTheGame() {
		//show the cover and change the game state to covertime
		ReaFrame.setVisible(true);
		GameState = ResearchGameState.COVERTIME; 
	}	
	
	//stop the game
	/**
	 * stop the game so that the whole frame is invisible 
	 */
	protected void stop() {
		ReaFrame.setVisible(false);
	}
	
	//update the elements of game to show/
	/**
	 * update the view according to new elements gained from model
	 * @param elements all elements view needed
	 */
	protected void updateView(ReaElements elements) {
		wholePanel.repaint();
		GameState = elements.gameState;
		switch(GameState) {
		case WAITING:
			break;
		case COVERTIME:
			hideTheScore();
			showTheCover();
			isGame = ResearchGameState.COVERTIME;
			break;
		case CONSTRUCTION:
			hideTheScore();
			hideTheCover();
			isGame = ResearchGameState.CONSTRUCTION;
			showTheConstruction();
			break;
		case TUTORIAL:
			hideTheScore();
			hideTheConstruction();
			isGame = ResearchGameState.TUTORIAL;
			updateTutorial(elements);
			showTheTutorial();
			break;
		case RULETIME:
			hideTheScore();
			hideTheTutorial();
			isGame = ResearchGameState.RULETIME;	
			showTheRule();
			break;
		case GAMETIME:
			hideTheScore();
			setCurserDown();
			isGame = ResearchGameState.GAMETIME;
			hideTheRule();
			showTheGame();
			updateTheGame(elements);
			break;
		case SCORETIME:
			setCurser();
			isGame = ResearchGameState.SCORETIME;
			hideTheInfo();
			hideTheGame();
			showTheScore();
			updateTheScore(elements);
			break;
		case INFOTIME:
			isGame = ResearchGameState.INFOTIME;
			hideTheScore();
			showTheInfo(elements);
			break;
		case GAMESTOP:
			hideTheScore();
			hideTheInfo();
			hideTheGame();
			hideTheRule();
			hideTheConstruction();
			hideTheCover();
			break;
		}
	}
	
	private void hideTheTutorial() {
		wholePanel.hideTutorial();
	}

	private void showTheTutorial() {
		wholePanel.showTutorial();
	}

	// show the cover page
	/**
	 * show the cover page by calling function of panel
	 */
	private void showTheCover() {
		wholePanel.showCover();
	}
	
	// hide the cover page
	/**
	 * hide the cover page by calling function of panel
	 */
	private void hideTheCover() {
		wholePanel.hideCover();
	}
	
	/**
	 * show construction page by calling function of panel
	 */
	private void showTheConstruction() {
		wholePanel.showConstruction();
	}
	
	/**
	 * hide the construction page by calling function of panel
	 */
	private void hideTheConstruction() {
		wholePanel.hideConstruction();
	}
		
	//show the map for the map time
	/**
	 * show the rule page by calling function of panel
	 */
	private void showTheRule() {
		wholePanel.showRule();
	}
		
	//hide the map when map showing time up
	/**
	 * hide the map when the time is up by calling function of panel
	 */
	private void hideTheRule() {
		wholePanel.hideRule();
	}
		
	//show the environment when the game begin
	/**
	 * show the game by calling function of panel
	 */
	private void showTheGame() {
		wholePanel.showTheGame();
	}

	//hide all class elements when class time up
	/**
	 * hide the game frame by calling function of panel
	 */
	private void hideTheGame() {
		wholePanel.hideTheGame();
	}
	
	//show the score
	/**
	 * show the scoer page by calling function of panel
	 */
	private void showTheScore() {
		wholePanel.showTheScore();
	}

	//hide all class elements when class time up
	/**
	 * hide the score page by calling function of panel
	 */
	private void hideTheScore() {
		wholePanel.hideTheScore();
	}
	
	/**
	 * set the cursor to an image of downward arrow 
	 */
	private void setCurserDown() {
		
		wholePanel.curserDown();
	}
	
	/**
	 * set cursor back to original image
	 */
	private void setCurser() {
		wholePanel.curserBack();
	}
	
	private void updateTutorial(ReaElements elements) {
		wholePanel.updateTutorial(elements.tutorialTime);
	}
	
	//update the game time
	/**
	 * update the game according the elements gained from model by calling function of panel
	 * @param elements elements gained from model
	 */
	private void updateTheGame(ReaElements elements) {
		//wholePanel.updateHook(elements.hooky, elements.rolly);
		
		wholePanel.updateTime(elements.time);
		wholePanel.updateScore(elements.score);
		wholePanel.updateQuest(elements.sampleList[elements.score]);
		wholePanel.updateRollandHook(elements.rollRate);
		wholePanel.updatepb(elements.barPercent);
		
		
	}
	
	// update the score page
	/**
	 * update the score according to the elements gained from model
	 * @param elements elements needed by view from model
	 */
	private void updateTheScore(ReaElements elements) {
		wholePanel.updateScorePage(elements.score);
		
	}
	
	// show the info page
	/**
	 * show the information page according to the elements from model
	 * @param elements elements needed by view from model
	 */
	private void showTheInfo(ReaElements elements) {
		wholePanel.showInfo(elements.infoNum);
	}
	
	// hide the info page
	/**
	 * hide the information by calling the function of panel
	 */
	private void hideTheInfo() {
		wholePanel.hideInfo();
	}
	
	//Store using images
	/**
	 * store all images 
	 */
	private void addImages() {	 
		try {
			backgroundImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/NormalBackground.png")).openStream());
			questImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Quest.png")).openStream());
			ruleImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/RuleImage.png")).openStream());
			barImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Bar.png")).openStream());
			nextButtonImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/NextButtonImage.png")).openStream());
			gameImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/GameImage.png")).openStream());
			scoreImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/ScoreImage.png")).openStream());
			machineImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Machine.png")).openStream());
			hookImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Hook.png")).openStream());
			scoreimg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Score.png")).openStream());
			timeImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Time.png")).openStream());
			rollImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Roll.png")).openStream());
			gameBackgroundImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/gamebg.jpeg")).openStream());
			coverImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/cover.jpeg")).openStream());
			constructionImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Construction.jpeg")).openStream());
			CursorImg = ImageIO.read((getClass().getResource("/Source/Images/Cursor.png")).openStream());
			pbImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/PB.png")).openStream());
			downImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/downArrow.png")).openStream());
			saveImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/save.png")).openStream());
			bgbgImg = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/bgbg.jpg")).openStream());
			
			for(int i=0; i<10; i++) {
				samplesImg[i] = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Sample"+ Integer.toString(i+1) + ".png")).openStream());				
			}
			
			for(int i=0; i <4; i++) {
				layerImg[i] = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/Layer"+ Integer.toString(i+1) + ".png")).openStream());
			}
			
			for(int i=0; i<10; i++) {
				infoImg[i] = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/InfoImg"+ Integer.toString(i+1) + ".png")).openStream());				
			}
			
			for(int i=0; i<17; i++) {
				tutorialImg[i] = ImageIO.read((getClass().getResource("/Source/Images/Reaserch/tutorial"+ Integer.toString(i+1) + ".png")).openStream());				
			}
				
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	//set the listener to buttons
	/**
	 * set button listeners
	 * @param ac4Next button of next button
	 * @param ac4Sample button for samples
	 * @param ac4Save button for serializable
	 */
	protected void setButtonListener(ActionListener ac4Next, ActionListener[] ac4Sample, ActionListener ac4Save) {
		NextButton.addActionListener(ac4Next);
		for (int i = 0; i < 10 ; i++) {
			wholePanel.sampleButtons[i].addActionListener(ac4Sample[i]);
		}
		saveButton.addActionListener(ac4Save);
		
	}
	
	/**
	 * add a mouse listener
	 * @param mouseListener add mouse listener
	 */
	public void setMouseListener(MouseListener mouseListener) {
		ReaFrame.addMouseListener(mouseListener);
		addMouseListener(mouseListener);
	}
	
	
	
	
//////////////////Panel /////////////////////////////////
	//background panel for the whole rea game
	//All images for the reaserch game
		BufferedImage backgroundImg, questImg, ruleImg, barImg, 
						nextButtonImg, gameImg, scoreImg, machineImg,
						hookImg, scoreimg, timeImg, rollImg, gameBackgroundImg,
						coverImg, constructionImg, CursorImg,pbImg, downImg, saveImg, bgbgImg;
		BufferedImage[] samplesImg = new BufferedImage[10];
		BufferedImage[] infoImg = new BufferedImage[10];
		BufferedImage[] layerImg = new BufferedImage[4];
		BufferedImage[] tutorialImg = new BufferedImage[17];
	
	
	private class WholePanel extends JPanel{
		private JPanel btnPanel = new JPanel();
		private JLabel ruleLabel = new JLabel();
		private JLabel gameLabel = new JLabel();
		private JLabel scoreLabel = new JLabel();
		private JLabel infoLabel = new JLabel();
		private JLabel layerOne = new JLabel();
		private JLabel layerTwo = new JLabel();
		private JLabel layerThree = new JLabel();
		private JLabel layerFour = new JLabel();
		private JLabel machine = new JLabel();
		private JLabel hook = new JLabel();
		private JLabel quest = new JLabel();
		private JLabel bar = new JLabel();
		private JLabel score = new JLabel();
		private JLabel time = new JLabel();
		private JLabel roll = new JLabel();
		private JLabel timeLabel = new JLabel();
		private JLabel scoreNum = new JLabel();
		private JLabel depth = new JLabel();
		private JLabel scoreNumber = new JLabel();
		private JLabel construction = new JLabel();
		private JButton[] sampleButtons = new JButton[10];
		private JLabel[] sampleInfo = new JLabel[10];
		private JLabel cover = new JLabel();
		private JLabel[] tutorial = new JLabel[10];
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		private int windowW = (int)bounds.getWidth();
		private int windowH = (int)bounds.getHeight();
		private double scaleRateW;
		private double scaleRateH;
		
		private JLabel progressBar = new JLabel();
		
		
			
		/**
		 * create buttons for samples
		 */
		private void createSampleButtons() {
			
			for (int i=0; i<10; i++) {
				sampleButtons[i] = new JButton();		
				sampleButtons[i].setIcon(new ImageIcon(samplesImg[i])); //set button images
				sampleButtons[i].setBorder(null);	//set the none border
				sampleButtons[i].setOpaque(false);
				sampleButtons[i].setBackground(null);
				sampleButtons[i].setVisible(false);
				sampleButtons[i].setContentAreaFilled(false);
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Cursor c = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
				sampleButtons[i].setCursor(c);
			}
			
			// samples on the score page
				GridBagConstraints gbc4sample1 = createGridConstraints((int) (scaleRateH * 50),0,0,(int) (scaleRateW * 750),GridBagConstraints.CENTER);
				this.add(sampleButtons[0],gbc4sample1);
				GridBagConstraints gbc4sample2 = createGridConstraints((int) (scaleRateH * 50),0,0,(int) (scaleRateW * 450),GridBagConstraints.CENTER);
				this.add(sampleButtons[1],gbc4sample2);
				GridBagConstraints gbc4sample3 = createGridConstraints((int) (scaleRateH * 50),0,0,(int) (scaleRateW * 150),GridBagConstraints.CENTER);
				this.add(sampleButtons[2],gbc4sample3);
				GridBagConstraints gbc4sample4 = createGridConstraints((int) (scaleRateH * 50),(int) (scaleRateW * 150),0,0,GridBagConstraints.CENTER);
				this.add(sampleButtons[3],gbc4sample4);
				GridBagConstraints gbc4sample5 = createGridConstraints((int) (scaleRateH * 50),(int) (scaleRateW * 450),0,0,GridBagConstraints.CENTER);
				this.add(sampleButtons[4],gbc4sample5);
				GridBagConstraints gbc4sample6 = createGridConstraints((int) (scaleRateH * 50),(int) (scaleRateW * 750),0,0,GridBagConstraints.CENTER);
				this.add(sampleButtons[5],gbc4sample6);
				
		}
		
		/**
		 * set cursor image back to normal image
		 */
		public void curserBack() {
			this.setCursor(null);
			
		}

		/**
		 * set cursor image to an arrow
		 */
		public void curserDown() {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Cursor c = toolkit.createCustomCursor(downImg, new Point(downImg.getWidth()/2,downImg.getHeight()/2), "custom cursor");
			this.setCursor(c);
			
		}


		//main panel constructor
		/**
		 * constructor of the whole panel
		 */
		public WholePanel() {
				
			//background color
			setBackground(null);
			
			// scale all images
			scaleAllImages();
				
			//initial and show the Next button 
			createButtonPanel();
			
			// initial the construction
			createConstruction();
			
			// initial the tutorial
			createTutorial();
			
			// initial the cover
			createCover();
				
			//initial the rule image
			createRule();
				
			//initial the elements for the class environment
			createGame();
	
			// initiate the score page
			createScore();
			
			// create the info page
			createInfo();		
		}
		
		
		
		//paint method for the background
		/**
		 * paint method for the background
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
				
			//add the background in the center
			Graphics2D g2d = (Graphics2D) g;
			int x = (int) ((657 / (double) 1200) * windowW);
			
			int y = (int) ((202 / (double) 675) * windowH);
			int gamebgx = (this.getWidth() - gameBackgroundImg.getWidth(null)) / 2;
			int gamebgy = (this.getHeight() - gameBackgroundImg.getHeight(null)) / 2;
			int tx = (this.getWidth() - bgbgImg.getWidth(null)) / 2;
			int ty = (this.getHeight() - bgbgImg.getHeight(null)) / 2;
			switch(isGame) {
			case COVERTIME:
				break;
			case CONSTRUCTION:
				break;
			case TUTORIAL:
				g2d.drawImage(bgbgImg, tx, ty, null);
				break;
			case RULETIME:
				//g2d.drawImage(gameBackgroundImg, gamebgx, gamebgy, null);
				break;
			case GAMETIME:
				g2d.drawImage(gameBackgroundImg, gamebgx, gamebgy, null);
				break;
			case SCORETIME:
				//g2d.drawImage(backgroundImg, x, y, null);
				break;
			case INFOTIME:
				//g2d.drawImage(backgroundImg, x, y, null);
				break;
			} 
			
			if (toRoll == true) {
				g.drawImage(scaleImage(rollImg, 1, rateT), x, y,null);
				g.drawImage(hookImg, x-1,(int) (y + ((scaleRateH * 40) * rateT)),null);
				
			}
		}
		
		//add button on the panel
		/**
		 * add button panel to the panel
		 */
		private void createButtonPanel() {
			//first layout set for the frame
			this.setLayout(new GridBagLayout());
					
			//button adding
			GridBagConstraints gbc4Bottons = createGridConstraints((int) (scaleRateH * 590),(int) (scaleRateW * 1090),0,0,GridBagConstraints.CENTER);			
			this.add(NextButton,gbc4Bottons);
			GridBagConstraints gbc4Save = createGridConstraints(0,0,0,0,GridBagConstraints.NORTHEAST);			
			this.add(saveButton,gbc4Save);
		}
		
		/**
		 * create the cover page
		 */
		private void createCover() {
			cover.setIcon(new ImageIcon(coverImg));
			cover.setOpaque(false);
			GridBagConstraints gbc4cover = createGridConstraints(0,0,0,0,GridBagConstraints.CENTER);
			this.add(cover,gbc4cover);
			cover.setVisible(false);
		}
		
		// create the construction
		/**
		 * create the construction
		 */
		private void createConstruction() {
			construction.setIcon(new ImageIcon(constructionImg));
			construction.setOpaque(false);
			GridBagConstraints gbc4construction = createGridConstraints(0,0,0,0,GridBagConstraints.CENTER);
			this.add(construction,gbc4construction);
			construction.setVisible(false);
		}
		
		private void createTutorial() {
			tutorial[0] = new JLabel();
			tutorial[1] = new JLabel();
			tutorial[2] = new JLabel();
			tutorial[3] = new JLabel();
			tutorial[4] = new JLabel();
			tutorial[5] = new JLabel();
			tutorial[6] = new JLabel();
			tutorial[7] = new JLabel();
			tutorial[8] = new JLabel();
			tutorial[9] = new JLabel();
			
			tutorial[0].setIcon(new ImageIcon(tutorialImg[0]));
			tutorial[0].setOpaque(false);
			GridBagConstraints gbc4t1 = createGridConstraints(0,0,(int) (scaleRateH * 200),(int) (scaleRateW * 900),GridBagConstraints.CENTER);
			this.add(tutorial[0],gbc4t1);
			tutorial[0].setVisible(false);
			
			tutorial[1].setIcon(new ImageIcon(tutorialImg[0]));
			tutorial[1].setOpaque(false);
			GridBagConstraints gbc4t2 = createGridConstraints(0,0,(int) (scaleRateH * 200),(int) (scaleRateW * 500),GridBagConstraints.CENTER);
			this.add(tutorial[1],gbc4t2);
			tutorial[1].setVisible(false);
			
			tutorial[2].setIcon(new ImageIcon(tutorialImg[0]));
			tutorial[2].setOpaque(false);
			GridBagConstraints gbc4t3 = createGridConstraints(0,0,(int) (scaleRateH * 200),(int) (scaleRateW * 100),GridBagConstraints.CENTER);
			this.add(tutorial[2],gbc4t3);
			tutorial[2].setVisible(false);
			
			tutorial[3].setIcon(new ImageIcon(tutorialImg[8]));
			tutorial[3].setOpaque(false);
			GridBagConstraints gbc4t4 = createGridConstraints((int) (scaleRateH * 350),0,0,(int) (scaleRateW * 900),GridBagConstraints.CENTER);
			this.add(tutorial[3],gbc4t4);
			tutorial[3].setVisible(false);
			
			tutorial[4].setIcon(new ImageIcon(tutorialImg[9]));
			tutorial[4].setOpaque(false);
			GridBagConstraints gbc4t5 = createGridConstraints((int) (scaleRateH * 350),0,0,(int) (scaleRateW * 500),GridBagConstraints.CENTER);
			this.add(tutorial[4],gbc4t5);
			tutorial[4].setVisible(false);
			
			tutorial[5].setIcon(new ImageIcon(tutorialImg[7]));
			tutorial[5].setOpaque(false);
			GridBagConstraints gbc4t6 = createGridConstraints((int) (scaleRateH * 350),0,0,(int) (scaleRateW * 100),GridBagConstraints.CENTER);
			this.add(tutorial[5],gbc4t6);
			tutorial[5].setVisible(false);
			
			tutorial[6].setIcon(new ImageIcon(tutorialImg[1]));
			tutorial[6].setOpaque(false);
			GridBagConstraints gbc4t7 = createGridConstraints(0,(int) (scaleRateW * 500),(int) (scaleRateH * 200),0,GridBagConstraints.CENTER);
			this.add(tutorial[6],gbc4t7);
			tutorial[6].setVisible(false);
			
			tutorial[7].setIcon(new ImageIcon(tutorialImg[10]));
			tutorial[7].setOpaque(false);
			GridBagConstraints gbc4t8 = createGridConstraints(0,(int) (scaleRateW * 900),(int) (scaleRateH * 400),0,GridBagConstraints.CENTER);
			this.add(tutorial[7],gbc4t8);
			tutorial[7].setVisible(false);
			
			tutorial[8].setIcon(new ImageIcon(tutorialImg[11]));
			tutorial[8].setOpaque(false);
			GridBagConstraints gbc4t9 = createGridConstraints((int) (scaleRateH * 350),(int) (scaleRateW * 700),0,0,GridBagConstraints.CENTER);
			this.add(tutorial[8],gbc4t9);
			tutorial[8].setVisible(false);
			
			tutorial[9].setIcon(new ImageIcon(tutorialImg[16]));
			tutorial[9].setOpaque(false);
			GridBagConstraints gbc4t10 = createGridConstraints(0,(int) (scaleRateW * 900),(int) (scaleRateH * 100),0,GridBagConstraints.CENTER);
			this.add(tutorial[9],gbc4t10);
			tutorial[9].setVisible(false);
			
		}
		
		//initial the map image
		/**
		 * initial the map image
		 */
		private void createRule() {
			ruleLabel.setIcon(new ImageIcon(ruleImg));
			ruleLabel.setOpaque(false);
			GridBagConstraints gbc4Map = createGridConstraints(0,0,0,0,GridBagConstraints.CENTER);
			this.add(ruleLabel,gbc4Map);
			ruleLabel.setVisible(false);
		}
		
		//initial the map image
		/**
		 * initial the map image
		 */
		private void createInfo() {
			for (int i = 0; i < 10; i++) {
				sampleInfo[i] = new JLabel();
				sampleInfo[i].setIcon(new ImageIcon(infoImg[i]));
				sampleInfo[i].setOpaque(false);
				GridBagConstraints gbc4sampleInfo = createGridConstraints(0,0,(int) (scaleRateH * 50),0,GridBagConstraints.CENTER);
				this.add(sampleInfo[i],gbc4sampleInfo);
				sampleInfo[i].setVisible(false);
				
			}
		}
		
		//show the map
		/**
		 * show the rule page
		 */
		protected void showRule() {
			ruleLabel.setVisible(true);
		}
				
		//hide the map
		/**
		 * hide the rule page
		 */
		protected void hideRule() {
			ruleLabel.setVisible(false);
		}	
		
		//create elements for the class
		/**
		 * create all elements for the class
		 */
		private void createGame() {
			progressBar.setBackground(null);
			progressBar.setOpaque(false);
			progressBar.setPreferredSize(new Dimension(pbImg.getWidth(), pbImg.getHeight()));
			GridBagConstraints gbc4pb = createGridConstraints(0,(int) (scaleRateW * 40),(int) (scaleRateH * 600),0,GridBagConstraints.CENTER);
			this.add(progressBar,gbc4pb);
			Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
			progressBar.setBorder(border);
			progressBar.setIcon(new ImageIcon(pbImg));
			
			
			progressBar.setVisible(false);
		
			
			//time
			time.setIcon(new ImageIcon(timeImg));
			time.setOpaque(false);
			GridBagConstraints gbc4time = createGridConstraints(0,(int) (scaleRateW * 360),(int) (scaleRateH * 540),0,GridBagConstraints.CENTER);
			this.add(time,gbc4time);
			time.setVisible(false);
			
			// timeText
			timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.PLAIN, (int) (scaleRateW * 48)));
			GridBagConstraints gbc4timeLabel = createGridConstraints(0,(int) (scaleRateW * 360),(int) (scaleRateH * 540),0,GridBagConstraints.CENTER);
			this.add(timeLabel,gbc4timeLabel);
			timeLabel.setOpaque(false);
			timeLabel.setVisible(false);
			
			
			// hook
			hook.setIcon(new ImageIcon(hookImg));
			hook.setOpaque(false);
			GridBagConstraints gbc4hook = createGridConstraints(0,(int) (scaleRateW * 136),(int) (scaleRateH * 170),0,GridBagConstraints.CENTER);
			this.add(hook,gbc4hook);
			hook.setVisible(false);
			
			
			// machine
			machine.setIcon(new ImageIcon(machineImg));
			machine.setOpaque(false);
			GridBagConstraints gbc4machine = createGridConstraints(0,0,(int) (scaleRateH * 235),0,GridBagConstraints.CENTER);
			this.add(machine,gbc4machine);
			machine.setVisible(false);
			
			// quest
			quest.setIcon(new ImageIcon(questImg));
			quest.setOpaque(false);
			GridBagConstraints gbc4quest = createGridConstraints(0,0,(int) (scaleRateH * 540),(int) (scaleRateW * 350),GridBagConstraints.CENTER);
			this.add(quest,gbc4quest);
			quest.setVisible(false);
			
			// bar
			bar.setIcon(new ImageIcon(barImg));
			bar.setOpaque(false);
			GridBagConstraints gbc4bar = createGridConstraints(0,(int) (scaleRateW * 40),(int) (scaleRateH * 600),0,GridBagConstraints.CENTER);
			this.add(bar,gbc4bar);
			bar.setVisible(false);
			
			// score
			score.setIcon(new ImageIcon(scoreimg));
			score.setOpaque(false);
			GridBagConstraints gbc4score = createGridConstraints(0,(int) (scaleRateW * 40),(int) (scaleRateH * 480),0,GridBagConstraints.CENTER);
			this.add(score,gbc4score);
			score.setVisible(false);
			
			// score number
			scoreNum.setOpaque(false);
			scoreNum.setFont(new Font(scoreNum.getFont().getName(), Font.PLAIN, (int) (scaleRateW * 36)));
			GridBagConstraints gbc4scoreNum = createGridConstraints(0,(int) (scaleRateW * 180),(int) (scaleRateH * 482),0,GridBagConstraints.CENTER);
			this.add(scoreNum,gbc4scoreNum);
			scoreNum.setVisible(false);
			
			
			
			// sample depth
			depth.setFont(new Font(depth.getFont().getName(), Font.PLAIN, (int) (scaleRateW * 36)));
			GridBagConstraints gbc4depth = createGridConstraints(0,0,(int) (scaleRateH * 500),(int) (scaleRateW * 350),GridBagConstraints.CENTER);
			this.add(depth,gbc4depth);
			depth.setOpaque(false);
			depth.setVisible(false);
			
		}
		
		// create score page
		/**
		 * create score page
		 */
		private void createScore() {
			
			// score number
			scoreNumber.setOpaque(false);
			scoreNumber.setFont(new Font(scoreNumber.getFont().getName(), Font.PLAIN, (int) (scaleRateW * 36)));
			scoreNumber.setForeground(Color.red);
			GridBagConstraints gbc4sn = createGridConstraints(0,0,(int) (scaleRateH * 232),(int) (scaleRateW * 50),GridBagConstraints.CENTER);
			this.add(scoreNumber,gbc4sn);
			scoreNumber.setVisible(false);
			
			createSampleButtons();
			
			// score page with text
			scoreLabel.setIcon(new ImageIcon(scoreImg));
			scoreLabel.setOpaque(false);
			GridBagConstraints gbc4Map = createGridConstraints(0,0,0,0,GridBagConstraints.CENTER);
			this.add(scoreLabel,gbc4Map);
			scoreLabel.setVisible(false);
			
		}
		
		/**
		 * show the cover page
		 */
		protected void showCover() {
			cover.setVisible(true);
		
		}
		
		/**
		 * hide the cover page
		 */
		protected void hideCover() {
			cover.setVisible(false);
		}
		
		/**
		 * show the construction page
		 */
		protected void showConstruction() {
			construction.setVisible(true);
		}
		
		/**
		 * hide the consteuction
		 */
		protected void hideConstruction() {
			construction.setVisible(false);
		}
		
		
		public void showTutorial() {
			// TODO Auto-generated method stub
			tutorial[0].setVisible(true);
			tutorial[1].setVisible(true);
			tutorial[2].setVisible(true);
			tutorial[3].setVisible(true);
			tutorial[4].setVisible(true);
			tutorial[5].setVisible(true);
			tutorial[6].setVisible(true);
			tutorial[7].setVisible(true);
			tutorial[8].setVisible(true);
			tutorial[9].setVisible(true);
			
		}


		public void hideTutorial() {
			// TODO Auto-generated method stub
			tutorial[0].setVisible(false);
			tutorial[1].setVisible(false);
			tutorial[2].setVisible(false);
			tutorial[3].setVisible(false);
			tutorial[4].setVisible(false);
			tutorial[5].setVisible(false);
			tutorial[6].setVisible(false);
			tutorial[7].setVisible(false);
			tutorial[8].setVisible(false);
			tutorial[9].setVisible(false);
		}
		
		public void updateTutorial(int time) {
			// TODO Auto-generated method stub
			if(time > 24) {
				tutorial[0].setIcon(new ImageIcon(tutorialImg[6]));
			} else if (time > 20) {
				tutorial[0].setIcon(new ImageIcon(tutorialImg[5]));
			} else if (time > 16) {
				tutorial[0].setIcon(new ImageIcon(tutorialImg[4]));
			} else if (time > 12) {
				tutorial[0].setIcon(new ImageIcon(tutorialImg[3]));
			} else if (time > 8) {
				tutorial[0].setIcon(new ImageIcon(tutorialImg[2]));
			} else if (time > 4) {
				tutorial[0].setIcon(new ImageIcon(tutorialImg[1]));
			} else if (time > 0) {
				tutorial[0].setIcon(new ImageIcon(tutorialImg[0]));
			}
			
			if(time > 60) {
				tutorial[1].setIcon(new ImageIcon(tutorialImg[6]));
			} else if (time > 50) {
				tutorial[1].setIcon(new ImageIcon(tutorialImg[5]));
			} else if (time > 40) {
				tutorial[1].setIcon(new ImageIcon(tutorialImg[4]));
			} else if (time > 30) {
				tutorial[1].setIcon(new ImageIcon(tutorialImg[3]));
			} else if (time > 20) {
				tutorial[1].setIcon(new ImageIcon(tutorialImg[2]));
			} else if (time > 10) {
				tutorial[1].setIcon(new ImageIcon(tutorialImg[1]));
			} else if (time > 0) {
				tutorial[1].setIcon(new ImageIcon(tutorialImg[0]));
			}
			
			if(time > 60) {
				tutorial[2].setIcon(new ImageIcon(tutorialImg[0]));
			} else if (time > 50) {
				tutorial[2].setIcon(new ImageIcon(tutorialImg[1]));
			} else if (time > 40) {
				tutorial[2].setIcon(new ImageIcon(tutorialImg[2]));
			} else if (time > 30) {
				tutorial[2].setIcon(new ImageIcon(tutorialImg[3]));
			} else if (time > 20) {
				tutorial[2].setIcon(new ImageIcon(tutorialImg[4]));
			} else if (time > 10) {
				tutorial[2].setIcon(new ImageIcon(tutorialImg[5]));
			} else if (time > 0) {
				tutorial[2].setIcon(new ImageIcon(tutorialImg[6]));
			}
			
			if(time > 60) {
				tutorial[8].setIcon(new ImageIcon(tutorialImg[15]));
			} else if (time > 45) {
				tutorial[8].setIcon(new ImageIcon(tutorialImg[14]));
			} else if (time > 30) {
				tutorial[8].setIcon(new ImageIcon(tutorialImg[13]));
			} else if (time > 15) {
				tutorial[8].setIcon(new ImageIcon(tutorialImg[12]));
			} else if (time > 0) {
				tutorial[8].setIcon(new ImageIcon(tutorialImg[11]));
			}
			
		}

		
		//show all elements for the class
		/**
		 * show the game page
		 */
		protected void showTheGame() {
			machine.setVisible(true);
			quest.setVisible(true);
			bar.setVisible(true);
			score.setVisible(true);
			time.setVisible(true);
			timeLabel.setVisible(true);
			scoreNum.setVisible(true);
			depth.setVisible(true);
			progressBar.setVisible(true);
			toRoll = true;
		}
		
		//hide all elements for the class
		/**
		 * hide the game page
		 */
		protected void hideTheGame() {
			machine.setVisible(false);
			quest.setVisible(false);
			bar.setVisible(false);
			score.setVisible(false);
			time.setVisible(false);
			timeLabel.setVisible(false);
			scoreNum.setVisible(false);
			depth.setVisible(false);
			progressBar.setVisible(false);
			toRoll = false;
		}
		
		//show the score page
		/**
		 * show the score page 
		 */
		protected void showTheScore() {
			scoreLabel.setVisible(true);
			scoreNumber.setVisible(true);
			for (int i = 0; i < 10; i++) {
				sampleButtons[i].setVisible(true);
			}
			
		}
				
		//hide the score page
		/**
		 *  hide the score page 
		 */
		protected void hideTheScore() {
			scoreLabel.setVisible(false);
			scoreNumber.setVisible(false);
			for (int i = 0; i < 10; i++) {
				sampleButtons[i].setVisible(false);
			}
		}
		
		
		//show the info page
		/**
		 * show the information of specific sample
		 * @param i show the specific sample according to the number
		 */
		protected void showInfo(int i) {
			sampleInfo[i].setVisible(true);
		}
						
		//hide the info page
		/**
		 * hide the information page
		 */
		protected void hideInfo() {
			for (int i = 0; i < 6; i++) {
				sampleInfo[i].setVisible(false);
			}
		}
		
		/**
		 * update the score during the game time 
		 * @param scoreNum the number of score
		 */
		protected void updateScorePage(int scoreNum) {
			scoreNumber.setText(Integer.toString(scoreNum));
		}
		
		/**
		 * update the time showed during the game
		 * @param t the current game time 
		 */
		protected void updateTime(double t) {
			//timeLabel.setVisible(false);
			timeLabel.setText(Integer.toString((int) t));
			//timeLabel.setVisible(true);
			
		}
		
		/**
		 * update the score during the game
		 * @param s score number
		 */
		protected void updateScore(int s) {
			scoreNum.setText(Integer.toString(s));
		}
		
		/**
		 * update the quest of the game to show the depth of the sample
		 * @param s the current sample game is looking for 
		 */
		protected void updateQuest(sample s) {
			depth.setText(Integer.toString(s.depth));
		}
		
		/**
		 * show the progress bar according the percentage
		 * @param percent
		 */
		protected void updatepb(int percent) {
			progressBar.setIcon(new ImageIcon(scaleImage(pbImg, (percent / (double) 100)+0.01,1)));
		}
		
		
		int yloc = 0;
		
		/**
		 * update roll and hook according to the rate 
		 * @param rate scale rate  needed for hook and roll
		 */
		protected void updateRollandHook(double rate) {
			rateT = rate;
			
		}
		
		/**
		 * scale all image according to the scale rate of width and height
		 */
		private void scaleAllImages() {
			scaleRateW = windowW / (double) 1200;
			scaleRateH = windowH / (double) 675;
			
			// resize all images according to the window size
			
			backgroundImg = scaleImage(backgroundImg,scaleRateW,scaleRateH);
			questImg = scaleImage(questImg,scaleRateW,scaleRateH);
			ruleImg = scaleImage(ruleImg,scaleRateW,scaleRateH);
			barImg = scaleImage(barImg,scaleRateW,scaleRateH);
			nextButtonImg = scaleImage(nextButtonImg,scaleRateW,scaleRateH);
			gameImg = scaleImage(gameImg,scaleRateW,scaleRateH);
			scoreImg = scaleImage(scoreImg,scaleRateW,scaleRateH);
			machineImg = scaleImage(machineImg,scaleRateW,scaleRateH);
			hookImg = scaleImage(hookImg,scaleRateW,scaleRateH);
			scoreimg = scaleImage(scoreimg,scaleRateW,scaleRateH);
			timeImg = scaleImage(timeImg,scaleRateW,scaleRateH);
			rollImg = scaleImage(rollImg,scaleRateW,scaleRateH);
			gameBackgroundImg = scaleImage(gameBackgroundImg,scaleRateW,scaleRateH);
			coverImg =  scaleImage(coverImg,scaleRateW,scaleRateH);
			constructionImg = scaleImage(constructionImg,scaleRateW,scaleRateH);
			CursorImg = scaleImage(CursorImg,scaleRateW,scaleRateH);
			pbImg = scaleImage(pbImg,scaleRateW,scaleRateH);
			downImg = scaleImage(downImg,scaleRateW*2,scaleRateH*2);
			bgbgImg = scaleImage(bgbgImg,scaleRateW,scaleRateH);
			
			for(int i=0; i<10; i++) {
				samplesImg[i] = scaleImage(samplesImg[i],scaleRateW,scaleRateH);				
			}
			
			for(int i=0; i <4; i++) {
				layerImg[i] = scaleImage(layerImg[i],scaleRateW,scaleRateH);
			}
			
			for(int i=0; i<10; i++) {
				infoImg[i] = scaleImage(infoImg[i],scaleRateW,scaleRateH);				
			}
			
			for(int i=0; i<17; i++) {
				tutorialImg[i] = scaleImage(tutorialImg[i],scaleRateW,scaleRateH);				
			}
			
			
		}
		
		
		
	}


	

	


}
