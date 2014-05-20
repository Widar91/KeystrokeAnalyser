package it.unito.di.educ;

import static org.junit.Assert.assertEquals;
import it.unito.di.educ.bachelor_thesis.Main.SystemController;
import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.Utils.Utils;
import it.unito.di.educ.bachelor_thesis.data_collection.Sample;
import it.unito.di.educ.bachelor_thesis.data_collection.UserCollector;
import it.unito.di.educ.bachelor_thesis.data_collection.users.User;
import it.unito.di.educ.bachelor_thesis.experiments.Distance;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DistanceTest {

	private static User[] usersList;
	
	@Before
	public void setUp() throws Exception {
		Utils.setDebugMode(false);	
		Options.getInstance().setUserFolder("/home/eddy/40-UTENTI-ORIGINALS");
		SystemController.getInstance().createGui();		/* Gui must be created in order to manage nullPointerExceptions caused from messages to it!*/
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		usersList = UserCollector.getInstance().getUsersList(); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetMeanDistance() {
		
		//md(s, X) where s not in X
		for (int j = 0; j < usersList[0].getAllUserSamples().length; j++) {
			
			double expectedResult = 0.0, testResult;
			
			Sample sample1 = usersList[0].getAllUserSamples()[j];
			User testUser = usersList[new Random().nextInt(38) + 1];
			Sample[] sampleUserArray = testUser.getAllUserSamples();
			
			for(int i = 0; i < sampleUserArray.length; i++)
				expectedResult += Distance.getRDistance(sample1, sampleUserArray[i]) + Distance.getADistance(sample1, sampleUserArray[i]);
			
			expectedResult = expectedResult/sampleUserArray.length; 
			testResult = Distance.getMeanDistance(sample1, testUser);
			
			System.out.println("Risultati ("+sample1.getSampleName() + ", " + sampleUserArray[0].getSampleUserName() + ") devono essere uguali: " + expectedResult + ", " + testResult);
			assertEquals("Uguali: ", expectedResult, testResult, 0.1);
		}
		
		//md(s, X) where s in X
		for (int j = 0; j < usersList[0].getAllUserSamples().length; j++) {
			
			double expectedResult = 0.0, testResult;
			
			Sample sample1 = usersList[0].getAllUserSamples()[j];
			User testUser = usersList[0];
			Sample[] sampleUserArray = testUser.getAllUserSamples();
			
			for(int i = 0; i < sampleUserArray.length; i++)
				expectedResult += Distance.getRDistance(sample1, sampleUserArray[i]) + Distance.getADistance(sample1, sampleUserArray[i]);
			
			expectedResult = expectedResult/(sampleUserArray.length-1); 
			testResult = Distance.getMeanDistance(sample1, testUser);
			
			System.out.println("Risultati ("+sample1.getSampleName() + ", " + sampleUserArray[0].getSampleUserName() + ") devono essere uguali: " + expectedResult + ", " + testResult);
			assertEquals("Uguali: ", expectedResult, testResult, 0.1);
		}
		
	}

	@Test
	public final void testGetUserModelDistance() {
		
		for (int k = 0; k < usersList.length; k++) {
			
			double expectedResult = 0.0, testResult;
			
			User testUser = usersList[k];
			Sample[] allUserSamples = testUser.getAllUserSamples();
			
			for(int i = 0; i < allUserSamples.length; i++) 
				for(int j = i+1; j < allUserSamples.length; j++) 
					expectedResult += Distance.getRDistance(allUserSamples[i], allUserSamples[j]) + Distance.getADistance(allUserSamples[i], allUserSamples[j]);
			
			expectedResult = expectedResult/(14+13+12+11+10+9+8+7+6+5+4+3+2+1); 
			testResult = Distance.getUserModelDistance(testUser);
			
			System.out.println("Risultati ("+testUser.getUserName()+") devono essere uguali: " + expectedResult + ", " + testResult);
			assertEquals("Uguali: ", expectedResult, testResult, 0.1);
			
		}
		
	}

}