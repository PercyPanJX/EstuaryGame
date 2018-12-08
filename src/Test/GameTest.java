package Test;
import EDUMVC.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import EDUMVC.EduModel;
import EDUMVC.EducationalGameState;
import MNGMVC.ManageGameState;
import MNGMVC.MngElements;
import MNGMVC.MngModel;
import MNGMVC.Tool;
import RESEARCHMVC.ReaModel;
import RESEARCHMVC.ResearchGameState;

class GameTest {

	@Test
	public void eduModelConstructorTest() {
		EduModel model = new EduModel();
	}
	
	@Test
	public void eduStartTheGameTest() {
		EduModel model = new EduModel();
		model.nextState();
		assertEquals(EducationalGameState.WAITING, model.getElements().GameState, "GameState should be covertime");
		model.startTheGame();
		assertEquals(EducationalGameState.COVERTIME, model.getElements().GameState, "GameState should be covertime");
	}
	
	@Test
	public void eduNextStateTest() {
		EduModel model = new EduModel();
		model.startTheGame();
		model.nextState();
		assertEquals(EducationalGameState.RULETIME, model.getElements().GameState, "nextstate is rule time");
		model.nextState();
		assertEquals(EducationalGameState.MAPTIME, model.getElements().GameState, "nextstate is maptime");
		model.nextState();
		assertEquals(EducationalGameState.BEGINNING, model.getElements().GameState, "nextstate is beginning time");
		model.nextState();
		assertEquals(EducationalGameState.CLASSTIME, model.getElements().GameState, "nextstate is class time");
		model.nextState();
		assertEquals(EducationalGameState.ENDING, model.getElements().GameState, "nextstate is ending time");
		model.nextState();
		assertEquals(EducationalGameState.SCORETIME, model.getElements().GameState, "nextstate is score showing");
		model.nextState();
		assertEquals(EducationalGameState.GAMESTOP, model.getElements().GameState, "nextstate is game stop");
		model.nextState();
		assertEquals(EducationalGameState.GAMESTOP, model.getElements().GameState, "nextstate is game stop");
	}
	
	@Test
	public void eduUpdateModelTest() {
		EduModel model = new EduModel();
		model.startTheGame();
		model.nextState();
		model.nextState();
		model.nextState();
		assertEquals(3.5, model.getElements().DarkCoverTimer, "Dark cover time is 3.5");
		model.updateModel();
		assertEquals(3.49, model.getElements().DarkCoverTimer, "Dark cover time is 3.49");
		while(model.getElements().DarkCoverTimer >= 0.01) {
			model.updateModel();
		}
		model.updateModel();
		assertEquals(EducationalGameState.CLASSTIME, model.getElements().GameState, "game state is classtime");
		
		//Class times test
		assertEquals(45.5, model.getElements().ClassTime, "class time is 45");
		model.updateModel();
		assertEquals(45.49, model.getElements().ClassTime, "class time is 44");
		while(model.getElements().ClassTime >= 0.00) {
			model.updateModel();
		}
		assertEquals(EducationalGameState.ENDING, model.getElements().GameState, "game state is endtime");
		
		//ending time test
		assertEquals(0.5, model.getElements().DarkCoverTimer, "dark covertime is 0.5");
		while(model.getElements().DarkCoverTimer >= 0.01) {
			model.updateModel();
		}
		model.updateModel();
		assertEquals(EducationalGameState.SCORETIME, model.getElements().GameState, "game state is SCORETIME");
		assertEquals(3, model.getElements().StarsNum, "Star num is 3");
		
		//score time test
		model.updateModel();
		
	}
	
	@Test
	public void	eduUpdatePickAnswerTest() {
		EduModel model = new EduModel();
		model.startTheGame();
		
		model.pickAnAnswer(0);
		model.pickAnAnswer(1);
		model.pickAnAnswer(2);
		model.pickAnAnswer(3);
		
		assertEquals(1, model.getElements().AnswerTimer,"answer timer is 1");
		
		model.nextState();
		model.nextState();
		model.nextState();
		model.nextState();
		model.nextState();
		
		for(int i = 0; i < 10; i++) {
			model.pickAnAnswer(0);
			model.pickAnAnswer(1);
			model.pickAnAnswer(2);
			model.pickAnAnswer(3);
			model.updateModel();
		}
	}
	
	@Test
	public void	eduLoadGameTest() {
		EduModel model = new EduModel();
		model.startTheGame();
		EduElements oldElements = model.getElements();
		model.loadGame(oldElements);
	}
	

	
	@Test
	public void reaModelConstructorTest() {
		ReaModel reaModel = new ReaModel();
		
	}
	
	
	@Test
	public void reaStartTheGameTest() {
		ReaModel reaModel = new ReaModel();
		reaModel.nextState();
		assertEquals(ResearchGameState.WAITING, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.startTheGame();
		assertEquals(ResearchGameState.COVERTIME, reaModel.getElements().gameState, "GameState should be covertime");
		
	}
	 
	
	@Test
	public void reaNextStateTest() {
		ReaModel reaModel = new ReaModel();
		reaModel.startTheGame();
		reaModel.nextState();
		assertEquals(ResearchGameState.CONSTRUCTION, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.nextState();
		assertEquals(ResearchGameState.TUTORIAL, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.nextState();
		assertEquals(ResearchGameState.RULETIME, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.nextState();
		assertEquals(ResearchGameState.GAMETIME, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.nextState();
		assertEquals(ResearchGameState.SCORETIME, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.nextState();
		assertEquals(ResearchGameState.GAMESTOP, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.nextState();
		assertEquals(ResearchGameState.GAMESTOP, reaModel.getElements().gameState, "GameState should be covertime");
		reaModel.nextState();
		
		reaModel.GameState = ResearchGameState.INFOTIME;
		reaModel.nextState();
		assertEquals(ResearchGameState.SCORETIME, reaModel.getElements().gameState, "GameState should be covertime");
	}
	
	
	@Test
	public void reaUpdateModelTest() {
		ReaModel reaModel = new ReaModel();
		reaModel.startTheGame();
		reaModel.nextState();
		reaModel.nextState();
		// tutorial
		assertEquals(0, reaModel.getElements().tutorialTime, "tutorialTime is 0");
		reaModel.updateModel();
		assertEquals(1, reaModel.getElements().tutorialTime, "tutorialTime is 0");
		
		reaModel.nextState();
		reaModel.nextState();
		// game start
		assertEquals(45, reaModel.getElements().time, "time is 45");
		reaModel.updateModel();
		assertEquals(44, reaModel.getElements().time, "time is 44");
		reaModel.mouseClicking();
		assertEquals(true, reaModel.isClicked, "mouse is clicked");
		reaModel.updateGame();
		
		// scale function
		reaModel.scale(1,1);
		assertEquals(1, reaModel.xRate, "xRate is 1");
		assertEquals(1, reaModel.yRate, "yRate is 1");
		
		// show information function
		reaModel.showInfo(1);
		assertEquals(ResearchGameState.INFOTIME, reaModel.getElements().gameState, "GameState should be covertime");
		assertEquals(1, reaModel.getElements().infoNum, "info number should be 1");
		
		// bar percentage
		reaModel.barPercent = 100;
		reaModel.updateModel();
		assertEquals(0, reaModel.score, "score number should be 1");

		
		// time ending
		reaModel.GameState = ResearchGameState.GAMETIME;
		reaModel.gameTime = 0;
		reaModel.updateModel();
		assertEquals(ResearchGameState.SCORETIME, reaModel.getElements().gameState, "GameState should be covertime");

	
	}
	
	@Test
	public void reaLoadGameTest() {
		ReaModel reaModel = new ReaModel();
		reaModel.nextState();
		reaModel.nextState();
		reaModel.nextState();
		reaModel.nextState();
		reaModel.updateModel();
		reaModel.updateModel();
		reaModel.updateModel();
		reaModel.updateModel();
		ReaModel model = new ReaModel();
		reaModel.loadGame(model.getElements());
		assertEquals(ResearchGameState.WAITING, reaModel.getElements().gameState, "GameState should be covertime");
	}
	
	@Test
	public void reaGetLayerNumberTest() {
		ReaModel reaModel = new ReaModel();
		assertEquals(5, reaModel.getSampleLayer(90), "layer number should be 5");
		assertEquals(4, reaModel.getSampleLayer(70), "layer number should be 4");
		assertEquals(3, reaModel.getSampleLayer(40), "layer number should be 3");
		assertEquals(2, reaModel.getSampleLayer(20), "layer number should be 2");
		assertEquals(1, reaModel.getSampleLayer(5), "layer number should be 1");
	}
	

	
	@Test
	public void mngConstructorTest() {
		MngModel mngModel = new MngModel();
	}
	
	@Test
	public void mngStartTheGameTest() {
		MngModel mngModel = new MngModel();
		mngModel.nextState();
		assertEquals(ManageGameState.WAITING, mngModel.getElements().GameState, "GameState shoule be covertime");
		mngModel.startTheGame();
		assertEquals(ManageGameState.COVERTIME, mngModel.getElements().GameState, "GameState should be covertime");
	}	
	
	@Test
	public void mngNextStateTest() {
	
		MngModel mngModel = new MngModel();
		mngModel.startTheGame();
		assertEquals(ManageGameState.COVERTIME, mngModel.getElements().GameState, "nextstate is covertime");
		mngModel.nextState();
		assertEquals(ManageGameState.RULETIME, mngModel.getElements().GameState, "nextstate is waitingtime");
		mngModel.nextState();
		assertEquals(ManageGameState.RULETIME2, mngModel.getElements().GameState, "nextstate is ruletime");
		mngModel.nextState();
		assertEquals(ManageGameState.BEGINNING, mngModel.getElements().GameState, "nextstate is ruletime");
		mngModel.nextState();
		assertEquals(ManageGameState.GAMETIME, mngModel.getElements().GameState, "nextstate is ruletime");
		mngModel.nextState();
		assertEquals(ManageGameState.ENDTIME, mngModel.getElements().GameState, "nextstate is ruletime");
		mngModel.nextState();
		assertEquals(ManageGameState.SCORETIME, mngModel.getElements().GameState, "nextstate is ruletime");
		mngModel.nextState();
		assertEquals(ManageGameState.GAMESTOP, mngModel.getElements().GameState, "nextstate is ruletime");
		mngModel.nextState();
		assertEquals(ManageGameState.GAMESTOP, mngModel.getElements().GameState, "nextstate is ruletime");
		mngModel.nextState();
	
		
	}
	
	@Test
	public void mngUpdateModelTest() {
		
		
		MngModel model = new MngModel();
		model.startTheGame();
		

		for(int i =0; i < 10000; i ++) {
			model.updateModel();
		}
		
		model.startTheGame();
		
		model.nextState();
		model.updateModel();
		model.nextState();
		model.updateModel();
		model.nextState();
		
		model.updateModel();
		model.nextState();
		model.updateModel();
		model.nextState();
		model.updateModel();
		
		MngElements e = model.getElements();
		e.GameState = ManageGameState.BEGINNING;
		e.trashNum = 3;
		e.spotsNum = 2;
		e.invasiveNum = 2;
		e.DarkCoverTimer = 0;
		model.loadGame(e);
		model.updateModel();

		
		model.chooseTool(Tool.REPORT);
		
		model.checkSelection(Tool.REPORT);

		model.checkSelection(Tool.SPRAY);
		
		for(int i =0; i < 10000; i ++) {
			model.updateModel();
		}
		
		model.chooseTool(Tool.REPORT);
		
		model.checkSelection(Tool.REPORT);

		model.checkSelection(Tool.SPRAY);
		
		e.GameState = ManageGameState.GAMETIME;
		e.spotsNum = 4;
		e.trashNum = 10;
		e.invasiveNum = 6;
		model.loadGame(e);
		model.updateModel();
		model.gameOver();
		
	}
	
}
