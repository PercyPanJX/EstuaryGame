package EDUMVC;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * including elements which the view need from the model
 * all attributes are public to making it easy to be used
 * @author percypan
 *
 */
public class EduElements implements Serializable{
//elements the view needed from the model		

	//game state
	public EducationalGameState GameState;
	
	//time for the game
	public double ClassTime;
	
	//students and teacher states
	public int QuestionStudent;
	
	//answers list for player picking
	public ArrayList<Answer> AnswersList;
	
	//game score
	public int Score;
	
	//question
	public Animal Question;
	
	//answer timer and dark cover timer
	public double AnswerTimer;
	public double DarkCoverTimer;
	
	//Stars Num
	public int StarsNum;
	public boolean isCorrect;
	
	/**
	 * Constructor to store the needed attributes 
	 * @param GS the current game state
	 * @param CT the class remaining time 
	 * @param QS the questioning student number 
	 * @param AL the answer list for player picking
	 * @param SC the total score
	 * @param AN the animal of the question 
	 * @param AT the answer timer for when player pick an answer
	 * @param DT the dark cover timer for the converting before the class beginning
	 * @param SN the total number of the stars after the class finishing
	 * @param IC the attribute to show if player pick the correct answer
	 */
	EduElements(EducationalGameState GS,
			double CT, int QS,
			ArrayList<Answer> AL,
			int SC, Animal AN, double AT, double DT, int SN,
			boolean IC){
		GameState = GS;
		ClassTime = CT;
		QuestionStudent = QS;
		AnswersList = AL;
		Score = SC;
		Question = AN;
		AnswerTimer = AT;
		DarkCoverTimer = DT;
		StarsNum = SN;
		isCorrect = IC;
	}
}
