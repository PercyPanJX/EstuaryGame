package EDUMVC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import SUPERMVC.Model;

/**
 * educational game model for the game logic
 * @author percypan
 *
 */
public class EduModel extends Model{
	
	//0.01 second per tick
	private double TICK = 0.01;
	
	//Associated timer for View
	private double AnswerTimer;
	private boolean IsCorrect;
	
	//time for the game
	private double ClassTime;
	private double DarkCoverTimer;
	
	//game state and frame size
	EducationalGameState GameState;
	
	//The question of animal, the questioning student and the correct answer
	private int QuestionStudent; //the n'th student showing the question (0,1,2)
	private Animal Question;
	private MapPosition CorrectAnswer; //correct answer for the animal

	//answers list
	private ArrayList<Answer> AnswersList;
	
	//Map position list
	List<MapPosition> directions;
	
	//the score player got
	private int Score;
	private int Stars; //3,4,5
	
	/**
	 * Constructor to initialize the attributes
	 */
	public EduModel(){		
		GameState = EducationalGameState.WAITING;
		Score = 0;
		AnswerTimer = 0;
		IsCorrect = false;
		DarkCoverTimer = 3.5;
		
		
		//added all directions on the array
		initialTheDirectionArray();
		
		//initial the answers list
		initialTheAnswersList();
		
		//Randomize the question and the answer list
		randomizeQuestion();
		randomizeTheAnswersList();	
	}
		
	/**
	 * initialize the direction list which is for randomize the order of the answers
	 */
	private void initialTheDirectionArray() {
		directions  = new ArrayList<MapPosition>();
		directions.add(MapPosition.NORTHEAST);
		directions.add(MapPosition.NORTHWEST);
		directions.add(MapPosition.SOUTHWEST);
		directions.add(MapPosition.SOUTHEAST);
	}
	
	/**
	 * initialize the answer list
	 */
	private void initialTheAnswersList() {
		AnswersList = new ArrayList<Answer>();
		for(int i = 0; i < 4; i++) {
			AnswersList.add(new Answer());
		}
	}

	/**
	 * start the game including initialize the needed attributes, the method is also using to restart the game
	 */
	public void startTheGame() {
		ClassTime = 45.5;
		DarkCoverTimer = 3.5;
		Score = 0;
		GameState = EducationalGameState.COVERTIME;
		
		//reinit the question and the answer list
		randomizeQuestion();
		for(int i =0; i < 4; i++) {
			AnswersList.get(i).resetTheAnswer();
		}
		randomizeTheAnswersList();
	}
	
	/**
	 * go to the next game state
	 */
	public void nextState() {
		switch(GameState) {
			case COVERTIME:
				GameState = EducationalGameState.RULETIME;
				break;
			case RULETIME:
				GameState = EducationalGameState.MAPTIME;
				break;	
			case MAPTIME:
				GameState = EducationalGameState.BEGINNING;
				break;
			case BEGINNING:
				GameState = EducationalGameState.CLASSTIME;
				break;
			case CLASSTIME:
				GameState = EducationalGameState.ENDING;
				break;
			case ENDING:
				GameState = EducationalGameState.SCORETIME;
				break;
			case SCORETIME:
				GameState = EducationalGameState.GAMESTOP;
				break;
			default:
				break;
		}
	}
	
	/**
	 * update the game according to the current state
	 */
	public void updateModel() {
		switch(GameState) {
		case BEGINNING:
			updateDarkCoverTimer();
			break;
		case CLASSTIME:
			updateClass();
			break;
		case ENDING:
			updateDarkCoverTimer();
			computeStars();
			break;
		case SCORETIME:
			break;
		default:
			break;
		}
	}
	
	/**
	 * update the dark cover timer.
	 */
	public void updateDarkCoverTimer() {
		DarkCoverTimer -= TICK;
		if(DarkCoverTimer <=0 ) {
			nextState();
			DarkCoverTimer = 0.5;
		}
	}
	
	/**
	 * update the attributes when in the class time
	 */
	public void updateClass() {
		ClassTime -= TICK;
		AnswerTimer -= TICK;
		
		// if time is over, show the next state
		if(ClassTime <= 0.00) nextState();
	}
	
	/**
	 * when pick an answer, check the answer and update the response according to the logic
	 * @param answerPickedNum the number of the answer picked by player
	 */
	public void pickAnAnswer(int answerPickedNum) {
		AnswerTimer = 1;
		
		AnswersList.get(answerPickedNum).clickedTheAnswer();
		
		//if picked answer is same as the correct answer, reset the answer list and add one score
		if(checkTheAnswer(AnswersList.get(answerPickedNum))) {
			Score ++;
			IsCorrect = true;
			
			//random pick the next answer
			randomizeQuestion();
			
			//reset all answers to unclicked
			for(int i =0; i < 4; i++) {
				AnswersList.get(i).resetTheAnswer();
			}
			
			//Randomize the answer list
			randomizeTheAnswersList();
		}else {
			IsCorrect = false;
		}
	}
	
	/**
	 * check the answer 
	 * @param ans the picked answer 
	 * @return the correct answer
	 */
	private boolean checkTheAnswer(Answer ans) {
		return ans.getMapPlace() == CorrectAnswer;
	}
	
	/**
	 * compute the stars after the class over
	 */
	private void computeStars() {
		if(Score > 10) Stars = 5; 
		else if(Score > 5) Stars = 4;
		else Stars = 3;
	}

	/**
	 * get the needed elements for the view
	 * @return the elements which view needed
	 */
	public EduElements getElements() {
		return new EduElements(GameState, ClassTime,
				QuestionStudent, AnswersList,
				Score, Question, AnswerTimer, DarkCoverTimer, Stars, IsCorrect);	
	}
	
	/**
	 * randomize the question, and set the correct answer 
	 */
	public void randomizeQuestion() {
		
		//random questioning student
		int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
		while(randomNum == QuestionStudent) {
			randomNum = ThreadLocalRandom.current().nextInt(0, 3);
		}
		QuestionStudent = randomNum;
		
		//random question
		Animal radomQuestion = Animal.randomAnimal();
		while(radomQuestion == Question) {
			radomQuestion = Animal.randomAnimal();
		}
		Question = radomQuestion;
		
		//set the correct answer
		switch(Question) {
		case CRAB:
			CorrectAnswer = MapPosition.SOUTHEAST;
			break;
		case TURTLE:
			CorrectAnswer = MapPosition.SOUTHWEST;
			break;
		case RABBIT:
			CorrectAnswer = MapPosition.NORTHWEST;
			break;
		case PIG:
			CorrectAnswer = MapPosition.NORTHEAST;
			break;
		}
	}
	
	/**
	 * randomize the order of answer list 
	 */
	public void randomizeTheAnswersList() {
		Collections.shuffle(directions);
		int i=0;
		for (MapPosition p : directions){
			AnswersList.get(i).setTheAnswer(p);
			i++;
		}
	}
	
	/**
	 * the method for loading the game
	 * @param eduData the saved educational game data
	 */
	public void loadGame(EduElements eduData) {

		//game state
		this.GameState = eduData.GameState;
		
		//time for the game
		this.ClassTime = eduData.ClassTime;
		
		//students and teacher states
		this.QuestionStudent =  eduData.QuestionStudent;
		
		//answers list for player picking
		this.AnswersList =  eduData.AnswersList;
		
		//game score
		this.Score =  eduData.Score;
		
		//question
		this.Question =  eduData.Question;
		
		//answer timer and dark cover timer
		this.AnswerTimer =  eduData.AnswerTimer;
		this.DarkCoverTimer =  eduData.DarkCoverTimer;
		
		//Stars Num
		this.Stars = eduData.StarsNum;
		this.IsCorrect =  eduData.isCorrect;
	}
	
	
	
}


