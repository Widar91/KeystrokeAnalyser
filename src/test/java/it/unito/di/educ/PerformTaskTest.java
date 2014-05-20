package it.unito.di.educ;


import it.unito.di.educ.bachelor_thesis.Main.SystemController;
import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.Utils.Utils;
import it.unito.di.educ.bachelor_thesis.data_collection.Sample;
import it.unito.di.educ.bachelor_thesis.data_collection.UserCollector;
import it.unito.di.educ.bachelor_thesis.data_collection.users.User;
import it.unito.di.educ.bachelor_thesis.experiments.Distance;
import it.unito.di.educ.bachelor_thesis.experiments.PerformTasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PerformTaskTest {
	
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

	//This is not a proper test, but, confronted with what is printed by the classification method, it shows that the latter is correct.
	@Test
	public final void performClassificationTest() {
		
		for(int i = 0; i < usersList.length; i++) {
			
			Sample sample = usersList[i].getAllUserSamples()[0];
			
			PerformTasks.performClassification(sample, usersList);
			System.out.println("Expected Mean Distance: " + Distance.getMeanDistance(sample, usersList[i]));
			
		}
	}

}
