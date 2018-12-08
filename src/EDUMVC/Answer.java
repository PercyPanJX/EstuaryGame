package EDUMVC;

import java.io.Serializable;

/**
 * Answer including boolean clicked to show whether the answer be clicked
 * including a map position
 * @author percypan
 *
 */
public class Answer implements Serializable{
	
	private boolean ClickedOrNot;
	private MapPosition MapPlace;
	
	Answer(){ClickedOrNot = false;}
	
	/**
	 * Set the map position for the answer
	 * @param MP the set the map position for the answer
	 */
	public void setTheAnswer(MapPosition MP) {MapPlace = MP;};
	
	/**
	 * When click the answer, set the attribute to true
	 */
	public void clickedTheAnswer() {ClickedOrNot = true;}
	
	/**
	 * Reset the answer, set the attribute to false
	 */
	public void resetTheAnswer() {ClickedOrNot = false;}
	
	/**
	 * Get the attribute of clicked or not
	 * @return the clicked or not status
	 */
	public boolean getClcikedOrNot() { return ClickedOrNot;}
	
	/**
	 * Get the attribute of map position of the answer
	 * @return the map position of this answer
	 */
	public MapPosition getMapPlace() {return MapPlace;}
}
