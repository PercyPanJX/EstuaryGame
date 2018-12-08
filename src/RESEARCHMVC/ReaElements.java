package RESEARCHMVC;

import java.io.Serializable;

/**
 * including all elements that are needed by View from Model
 * all elements are public so that they are easy to use
 * @author hongbozhan
 *
 */
public class ReaElements implements Serializable{
	// game state
	public ResearchGameState gameState;
	
// score
	public int score;
	
// time 
public int time;

// sample number
public sample[] sampleList = new sample[10];

// depth of the hook
public int rolly, hooky, roll, barPercent;

// rate of roll
public double rollRate;

//info number
public int infoNum;

public int tutorialTime;
	
	/**
	 * constructor to store all elements that are needed
	 * @param a the current game state
	 * @param b the current score player gets
	 * @param c current time left
	 * @param d an arraylist of samples
	 * @param e the current y location of the middle point of the roll image
	 * @param f the current y location of hook
	 * @param g	the scale rate in height of the roll according to the depth of hook
	 * @param h the current moving rate of hook and roll
	 * @param i the percentage of completion of the progress bar
	 * @param j the specific sample representing in number
	 */
	ReaElements(ResearchGameState a, int b, int c, sample[] d, int e, int f, double g, int h, int i,int j, int k){
		gameState = a;
		score = b;
		time = c;
		sampleList = d;
		rolly = e;
		hooky = f;
		rollRate = g;
		roll = h;
		barPercent = i;
		infoNum = j;
		tutorialTime = k;
	}

}
