package RESEARCHMVC;

import java.io.Serializable;

/**
 * a data type named sample including things needed for a sample
 * @author hongbozhan
 *
 */
public class sample implements Serializable{
	int number;
	int depth;
	String name;
	boolean isCollected;
	
	/**
	 * constructor for sample 
	 * @param n number of sample player got
	 * @param d the depth of sample
	 * @param name the name of the sample
	 * @param i whether the sample is collected
	 */
	public sample(int n, int d, String name, boolean i){
		this.number = n;
		this.depth = d;
		this.name = name;
		this.isCollected = i;
		
	}

}
