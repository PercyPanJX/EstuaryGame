package EDUMVC;
import SUPERMVC.View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Educational game view including all displays methods
 * @author percypan
 *
 */
public class EduView extends View{
	
	//whole frame
	JFrame EduFrame;
	

	//main panel for the painting
	MainPanel mainPanel;
	
	//store the game state
	private EducationalGameState GameState;
	
	/**
	 * Constructor including initializing all elements
	 */
	EduView() {
		
		//initial game state is waiting for the game begin
		GameState = EducationalGameState.WAITING;
		
		//initial the frame
		initialTheFrame();
		
	}
	
	/**
	 * initialize the main panel and the frame
	 */
	private void initialTheFrame(){
		//initialize the frame
		EduFrame = new JFrame("Estaury Educational Game");
		EduFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		EduFrame.setBackground(Color.BLACK);
		
		//set the panel for the background
		mainPanel = new MainPanel();
		
		//set the background panel for the frame
		EduFrame.setContentPane(mainPanel);
		
		EduFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	
	/**
	 * Start the game, show the panel and set to the cover time
	 */
	protected void startTheGame() {
		//show the cover and change the game state to cover time
		EduFrame.setVisible(true);
		GameState = EducationalGameState.COVERTIME;
		initialValues() ;
	}

	/**
	 * Stop the game, hide the last elements and set unvisible for the frame
	 */
	protected void stop() {
		mainPanel.hideStars();
		EduFrame.setVisible(true);
	}
	
	/**
	 * update the view according to the elements from the model
	 * @param elements all elements the view need from the model
	 */
	protected void updateView(EduElements elements) {
		mainPanel.repaint();

		GameState = elements.GameState;
		switch(GameState) {
		case WAITING:
			initialValues();
			break;
		case COVERTIME:
			showCover();
			break;
		case RULETIME:
			hideCover();
			showTheRule();
			break;
		case MAPTIME:
			hideTheRule();
			showTheMap();
			break;
		case BEGINNING:
			hideTheMap();
			showTheClassEnvironment();
			beginningTime(elements);
			break;
		case CLASSTIME:
			hideDarkCover();
			showTheClassEnvironment();
			updateTheClass(elements);
			break;
		case ENDING:
			showEndingCover();
			break;
		case SCORETIME:
			hideDarkCover();
			hideAllClassElements();	
			showStars(elements);
			break;
		case GAMESTOP:
			stop();
			break;
		}
	}
	
	/**
	 * resetAllValues
	 */
	private void initialValues() {
		mainPanel.initialValues();
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
	 * show the rule element
	 */
	private void showTheRule() {
		mainPanel.showTheRule();
	}
	
	/**
	 * hide the rule element
	 */
	private void hideTheRule() {
		mainPanel.hideTheRule();
	}
	
	/**
	 * show the map element
	 */
	private void showTheMap() {
		mainPanel.showMap();
	}
	
	/**
	 * hide the map element
	 */
	private void hideTheMap() {
		mainPanel.hideMap();
	}
	
	/**
	 * show the class environment
	 */
	private void showTheClassEnvironment() {
		mainPanel.showTheClassEnvironment();
	}
	
	/**
	 * Beginning dark cover shwowing
	 * @param e elements from the model
	 */
	private void beginningTime(EduElements e) {
		mainPanel.setDarkCoverTime(e.DarkCoverTimer);
	}
	
	/**
	 * hide the dark cover
	 */
	private void hideDarkCover() {
		mainPanel.hideDarkCoverTime();
	}
	
	/**
	 * hide all elements about the class
	 */
	private void hideAllClassElements() {
		mainPanel.hideTheClassEnvironment();
		mainPanel.hideAllAnswerList();
	}
	
	/**
	 * update the elements on the class
	 * @param elements elements from the model
	 */
	private void updateTheClass(EduElements elements) {
		mainPanel.ChangeTheAnswerListImg(elements.AnswersList);
		mainPanel.showTheAnswerList(elements.AnswersList);
		mainPanel.showTheQuestion(elements.QuestionStudent, elements.Question);
		mainPanel.studentRaisingHand(elements.QuestionStudent);
		mainPanel.updateTimeAndScore((int)elements.ClassTime, elements.Score);
		if(elements.AnswerTimer > 0.00) {
			double rate = 1 - elements.AnswerTimer;
			if(rate > 0.5) rate = elements.AnswerTimer; 
			mainPanel.teacherUp(elements.isCorrect, 2*rate);
		}
		
		else mainPanel.teacherDown();
	}
	
	/**
	 * when the class over, showing the ending dark cover
	 */
	private void showEndingCover() {
		mainPanel.showGameEndCover();
	}
	
	/**
	 * Set the button listeners on the panel
	 * @param ac4Next buttons listener for the 'next' button
	 * @param AL Action listener for the answers list
	 * @param saveButton Action listener for the save button
	 */
	protected void setButtonListener(ActionListener ac4Next, ArrayList<ActionListener> AL, ActionListener saveButton) {
		mainPanel.setListeners(ac4Next, AL, saveButton);
		
	}
	
	/**
	 * Showing the stars
	 * @param e elements from the model
	 */
	private void showStars(EduElements e) {
		mainPanel.showStars(e.StarsNum);
	}
	
////////////////// Panel /////////////////////////////////
	/**
	 * mainPanel contains all elements for the display
	 * @author percypan
	 *
	 */
	private class MainPanel extends JPanel{
		private JLabel CoverLabel = new JLabel();
		private JLabel RuleLabel = new JLabel();
		private JLabel MapLabel = new JLabel();
		private JLabel MiniMapLabel = new JLabel();
		private JLabel TeacherLabel = new JLabel();
		private JLabel TimeLabel = new JLabel();
		private JLabel DarkCover = new JLabel();
		private JLabel CenterText = new JLabel();
		private JLabel CenterText4Stars = new JLabel();
		private JLabel ruleWord = new JLabel();
		
		private JLabel [] Stars = new JLabel[3];
		private JLabel[] StudentLabel = new JLabel[3];
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		private int windowW = (int)bounds.getWidth();
		private int windowH = (int)bounds.getHeight();
		private double scaleRateW;
		private double scaleRateH;
		
		//Variables for the question moving
		boolean questionToShow = false;
		int questionStudent = 0;
		Animal QuestionAnimal = null;
		private double [] oldX = new double[3];
		private double [] oldY = new double[3];
		private double move = 0.00;
		private boolean goOrBack = true;
		private double StarScale = 2.00;
		
		//All images for the edu game
		BufferedImage BackgroundImg, EduCoverImg, RuleImg, NextButtonImg, MapImg, MiniMapImg;
		BufferedImage[][] StudentsImgUpAndDown = new BufferedImage[3][2];
		BufferedImage[][] StudentsQuestionImgD = new BufferedImage[3][4]; //PIG, RABBIT, CRAB, TURTLE
		BufferedImage[] TeacherImgUpAndDown = new BufferedImage[2];
		BufferedImage AnswerListImgSW, AnswerListImgSE, AnswerListImgNW, AnswerListImgNE;;
		BufferedImage [] StarsImg = new BufferedImage[3];
		BufferedImage CoolImg, OopsImg;
		BufferedImage CursorImg;
		
		//buttons for the interaction
		JButton NextButton;
		private JButton [] AnswersButton = new JButton[4];
		JButton SaveGameButton = new JButton();
		JButton CorrectRuleButton = new JButton();
		JButton WrongRuleButton = new JButton();
		
		boolean intializeOrNot; 
		
		/**
		 * constructor for initialize all elements and hide all of them
		 */
		public MainPanel() {
			
			//store all images 
			addedImages();
			
			//scale all elements
			scaleAllElements();
			
			//background color
			setBackground(null);
			
			//initial buttons
			initialButtons();
			
			//initial and show the Next button 
			createButtonPanel();
			
			//initial the cover
			createCover();
			
			//initial the rule image
			createRule();
			
			//initial the map image
			createMap();
			
			//initial the elements for the class environment
			createClassEnvironment();
			
			//initial the answer list images
			createTheAnswerList();
			
			//create Stars
			createStars();
			
			//create SaveGameButton
			createSaveGameButton();
			
			//Initial all values
			initialValues();
			
		}
		
		/**
		 * paint component for background painting and the question label showing
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			//add the background in the center
			Graphics2D g2d = (Graphics2D) g;
			int x = (windowW - BackgroundImg.getWidth(null)) / 2;
			int y = (windowH - BackgroundImg.getHeight(null)) / 2;
			g2d.drawImage(BackgroundImg, x, y, null);
			
			//question label showing
			questionShowing(g);
		}
		
		
		/**
		 * showing the question label with moving action 
		 * @param g the graphics painting in the paintComponent
		 */
		public void questionShowing(Graphics g) {
			if (questionToShow == false) return;
			BufferedImage questionImageShow = null;
			
			//switch the question image
			switch(QuestionAnimal) {
			case PIG:
				questionImageShow = StudentsQuestionImgD[questionStudent][0]; //PIG, RABBIT, CRAB, TURTLE
				break;
			case RABBIT:
				questionImageShow = StudentsQuestionImgD[questionStudent][1];
				break;
			case CRAB:
				questionImageShow = StudentsQuestionImgD[questionStudent][2];
				break;
			case TURTLE:
				questionImageShow = StudentsQuestionImgD[questionStudent][3];
				break;
			}
			
			//moving range
			if(move >= 10) {goOrBack = false;}
			else if(move <= 0) {goOrBack = true;}
			
			if(goOrBack==false) {
				move -= 0.1; 
			}else move += 0.1;
			
			//the question location
			int x = (int)oldX[questionStudent];
			int y = (int)oldY[questionStudent];
			if(questionStudent == 1) y += move;
			else x += move;
			
			//drawing the image
			g.drawImage(questionImageShow, x, y, null );
		}
		
		/**
		 * intialize the values
		 */
		protected void initialValues(){
			intializeOrNot = true;
		}
		
		/**
		 * initialize the buttons, including setting null border and null background, and setting the cursor
		 */
		private void initialButtons() {
			NextButton = new JButton();
			
			//set the none border
			NextButton.setBorder(null);
		
			//initial the answers button
			for(int i = 0; i< 4; i++) {
				AnswersButton[i] = new JButton();
				AnswersButton[i].setBorder(null);
			}

			
		}
		
		/**
		 * Initialize the 'next' button panel
		 * 
		 */
		private void createButtonPanel() {
			//first layout set for the frame
			this.setLayout(new GridBagLayout());
			
			//button adding
			NextButton.setIcon(new ImageIcon(NextButtonImg));
			NextButton.setBackground(null);
			NextButton.setOpaque(false);
			NextButton.setContentAreaFilled(false);
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Cursor cur = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
			NextButton.setCursor(cur);
			CorrectRuleButton.setCursor(cur);
			WrongRuleButton.setCursor(cur);

			GridBagConstraints gbc4Bottons = createGridConstraints(0,0,0,0,GridBagConstraints.SOUTHEAST);			
			this.add(NextButton,gbc4Bottons);
			
		}
		
		/**
		 * create rule page
		 */
		private void createRulePage(){

			CorrectRuleButton.setIcon(new ImageIcon(AnswerListImgNW));
			CorrectRuleButton.setVisible(false);
			CorrectRuleButton.setBackground(null);
			CorrectRuleButton.setBorder(null);
			CorrectRuleButton.setOpaque(false);
			
			
			ActionListener CorrectAnswerButton = new ActionListener(){
				public void actionPerformed(ActionEvent actionEvent) {
					ruleWord.setText("Cool! You answer right");
				}
			};
			
			CorrectRuleButton.addActionListener(CorrectAnswerButton);

			WrongRuleButton.setIcon(new ImageIcon(AnswerListImgNE));
			WrongRuleButton.setVisible(false);
			WrongRuleButton.setBackground(null);
			WrongRuleButton.setBorder(null);;
			WrongRuleButton.setOpaque(false);
			
			ActionListener WrongAnswerButton = new ActionListener(){
				public void actionPerformed(ActionEvent actionEvent) {
					intializeOrNot = false;
					WrongRuleButton.setVisible(false);
					ruleWord.setText("Opps! Try other one");
				}
			};
			
			
			WrongRuleButton.addActionListener(WrongAnswerButton);
			
			GridBagConstraints gbc4Correct = createGridConstraints((int)(300*scaleRateH),(int)(400*scaleRateW),0,0,GridBagConstraints.CENTER);
			this.add(CorrectRuleButton,gbc4Correct);
			
			GridBagConstraints gbc4Wrong = createGridConstraints((int)(300*scaleRateH),(int)(750*scaleRateW),0,0,GridBagConstraints.CENTER);
			this.add(WrongRuleButton,gbc4Wrong);
			
			ruleWord.setText("");
			ruleWord.setBackground(null);
			ruleWord.setBorder(null);
			ruleWord.setOpaque(false);
			ruleWord.setVisible(false);

			GridBagConstraints gbc4RuleLabel = createGridConstraints((int)(450*scaleRateH),(int)(400*scaleRateW),0,0,GridBagConstraints.CENTER);
			this.add(ruleWord,gbc4RuleLabel);
		}
		
		/**
		 * Initialize the rule elements
		 */
		private void createRule() {
			//elements for the rule
			createRulePage();
			
			RuleLabel.setIcon(new ImageIcon(RuleImg));
			RuleLabel.setOpaque(false);
			GridBagConstraints gbc4Rule = createGridConstraints(0,0,0,0,GridBagConstraints.NORTH);
			this.add(RuleLabel,gbc4Rule);
			RuleLabel.setVisible(false);
			
		}
		
		/**
		 * Initialize the cover elements
		 */
		private void createCover() {
			CoverLabel.setIcon(new ImageIcon(EduCoverImg));
			CoverLabel.setOpaque(false);
			GridBagConstraints gbc4Cover = createGridConstraints(0,0,(int)(40*scaleRateH),0,GridBagConstraints.CENTER);
			this.add(CoverLabel,gbc4Cover);
			CoverLabel.setVisible(false);
		}
		
		/**
		 * Initialize the map element 
		 */
		private void createMap() {
			MapLabel.setIcon(new ImageIcon(MapImg));
			MapLabel.setOpaque(false);
			GridBagConstraints gbc4Map = createGridConstraints(0,0,(int)(40*scaleRateH),0,GridBagConstraints.CENTER);
			this.add(MapLabel,gbc4Map);
			MapLabel.setVisible(false);
		}
		
		
		/**
		 * Initialize all class environment elements
		 */
		private void createClassEnvironment() {	
			
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
			
			//Teacher image
			TeacherLabel.setOpaque(false);
			GridBagConstraints gbc4Teacher = createGridConstraints(windowH/4, (int)(100*scaleRateH), 0, 0, GridBagConstraints.WEST); 
			this.add(TeacherLabel,gbc4Teacher);
			TeacherLabel.setVisible(false);

			//mini map image
			ImageIcon MiniMapIcon= new ImageIcon(MiniMapImg);
			MiniMapLabel.setIcon(MiniMapIcon);
			MiniMapLabel.setOpaque(false);
			GridBagConstraints gbc4MiniMap = createGridConstraints(0, 0, 0, 0 , GridBagConstraints.NORTHEAST); 
			this.add(MiniMapLabel,gbc4MiniMap);
			MiniMapLabel.setVisible(false);
			
			//3 students image
			for(int i = 0; i < 3; i++) {
				StudentLabel[i] = new JLabel();
				StudentLabel[i].setIcon(new ImageIcon(StudentsImgUpAndDown[i][0]));
				StudentLabel[i].setOpaque(false);
				StudentLabel[i].setVisible(false);		
			}
			
			GridBagConstraints gbc4Student1 = createGridConstraints((int)(50*scaleRateH), (int)(50*scaleRateW), 0, 0, GridBagConstraints.NORTHWEST); 
			this.add(StudentLabel[0],gbc4Student1);
			GridBagConstraints gbc4Student2 = createGridConstraints((int)(50*scaleRateH), (int)(50*scaleRateW), 0, 0, GridBagConstraints.NORTH); 
			this.add(StudentLabel[1],gbc4Student2);
			GridBagConstraints gbc4Student3 = createGridConstraints(0, 0, 0, (int)(50*scaleRateW), GridBagConstraints.EAST); 
			this.add(StudentLabel[2],gbc4Student3);
			
			//set the font for center texts 
			try {
			    //create the font to use. Specify the size!
			    Font customFontForTime = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(50 * scaleRateH));
			    Font customFontForCenterText = Font.createFont(Font.TRUETYPE_FONT,(getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(100 * scaleRateH));
			    Font customFontForStars = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(60 * scaleRateH));
			    Font customFontForSave = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(40 * scaleRateH));
			    Font customFontForRule = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(50 * scaleRateH));
			 
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()));
			    
			    TimeLabel.setFont(customFontForTime);
			    CenterText.setFont(customFontForCenterText);
			    CenterText4Stars.setFont(customFontForStars);
			    SaveGameButton.setFont(customFontForSave);
			    ruleWord.setFont(customFontForRule);
			    
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
			
			//time
			GridBagConstraints gbc4Time = createGridConstraints(0, 0, 0, 0, GridBagConstraints.SOUTH); 
			this.add(TimeLabel,gbc4Time);
			TimeLabel.setVisible(false);
			TimeLabel.setOpaque(false);
		}
		
		/**
		 * Set the rule label be visible 
		 */
		protected void showTheRule() {
			RuleLabel.setVisible(true);
			CorrectRuleButton.setVisible(true);
			if(intializeOrNot == true) {
				WrongRuleButton.setVisible(true);
			}
			
			ruleWord.setVisible(true);
		}
		
		/**
		 * hide the rule label
		 */
		protected void hideTheRule() {
			
			ruleWord.setVisible(false);
			RuleLabel.setVisible(false);
			CorrectRuleButton.setVisible(false);
			WrongRuleButton.setVisible(false);
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
		 * Show the map label
		 */
		protected void showMap() {
			MapLabel.setVisible(true);
		}
		
		/**
		 * hide the map label
		 */
		protected void hideMap() {
			MapLabel.setVisible(false);
		}
				
		/**
		 * show the class room environment elements
		 */
		protected void showTheClassEnvironment() {
			CenterText.setVisible(true);
			TeacherLabel.setVisible(true);
			MiniMapLabel.setVisible(true);
			
			for(int i = 0; i < 3; i++) {
				StudentLabel[i].setVisible(true);
			}
			
			TimeLabel.setVisible(true);
		}
		
		/**
		 * Set the dark cover text
		 * @param time The remaining timer which will show on the dark cover
		 */
		protected void setDarkCoverTime(double time) {
			CenterText.setIcon(null);
			if((int)time == 0) 	CenterText.setText("START!");
			else CenterText.setText(Integer.toString((int)time));
			NextButton.setVisible(false);
			DarkCover.setVisible(true);
		}
		
		/**
		 * The method is for Hiding dark cover
		 */
		protected void hideDarkCoverTime() {
			CenterText.setVisible(false);
			DarkCover.setVisible(false);
			NextButton.setVisible(true);
		}
		
		/**
		 * Show the game end label
		 */
		protected void showGameEndCover() {
			CenterText.setIcon(null);
			CenterText.setText("CLASS OVER");
			CenterText.setVisible(true);
			DarkCover.setVisible(true);
		}
		
		/**
		 * Changing teacher label image and show the center clicked label
		 * @param isCorrect check if player click the right answer 
		 * @param rate the rate for center label image size
		 */
		protected void teacherUp(boolean isCorrect, double rate) {
			TeacherLabel.setIcon(new ImageIcon(TeacherImgUpAndDown[1]));
			CenterText.setText(null);
			if(isCorrect) CenterText.setIcon(new ImageIcon(scaleImage(CoolImg,rate,rate)));
			else CenterText.setIcon(new ImageIcon(scaleImage(OopsImg,rate,rate)));
			CenterText.setVisible(true);
		}
		
		/**
		 * Teacher's label changing back
		 */
		protected void teacherDown() {
			TeacherLabel.setIcon(new ImageIcon(TeacherImgUpAndDown[0]));
			CenterText.setVisible(false);
		}
		
		/**
		 * Hide all class environment elements
		 */
		protected void hideTheClassEnvironment() {
			TeacherLabel.setVisible(false);
			MiniMapLabel.setVisible(false);
			
			for(int i = 0; i < 3; i++) {
				StudentLabel[i].setVisible(false);
			}
			
			questionToShow = false;
			
			TimeLabel.setVisible(false);
		}
		
		/**
		 * Update the remaining class timer and the score text label
		 * @param time the class remaining time
		 * @param score the score
		 */
		protected void updateTimeAndScore(int time, int score) {
			TimeLabel.setText("CLASS TIME: " + Integer.toString(time) + "   SCORE: " + Integer.toString(score));
		}
		
		/**
		 * Show the question label
		 * @param QuestionNum question student number
		 * @param Question the animal for the question
		 */
		protected void showTheQuestion(int QuestionNum, Animal Question) {
			
			questionStudent = QuestionNum;
			QuestionAnimal = Question;
			questionToShow = true;
			
			if(oldX[0] == 0) {
				//stores the question label initial locations
				oldX[0]  = StudentLabel[0].getLocation().x + (150*scaleRateW);
				oldY[0]  = StudentLabel[0].getLocation().y;
			
				oldX[1] = StudentLabel[1].getLocation().x;
				oldY[1] = StudentLabel[1].getLocation().y + (150*scaleRateH);
				
				oldX[2] = StudentLabel[2].getLocation().x - (150*scaleRateW);
				oldY[2] = StudentLabel[2].getLocation().y;
			}
		}
		
		
		/**
		 * Change the student label which is asking the question
		 * @param QuestionNum Questining student number
		 */
		protected void studentRaisingHand(int QuestionNum) {
			for(int i = 0; i < 3; i++) {
				if(i != QuestionNum) {
					ImageIcon StudentIcon = new ImageIcon(StudentsImgUpAndDown[i][0]);
					StudentLabel[i].setIcon(StudentIcon);
				}else {
					ImageIcon StudentIcon = new ImageIcon(StudentsImgUpAndDown[i][1]);
					StudentLabel[i].setIcon(StudentIcon);	
				}
			}
		}
		
		/**
		 * Initialize the answer list label
		 */
		private void createTheAnswerList() {	
			for(int i=0; i < 4; i++) {
				AnswersButton[i].setOpaque(false);
				
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Cursor cur = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
				AnswersButton[i].setCursor(cur);
				
				GridBagConstraints gbc4AnswersBottons = createGridConstraints(0,(int)((i*300 - 100)*scaleRateW),(int)(100*scaleRateH),0,GridBagConstraints.SOUTH);			
				this.add(AnswersButton[i],gbc4AnswersBottons);
				AnswersButton[i].setVisible(false);
			}
		}
		
		/**
		 * change the answer list image
		 * @param AL the answer list from the model
		 */
		protected void ChangeTheAnswerListImg(ArrayList<Answer> AL) {
			for(int i=0; i < 4; i++) {
				
				ImageIcon AnswerIcon = new ImageIcon();
				switch(AL.get(i).getMapPlace()) {
				case SOUTHWEST:
					AnswerIcon.setImage(AnswerListImgSW);
					break;
				case SOUTHEAST:
					AnswerIcon.setImage(AnswerListImgSE);
					break;
				case NORTHEAST:
					AnswerIcon.setImage(AnswerListImgNE);
					break;
				case NORTHWEST:
					AnswerIcon.setImage(AnswerListImgNW);
					break;
				default:
					break;
				}
				AnswersButton[i].setIcon(AnswerIcon);
			}
		}
		
		/**
		 * Show the answer list
		 * @param AL answer list from the model
		 */
		protected void showTheAnswerList(ArrayList<Answer> AL) {
			for(int i=0; i < 4; i++){
				boolean clickOrNot = AL.get(i).getClcikedOrNot();
				if(clickOrNot == false) {
					AnswersButton[i].setVisible(true);
				}else {
					AnswersButton[i].setVisible(false);
				}
			}
		}
		
		
		/**
		 * Hide all answer list
		 */
		protected void hideAllAnswerList() {
			for(int i=0; i < 4; i++){
				AnswersButton[i].setVisible(false);
			}
		}
		
		/**
		 * Initialize the stars lebel
		 */
		private void createStars() {
		
			//Center Text for Stars
			GridBagConstraints gbc4CenterText = createGridConstraints((int)(100*scaleRateH),0,0,0,GridBagConstraints.CENTER);
			this.add(CenterText4Stars,gbc4CenterText);
			CenterText4Stars.setOpaque(false);
			CenterText4Stars.setVisible(false);
			
			//initial the stars label
			for(int i =0; i <3; i++) {
				Stars[i] = new JLabel();
				Stars[i].setOpaque(false);			
				GridBagConstraints gbc4Stars = createGridConstraints(0,0,(int)(200*scaleRateH),0,GridBagConstraints.CENTER);			
				this.add(Stars[i],gbc4Stars);
				Stars[i].setVisible(false);
			}
		}
		
		/**
		 * Show the stars
		 * @param number The number of stars
		 */
		protected void showStars(int number) {
			
			BufferedImage starShowing = null;
			
			switch(number) {
			case 3:
				starShowing = StarsImg[0];
				CenterText4Stars.setText("GOOD TEACHER!");
				break;
			case 4:
				CenterText4Stars.setText("WONDERFUL TEACHER!");
				starShowing = StarsImg[1];
				break;
			default:
				CenterText4Stars.setText("THANK YOU, EXCELLENT TEACHER!");
				starShowing = StarsImg[2];
				break;
			}
			
			starShowing = scaleImage(starShowing,StarScale,StarScale);
			
			if(StarScale > 1.00) StarScale -= 0.05;
			
			int showingNum = number - 3;
			Stars[showingNum].setIcon(new ImageIcon(starShowing));
			Stars[showingNum].setVisible(true);
				
			CenterText4Stars.setVisible(true);
		}
		
		/**
		 * Hide all stars label
		 */
		protected void hideStars() {
			StarScale = 2.0;
			CenterText4Stars.setVisible(false);
			for(int i=0; i < 3; i++) {Stars[i].setVisible(false);}
		}
		
		/**
		 * create the saved game button
		 */
		private void createSaveGameButton() {
			SaveGameButton.setBackground(null);
			SaveGameButton.setOpaque(false);
			SaveGameButton.setBorder(null);
			SaveGameButton.setText("SAVE");
			SaveGameButton.setForeground(Color.WHITE);
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Cursor c = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
			SaveGameButton.setCursor(c);
			
			GridBagConstraints gbc4Voice = createGridConstraints(0,0,0,0,GridBagConstraints.SOUTHWEST);
			this.add(SaveGameButton, gbc4Voice);
		}
		
		/**
		 * add listeners for buttons
		 * @param ac4Next action for next button
		 * @param AL action for answers 
		 * @param saveListener action for save button
		 */
		protected void setListeners(ActionListener ac4Next, ArrayList<ActionListener> AL, ActionListener saveListener) {

			NextButton.addActionListener(ac4Next);
			
			for(int i = 0; i < 4; i++) {
				AnswersButton[i].addActionListener(AL.get(i));
			}
			
			SaveGameButton.addActionListener(saveListener);
		}
		
		
		/**
		 * Load all images
		 */
		private void addedImages() {	 
			try {
				BackgroundImg = ImageIO.read((getClass().getResource("/Source/Images/Education/Background.png")).openStream());
				EduCoverImg = ImageIO.read((getClass().getResource("/Source/Images/Education/EduCover.png")).openStream());
				RuleImg = ImageIO.read((getClass().getResource("/Source/Images/Education/Rule.png")).openStream());
				NextButtonImg = ImageIO.read((getClass().getResource("/Source/Images/Education/Next.png")).openStream());
				MapImg = ImageIO.read((getClass().getResource("/Source/Images/Education/Map.png")).openStream());
				MiniMapImg = ImageIO.read((getClass().getResource("/Source/Images/Education/miniMap.png")).openStream());
				TeacherImgUpAndDown[0] =  ImageIO.read((getClass().getResource("/Source/Images/Education/Teacher/TeacherDown.png")).openStream());
				TeacherImgUpAndDown[1] =  ImageIO.read((getClass().getResource("/Source/Images/Education/Teacher/TeacherUp.png")).openStream());

				for(int i=0; i<3; i++) {
					StudentsImgUpAndDown[i][0] = ImageIO.read((getClass().getResource("/Source/Images/Education/Students/Student" + Integer.toString(i+1) + "Down.png")).openStream());
					StudentsImgUpAndDown[i][1] = ImageIO.read((getClass().getResource("/Source/Images/Education/Students/Student" + Integer.toString(i+1) + "Up.png")).openStream());				
					
					StudentsQuestionImgD[i][0] = ImageIO.read((getClass().getResource("/Source/Images/Education/Questions/Question" + Integer.toString(i+1) + "PIG.png")).openStream());
					StudentsQuestionImgD[i][1] = ImageIO.read((getClass().getResource("/Source/Images/Education/Questions/Question" + Integer.toString(i+1) + "RABBIT.png")).openStream());
					StudentsQuestionImgD[i][2] = ImageIO.read((getClass().getResource("/Source/Images/Education/Questions/Question" + Integer.toString(i+1) + "CRAB.png")).openStream());
					StudentsQuestionImgD[i][3] = ImageIO.read((getClass().getResource("/Source/Images/Education/Questions/Question" + Integer.toString(i+1) + "TURTLE.png")).openStream());	
					
					StarsImg[i] = ImageIO.read((getClass().getResource("/Source/Images/Education/Stars/" + Integer.toString(i+3) + "stars.png")).openStream());
				}
				
				AnswerListImgSW = ImageIO.read((getClass().getResource("/Source/Images/Education/Answers/AnswerSW.png")).openStream());
				AnswerListImgSE = ImageIO.read((getClass().getResource("/Source/Images/Education/Answers/AnswerSE.png")).openStream());
				AnswerListImgNW = ImageIO.read((getClass().getResource("/Source/Images/Education/Answers/AnswerNW.png")).openStream());
				AnswerListImgNE = ImageIO.read((getClass().getResource("/Source/Images/Education/Answers/AnswerNE.png")).openStream());
				
				CoolImg = ImageIO.read((getClass().getResource("/Source/Images/Education/Answers/cool.png")).openStream());
				OopsImg = ImageIO.read((getClass().getResource("/Source/Images/Education/Answers/oops.png")).openStream());
				CursorImg = ImageIO.read((getClass().getResource("/Source/Images/Cursor.png")).openStream());
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Scale all images of element according to the rate between background and the window size
		 */
		private void scaleAllElements() {
			
			scaleRateW = windowW / (double) BackgroundImg.getWidth();
			scaleRateH = windowH / (double) BackgroundImg.getHeight();
			
			//resize all images according to scale rates
			BackgroundImg = scaleImage(BackgroundImg,scaleRateW,scaleRateH); 
			EduCoverImg = scaleImage(EduCoverImg, scaleRateW, scaleRateH);
			RuleImg = scaleImage(RuleImg,scaleRateW,scaleRateH);
			NextButtonImg = scaleImage(NextButtonImg, scaleRateW, scaleRateH);
			MapImg = scaleImage(MapImg, scaleRateW, scaleRateH);
			MiniMapImg = scaleImage(MiniMapImg, scaleRateW, scaleRateH);
			
			for(int i=0; i < 3; i++) {
				StudentsImgUpAndDown[i][0] = scaleImage(StudentsImgUpAndDown[i][0], scaleRateW,scaleRateH);
				StudentsImgUpAndDown[i][1] = scaleImage(StudentsImgUpAndDown[i][1], scaleRateW,scaleRateH);
				
				for(int j = 0; j < 4; j++) {
					StudentsQuestionImgD[i][j] = scaleImage(StudentsQuestionImgD[i][j], scaleRateW,scaleRateH);
				}
				
				StarsImg[i] = scaleImage(StarsImg[i], scaleRateW*1.5, scaleRateH*1.5);
			}	
			
			for(int i = 0; i <2; i++) {
				TeacherImgUpAndDown[i] = scaleImage(TeacherImgUpAndDown[i], scaleRateW,scaleRateH);
			}
			
			AnswerListImgSW = scaleImage(AnswerListImgSW, scaleRateW, scaleRateH);
			AnswerListImgSE = scaleImage(AnswerListImgSE, scaleRateW, scaleRateH);
			AnswerListImgNW = scaleImage(AnswerListImgNW, scaleRateW, scaleRateH);
			AnswerListImgNE = scaleImage(AnswerListImgNE, scaleRateW, scaleRateH);
			
			CoolImg = scaleImage(CoolImg, scaleRateW,scaleRateH);
			OopsImg = scaleImage(OopsImg, scaleRateW,scaleRateH);
			CursorImg = scaleImage(CursorImg, scaleRateW, scaleRateH);
		}
		
		
	}
	
}	

