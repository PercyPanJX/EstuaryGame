package COVERMVC;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SUPERMVC.View;

/**
 * the cover view
 * @author percypan
 *
 */
public class CoverView extends View{
	
	JFrame CoverFrame;
	
	Clip backgrounMusic;
	boolean voice = true;
	
	mainPanel mainPane;
	
	/**
	 * cover view constructor will initialize the cover panel
	 */
	CoverView(){
		
		//initialize the frame
		initialTheFrame();
		
		//set the background for the frame
		setMainPane();
		
		//show the designed frame
		showCover();
		
		//background music playing
		createBackgroundMusic();
	};
	
	/**
	 * Initialize the frame with maximum window
	 */
	private void initialTheFrame() {
		//initial the frame
		CoverFrame = new JFrame("Estuary Game");
		CoverFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		CoverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
	
	/**
	 * Set the mainPane for the panel
	 */
	private void setMainPane() {
		//set the panel for the background
		mainPane = new mainPanel();
	
		//set the mainPane for the frame
		CoverFrame.setContentPane(mainPane);		
	}
	
	/**
	 * Add the button listeners
	 * @param acb4Game1 the button listener for the first game
	 * @param acb4Game2 the button listener for the second game
	 * @param acb4Game3 the button listener for the third game
	 * @param VoiceControl the button listener for the voice controller
	 * @param loadButton the button listener for the load game button
	 */
	public void setButtonListener(ActionListener acb4Game1, 
			ActionListener acb4Game2, ActionListener acb4Game3, ActionListener VoiceControl, ActionListener loadButton) {
		mainPane.setButtonListener(acb4Game1, acb4Game2, acb4Game3, VoiceControl, loadButton);
	}
	
	/**
	 * when click the voice, open the voice or close the voice
	 */
	protected void clickVoice() {
		if(voice == true) {
			voice = false;
			mainPane.voiceClick(false);
			backgrounMusic.stop();
		}
		else {
			voice = true;
			backgrounMusic.start();
			mainPane.voiceClick(true);
		}
	}
	
	/**
	 * Show the frame
	 */
	protected void showCover() {
		//set visible for the frame
		CoverFrame.setVisible(true);
	}
	
	/**
	 * Repaint the panel, currently doing nothing
	 */
	protected void update() {
		mainPane.repaint();
	}
	
	/**
	 * hide all elements for the cover
	 */
	protected void hideAll() {
		CoverFrame.setVisible(false);
	}
	
	/**
	* create the background music with infinit loop
	*/
	private void createBackgroundMusic() {
		try {
			URL soundFile = getClass().getResource("/Source/music/background.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			backgrounMusic = AudioSystem.getClip();
			
			backgrounMusic.open(audioIn);
			backgrounMusic.start();
			backgrounMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(UnsupportedAudioFileException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
///////////////////////Panel classes////////////////
	//The panel for the main panel
	@SuppressWarnings("serial")
	private class mainPanel extends JPanel{
		ButtonPanel buttonPane;
		double scaleRateW;
		double scaleRateH;
		JLabel TitleLabel = new JLabel();
		JLabel PickWordsLabel = new JLabel();
		JButton GameButton1, GameButton2, GameButton3;
		JButton VoiceButton = new JButton();
		JButton LoadButton = new JButton();
		BufferedImage BackgroundImg,Game1Img,Game2Img,Game3Img;
		BufferedImage CursorImg, VoiceONImg, VoiceOFFImg;
		int gameOn = 0;
		double rate = 1;

		boolean makeDark = true;
		double alpha = 255;
		
		/**
		 * constructor for the main panel
		 */
		public mainPanel() {

			setBackground(Color.BLACK);
			
			//added all images
			addedImages();
	
			//scale all elements according to the window size
			scaleAllElements();
			
			//set the buttons on the frame
			setButtons();
			
			//create the needed words
			createWords();
			
			//create the voice label
			createVoiceLabel();
			
			//set the load button
			setLoadButton();
		}
		
		/**
		 * Paint the background and the words
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			//Image scaledBG = BackgroundImg.getScaledInstance((int)windowW, (int)windowH, Image.SCALE_DEFAULT); 
			g.drawImage(BackgroundImg, 0, 0, null);
	
			//set the pick words text alpha 
			if(alpha <= 20) makeDark = false;
			else if(alpha >= 255) makeDark = true;
			if(makeDark == true) alpha -= 0.9;
			else alpha += 0.9;
			PickWordsLabel.setForeground(new Color(255,255,255,(int)alpha));
			
			if(gameOn != 0 && rate <1.1) rate += 0.001;
			
			switch(gameOn) {
			case 1:
				GameButton1.setIcon(new ImageIcon(scaleImage(Game1Img,rate,rate)));
				break;
			case 2:
				GameButton2.setIcon(new ImageIcon(scaleImage(Game2Img,rate,rate)));
				break;
			case 3:
				GameButton3.setIcon(new ImageIcon(scaleImage(Game3Img,rate,rate)));
				break;
			default:
					
			}
		}
		
		/**
		 * Create and add button panels for the 3 games
		 */
		private void setButtons() {
			
			buttonPane = new ButtonPanel();
			
			//set the grid bag layout for the frame
			this.setLayout(new GridBagLayout());
					
			//set the location for the panel of buttons
			GridBagConstraints gbc4Bottons = createGridConstraints(0,0,0,0,GridBagConstraints.CENTER);
				
			//add the panel of buttons on the frame
			this.add(buttonPane, gbc4Bottons);
		}
		
		/**
		 * Add the button listeners
		 * @param acb4Game1 the button listener for the first game
		 * @param acb4Game2 the button listener for the second game
		 * @param acb4Game3 the button listener for the third game
		 * @param VoiceControl the button listener for the voice controller
		 * @param loadButton the button listener for the load button
		 */
		private void setButtonListener(ActionListener acb4Game1, 
				ActionListener acb4Game2, ActionListener acb4Game3, ActionListener voiceControl, ActionListener loadButton) {
			GameButton1.addActionListener(acb4Game1);
			GameButton2.addActionListener(acb4Game2);
			GameButton3.addActionListener(acb4Game3);
			VoiceButton.addActionListener(voiceControl);
			LoadButton.addActionListener(loadButton);
		}
		
		/**
		 * Create words, including title and the picking label notion
		 */
		private void createWords() {
			TitleLabel.setForeground(Color.white);
			PickWordsLabel.setForeground(new Color(255,255,255,255));
			
			//set the font for center texts 
			try {
			    //create the font to use. Specify the size!
			    Font customFontForTitle = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(100 * scaleRateH));
			    Font customFontForPickWords = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(40 * scaleRateH));
			    Font customFontForLoad = Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()).deriveFont(Font.BOLD, (float)(40 * scaleRateH));
			   
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    //register the font
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, (getClass().getResource("/Source/font/newFont.ttf")).openStream()));
			    
			    TitleLabel.setFont(customFontForTitle);
			    PickWordsLabel.setFont(customFontForPickWords);
			    LoadButton.setFont(customFontForLoad);
			} catch (IOException e) {
			    e.printStackTrace();
			} catch(FontFormatException e) {
			    e.printStackTrace();
			}
			
			TitleLabel.setText("ESTUARY SCHOOL");
			PickWordsLabel.setText("-PICK ONE CAREER YOU WANT TO TRY-");
			TitleLabel.setVisible(true);
			PickWordsLabel.setVisible(true);
			
			//set the location for the panel of buttons
			GridBagConstraints gbc4Title = createGridConstraints(0,0,0,0,GridBagConstraints.NORTH);
			
			//set the location for the panel of buttons
			GridBagConstraints gbc4PickWords = createGridConstraints(0,0,(int)(60*scaleRateH),0,GridBagConstraints.SOUTH);

			//add labels on the frame
			this.add(TitleLabel, gbc4Title);
			this.add(PickWordsLabel, gbc4PickWords);
		}
		
		/**
		 * initialize the voice button
		 */
		private void createVoiceLabel() {
			if(voice == true) {
				VoiceButton.setIcon(new ImageIcon(VoiceONImg));
			}else {
				VoiceButton.setIcon(new ImageIcon(VoiceOFFImg));
			}
			
			VoiceButton.setOpaque(false);
			VoiceButton.setBackground(null);
			VoiceButton.setVisible(true);
			VoiceButton.setBorder(null);
			VoiceButton.setContentAreaFilled(false);
			
			GridBagConstraints gbc4Voice = createGridConstraints(0,0,0,0,GridBagConstraints.SOUTHWEST);
			this.add(VoiceButton, gbc4Voice);
		}
		
		/**
		 * open the voice or mute
		 * @param onOrNot the player choicing to open the voice or not
		 */
		private void voiceClick(boolean onOrNot){
			if(onOrNot) VoiceButton.setIcon(new ImageIcon(VoiceONImg));
			else VoiceButton.setIcon(new ImageIcon(VoiceOFFImg));
		}
		
		/**
		 * set the load button which could load the game
		 */
		private void setLoadButton() {

			//set the load button 
			LoadButton.setBackground(null);
			LoadButton.setOpaque(false);
			LoadButton.setBorder(null);
			LoadButton.setText("LOAD");
			LoadButton.setForeground(Color.WHITE);
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Cursor c = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
			LoadButton.setCursor(c);
			

			GridBagConstraints gbc4Voice = createGridConstraints(0,0,0,0,GridBagConstraints.SOUTHEAST);
			this.add(LoadButton, gbc4Voice);
			
		}
		

		/**
		 * Button panel including three game buttons
		 * @author percypan
		 *
		 */
		private class ButtonPanel extends JPanel{
			
			public ButtonPanel() {
				
				initialButtons();
				
				setBorder(null); //set the none border
				this.setBackground(null); // set the transparent background
				
				//set the grid bag layout for the panel
				setLayout(new GridBagLayout());
		        GridBagConstraints gbc = new GridBagConstraints();
		        
		        //horizontal showing the buttons
		        gbc.gridheight = GridBagConstraints.REMAINDER;
		        
		        //set the spaces between buttons 
		        gbc.insets = new Insets((int)(20*scaleRateH), (int)(80*scaleRateW), 0, (int)(70*scaleRateW));
		        
		        //set the transparent
		        setOpaque(false);
		        
		        //add buttons
		        add(GameButton1, gbc);
		        add(GameButton2, gbc);
		        add(GameButton3, gbc);
			}
			
			/**
			 * Initialize the buttons 
			 * Set the button null border
			 * Set the button images
			 * Set the cursor for the buttons
			 */
			private void initialButtons(){
				
				//set three game buttons
				GameButton1 = new JButton();
				GameButton2 = new JButton();
				GameButton3 = new JButton();
				
				//set background and the opaque
				GameButton1.setBackground(null);
				GameButton1.setOpaque(false);
				GameButton2.setBackground(null);
				GameButton2.setOpaque(false);
				GameButton3.setBackground(null);
				GameButton3.setOpaque(false);
				GameButton1.setContentAreaFilled(false);
				GameButton2.setContentAreaFilled(false);
				GameButton3.setContentAreaFilled(false);
				
				
				//set the none border
				GameButton1.setBorder(null);
				GameButton2.setBorder(null);
				GameButton3.setBorder(null);
				
				//set button images
				GameButton1.setIcon(new ImageIcon(Game1Img));
				GameButton2.setIcon(new ImageIcon(Game2Img));
				GameButton3.setIcon(new ImageIcon(Game3Img));
				
				//Set hand cursor for buttons
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Cursor c = toolkit.createCustomCursor(CursorImg, new Point(0,0), "custom cursor");
				GameButton1.setCursor(c);
				GameButton2.setCursor(c);
				GameButton3.setCursor(c);
				
				GameButton1.setSize(Game1Img.getWidth(), Game1Img.getHeight());
				
				GameButton1.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e ) {
				    	gameOn = 1;
				    }
				    public void mouseExited(MouseEvent e ) {
				    	gameOn = 0;
						GameButton1.setIcon(new ImageIcon(Game1Img));
						rate = 1;
				    }
				} );
				
				GameButton2.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e ) {
				    	gameOn = 2;
				    }
				    public void mouseExited(MouseEvent e ) {
				    	gameOn = 0;
						GameButton2.setIcon(new ImageIcon(Game2Img));
						rate = 1;
				    }
				} );
				
				GameButton3.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e ) {
				    	gameOn = 3;
				    }
				    public void mouseExited(MouseEvent e ) {
				    	gameOn = 0;
						GameButton3.setIcon(new ImageIcon(Game3Img));
						rate = 1;
				    }
				} );
			}
		}
		
		/**
		 * load and store all needed iamges
		 */
		private void addedImages() {
			try {
				
				BackgroundImg = ImageIO.read((getClass().getResource("/Source/Images/Cover/cover.png")).openStream());
				Game1Img = ImageIO.read((getClass().getResource("/Source/Images/Cover/Game1.png")).openStream());
				Game2Img = ImageIO.read((getClass().getResource("/Source/Images/Cover/Game2.png")).openStream());
				Game3Img = ImageIO.read((getClass().getResource("/Source/Images/Cover/Game3.png")).openStream());
				CursorImg = ImageIO.read((getClass().getResource("/Source/Images/Cursor.png")).openStream());
				VoiceONImg = ImageIO.read((getClass().getResource("/Source/Images/Cover/voiceON.png")).openStream());
				VoiceOFFImg = ImageIO.read((getClass().getResource("/Source/Images/Cover/voiceOFF.png")).openStream());
			} catch (IOException e) {
		  		e.printStackTrace();
		  	}
			
		}
		

		/**
		 * Scale all elements according to the window size
		 */
		private void scaleAllElements() {

			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Rectangle bounds = env.getMaximumWindowBounds();
			double windowW = (double)bounds.getWidth();
			double windowH = (double)bounds.getHeight();

			scaleRateW = windowW / (double) BackgroundImg.getWidth();
			scaleRateH = windowH / (double) BackgroundImg.getHeight();
			
			BackgroundImg = scaleImage(BackgroundImg,scaleRateW,scaleRateH);
			Game1Img = scaleImage(Game1Img,scaleRateW,scaleRateH);
			Game2Img = scaleImage(Game2Img,scaleRateW,scaleRateH);
			Game3Img = scaleImage(Game3Img,scaleRateW,scaleRateH);
			CursorImg = scaleImage(CursorImg,scaleRateW,scaleRateH);
			VoiceONImg = scaleImage(VoiceONImg,scaleRateW,scaleRateH);
			VoiceOFFImg = scaleImage(VoiceOFFImg,scaleRateW,scaleRateH);
		}
		
	}
	
	

	
}
