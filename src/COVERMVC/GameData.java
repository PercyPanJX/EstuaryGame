package COVERMVC;
import java.io.Serializable;

import EDUMVC.EduElements;
import MNGMVC.MngElements;
import RESEARCHMVC.ReaElements;
import WholeGameControl.*;

/**
 * game data for the saving and load the game
 * @author percypan
 *
 */
public class GameData implements Serializable{
	
	public GamePicked gamePicked;
	public EduElements eduGameData;
	public ReaElements reaGameData;
	public MngElements mngGameData;
	
	/**
	 * game data constructor will initialize the game picked to the cover
	 */
	public GameData(){
		gamePicked = GamePicked.COVER;
	}
	
	/**
	 * the method to save the data of educational game
	 * @param eduGameData
	 */
	public void saveEduGameData(EduElements eduGameData) {
		this.eduGameData = eduGameData;
	}
	
	/**
	 * the method to save the data of research game
	 * @param reaGameData
	 */
	public void saveReaGameData(ReaElements reaGameData) {
		this.reaGameData = reaGameData;
	}
	
	/**
	 * the method to save the data of research game
	 * @param mngGameData
	 */
	public void saveMngGameData(MngElements mngGameData) {
		this.mngGameData = mngGameData;
	}
}
