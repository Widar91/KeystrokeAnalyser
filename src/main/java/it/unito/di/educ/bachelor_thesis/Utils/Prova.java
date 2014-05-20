package it.unito.di.educ.bachelor_thesis.utils;

import it.unito.di.educ.bachelor_thesis.data_collection.Sample;
import it.unito.di.educ.bachelor_thesis.data_collection.UserCollector;
import it.unito.di.educ.bachelor_thesis.data_collection.users.Impostor;
import it.unito.di.educ.bachelor_thesis.data_collection.users.User;
import it.unito.di.educ.bachelor_thesis.experiments.Distance;
import it.unito.di.educ.bachelor_thesis.experiments.PerformTasks;
import it.unito.di.educ.bachelor_thesis.experiments.Results;

import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JOptionPane;


public class Prova {
	public static void main(String[]args) throws Exception{
		
		int min, sec;
		long startTime, endTime;
		Options options = Options.getInstance();

		startTime = System.currentTimeMillis();
		
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/raUsers");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaSamples/SampleUsers");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/KEYSTROKE-SAMPLES-31-USERS/USERS");
		
		options.setRNGraph(false, 2);
		options.setANGraph(false, 2);
		
		options.setRNGraph(false, 3);
		options.setANGraph(false, 3);
		
		options.setRNGraph(true, 4);
		options.setANGraph(true, 4);
		//Options.setRNGraph(false, 4);
		//Options.setANGraph(false, 4);
		
		options.setRNGraph(false, 5);
		options.setANGraph(false, 5);
		
		options.setK(0.5);
		options.setT(1.1);
		
		options.normalizedWeightR(false);
		options.normalizedWeightA(false);
		
		options.setMinSharedNGraphs(1);
		options.setMinNGraphDuration(20);
		options.setMaxNGraphDuration(1000);
		options.setMaxNumberOfSamples(20000);
		options.setMinNumberOfSamples(1);
		options.setLowerCase(true);
		
		Utils.setDebugMode(false);
		Utils.debug("----------  START  ----------");
		
		authenticationRun();
		//printAllADistances();
		//UsersTest();
		//NGraphTest();
		//A_R_DistanceTest();
		//meanDistanceTest(false);
		//modelDistanceTest();
		//classificationTest();
		//identificationTest();
		//authenticationTest();
		//sigleAuthenticationTest();

		endTime = System.currentTimeMillis();
		min = (int)((endTime - startTime)/(Math.pow(10, 3)))/60;
		sec = (int)((endTime - startTime)/(Math.pow(10, 3)))%60;
		System.out.println("Operation completed in "+ min +"m "+ sec +"s.");

	}
	
	private static void authenticationRun() {
		
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/40 USERS SAMPLES/40-UTENTI-ORIGINALS");
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList();
		
		UserCollector.getInstance().collectImpostors("/home/eddy/Dropbox/Scuola/Tesi/40 USERS SAMPLES/IMPOSTORS-ORIGINALS");
		Impostor impostors = UserCollector.getInstance().getImpostors(); 
		
		int lc = 0, ic = 0, uic = 0;
		
		String resultUserName;
		Sample[] allUserSamples;
		User[] reducedUsersList;
		
		for (User user : usersList){
			allUserSamples = user.getAllUserSamples();
			
			/* Performs authentication for each sample in the Users's profile */
			for(Sample sample : allUserSamples) {		
				
				/* LEGAL CONNECTIONS */
				if(!(user.getUserName().contains("IMPOSTOR") || user.getUserName().contains("impostor"))) { 
					
					lc++;
				}
				
				/* each iteration removes a sample from all profiles in order to increase the number of attempts */
				for(int j = 0; j < allUserSamples.length; j++) {	
					
					reducedUsersList = UserCollector.getInstance().getReducedUsersList(j);
					
					/* if the sample is an Impostor then let's try to authenticate it as each of the legal users --> IMPOSTORS CONNECTION*/
					if(user.getUserName().contains("IMPOSTORS")) { 			
						
						for(User u : reducedUsersList) 
							ic++;
					}
					
					/* else the sample is from a Legal User so let's use it as in Impostor */
					else { 															
						
						/* for each user */
						for(User u : reducedUsersList)		
							
							/* if the sample does not come from the same user then try to authenticate it */
							if(!(sample.getSampleUserName().equals(u.getUserName())))								
								uic++;
						
					}
					
				}
			}
		}
		
		allUserSamples = impostors.getAllUserSamples();
		
		/* Performs authentication for each sample in the Users's profile */
		for(Sample sample : allUserSamples) {		
			
			
			/* each iteration removes a sample from all profiles in order to increase the number of attempts */
			for(int j = 0; j < 15; j++) {	
				
				reducedUsersList = UserCollector.getInstance().getReducedUsersList(j);
				
				/* if the sample is an Impostor then let's try to authenticate it as each of the legal users --> IMPOSTORS CONNECTION*/
				if(impostors.getUserName().contains("IMPOSTOR")) { 			
					
					for(User u : reducedUsersList) 
						ic++;
				}
			}
		}
		System.out.printf("legal conn: %d, impostors conn: %d, users impostor conn: %d\n", lc, ic, uic);
	}
	
	private static void printAllADistances(){
		
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/40 USERS SAMPLES/40-UTENTI-ORIGINALS");
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		Arrays.sort(usersList, new Comparator<User>(){
			public int compare(User arg0, User arg1) {
				return arg0.getUserName().compareToIgnoreCase(arg1.getUserName());
			}});
		
		for(int i = 0; i < usersList.length; i++){
			
			User u1 = usersList[i];
			Sample [] s1 = u1.getAllUserSamples();
			
			for(int k = 0; k < u1.getAllUserSamples().length; k++) {
				
				for(int j = 0; j < i; j++) {		/* print distances with all previous users */
					
					User u2 = usersList[j];
					Sample [] s2 = u2.getAllUserSamples();
					for(int z = 0; z < u2.getAllUserSamples().length; z++) {
						//System.out.printf("%s %s %.6f\n", s1[k].getSampleName(), s2[z].getSampleName(), Distance.getADistance(s1[k], s2[z]));
						System.out.printf("%s %s %.6f\n", s1[k].getSampleName(), s2[z].getSampleName(), Distance.getRDistance(s1[k], s2[z]));
					}
				}
				
				//for(int j = 0; j < s1.length; j++) {		/* print distances with all previous samples of the same user */
					for(int x = 0; x < k; x++) {
						//System.out.printf("%s %s %.6f\n", s1[j].getSampleName(), s1[k].getSampleName(), Distance.getADistance(s1[j], s1[k]));
						System.out.printf("%s %s %.6f\n", s1[k].getSampleName(), s1[x].getSampleName(), Distance.getRDistance(s1[k], s1[x]));
					}
				//}
			}
			//Sample [] s1 = u1.getAllUserSamples();
			
		}
	}
	
	private static void UsersTest() throws Exception {
		User u1 = new User("baldoni", "/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/profUsers/baldoni");
		u1.collectSamples();
		Sample[] slist = u1.getAllUserSamples();
		
		for(int i = 0; i < slist.length; i++){
			for(int j = i+1; j < slist.length; j++){
				//System.out.println(""+slist[i].getSampleName()+"-"+slist[j].getSampleName()+": "+ Distance.getRDistance(slist[i], slist[j]));
				System.out.println(""+slist[i].getSampleName()+"-"+slist[j].getSampleName()+": "+ Distance.getADistance(slist[i], slist[j]));
			}
		}
	}
	
	private static void NGraphTest() throws Exception {

		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaSamples/SampleUsers");
		//User [] usersList = UserCollector.collectUsers(Options.getUserFolderPath());
		
		//Sample s1 = new Sample("baldoni-14", "/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/profUsers/baldoni/baldoni-14", "baldoni");
		//Sample s2 = new Sample("baldoni-5", "/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/profUsers/baldoni/baldoni-5", "baldoni");
		
		Sample s1 = new Sample("andres-1", "/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/profUsers/andres/andres-1", "andres");
		Sample s2 = new Sample("andres-2", "/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/profUsers/andres/andres-2", "andres");
		
		//Sample s1 = new Sample("bono-15", "/home/eddy/Dropbox/Scuola/Tesi/40 USERS SAMPLES/40-UTENTI-ORIGINALS/bono/bono-15", "bono");
		//Sample s2 = new Sample("baroglio-15", "/home/eddy/Dropbox/Scuola/Tesi/40 USERS SAMPLES/40-UTENTI-ORIGINALS/baroglio/baroglio-15", "baroglio");
		
		Utils.setDebugMode(true);
		s2.collectNGraphs();
		s1.collectNGraphs();

		System.out.println("Sample1 ["+s1.getSampleName()+"], Sample2 ["+s2.getSampleName()+"]");
		System.out.println("R2(s1, s2): "+ Distance.getRDistance(s1, s2));
		System.out.println("R2(s2, s1): "+ Distance.getRDistance(s2, s1));
		System.out.println("A2(s1, s2): "+ Distance.getADistance(s1, s2));
		System.out.println("A2(s2, s1): "+ Distance.getADistance(s2, s1));

		System.out.println("["+ s1.getSampleName() +"] 2-grafi:"+ s1.getnGraphSets()[0].toString());
		System.out.println("["+ s2.getSampleName() +"] 2-grafi:"+ s2.getnGraphSets()[0].toString());

	}

	private static void A_R_DistanceTest() throws Exception{
		/* Expected results (from article)
		 * R2 = 0.6666		A2 = 0.6
		 * R3 = 1			A3 = 0.6666
		 * R23 = 0,8333		A23 = 0,6333
		 * */
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/raUsers");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/raUsers2");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/raUsers1+2");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/profUsers");
		
		//Options.setRNGraph(true, 4);
		//Options.setRNGraph(true, 5);
		
		Utils.setDebugMode(true);
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		
		/*If you want to show shared n-Graph un-comment debug lines in Distance.getRDistance */
		
		
		Sample sample1 = usersList[0].getAllUserSamples()[0];
		Sample sample2 = usersList[1].getAllUserSamples()[0];
		
		Options.getInstance().setRNGraph(true, 2);
		Options.getInstance().setANGraph(true, 2);
		Options.getInstance().setRNGraph(false, 3);
		Options.getInstance().setANGraph(false, 3);
		
		System.out.println("Sample1 ["+sample1.getSampleName()+"], Sample2 ["+sample2.getSampleName()+"]");
		//System.out.println("R2(s1, s2) grafi:"+ sample1.getnGraphSets()[0].toString());
		System.out.println("R2(s1, s2): "+ Distance.getRDistance(sample1, sample2));
		System.out.println("R2(s2, s1): "+ Distance.getRDistance(sample2, sample1));
		System.out.println("A2(s1, s2): "+ Distance.getADistance(sample1, sample2));
		System.out.println("A2(s2, s1): "+ Distance.getADistance(sample2, sample1));
		
		Options.getInstance().setRNGraph(false, 2);
		Options.getInstance().setANGraph(false, 2);
		Options.getInstance().setRNGraph(true, 3);
		Options.getInstance().setANGraph(true, 3);
		
		System.out.println("Sample1 ["+sample1.getSampleName()+"], Sample2 ["+sample2.getSampleName()+"]");
		//System.out.println("R2(s1, s2) grafi:"+ sample1.getnGraphSets()[1].toString());
		System.out.println("R3(s1, s2): "+ Distance.getRDistance(sample1, sample2));
		System.out.println("R3(s2, s1): "+ Distance.getRDistance(sample2, sample1));
		System.out.println("A3(s1, s2): "+ Distance.getADistance(sample1, sample2));
		System.out.println("A3(s2, s1): "+ Distance.getADistance(sample2, sample1));
		
		Options.getInstance().setRNGraph(true, 2);
		Options.getInstance().setANGraph(true, 2);
		
		System.out.println("Sample1 ["+sample1.getSampleName()+"], Sample2 ["+sample2.getSampleName()+"]");
		System.out.println("R23(s1, s2): "+ Distance.getRDistance(sample1, sample2));
		System.out.println("R23(s2, s1): "+ Distance.getRDistance(sample2, sample1));
		System.out.println("A23(s1, s2): "+ Distance.getADistance(sample1, sample2));
		System.out.println("A23(s2, s1): "+ Distance.getADistance(sample2, sample1));
	}
	
	private static void meanDistanceTest(boolean fromSampleUser) throws Exception{
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/raUsers");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaSamples/SampleUsers");
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/profUsers");
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		
		Utils.setDebugMode(true);		
		
		Sample sample1 = usersList[1].getAllUserSamples()[14];
		Sample[] sampleUserArray;
		double result = 0.0, rDist, aDist;
		int booleanIndex;
		
		if(fromSampleUser)
			booleanIndex = 0;
		else
			booleanIndex = 1;
			
		sampleUserArray = usersList[booleanIndex].getAllUserSamples();
		
		
		
		System.out.println("Sample <"+sample1.getSampleName()+">");
		for(int i = 0; i < sampleUserArray.length; i++){
			rDist = Distance.getRDistance(sample1, sampleUserArray[i]);
			aDist = Distance.getADistance(sample1, sampleUserArray[i]);
			result += rDist + aDist;
			System.out.println("R(s1, "+ sampleUserArray[i].getSampleName() +"): "+ rDist);
			System.out.println("A(s1, "+ sampleUserArray[i].getSampleName() +"): "+ aDist);
			System.out.println("R + A = "+ (rDist + aDist));
		}
		System.out.println("Expected md distance (with equal number of samples) = "+ (result/sampleUserArray.length));
		if(fromSampleUser) {
			System.out.println("Expected md distance \t\t= "+ ((result-1)/(sampleUserArray.length-1)));
		}
		System.out.println("Computed md("+ sample1.getSampleName() +", "+ usersList[booleanIndex].getUserName() +") \t= " + Distance.getMeanDistance(sample1, usersList[booleanIndex]));
	}
	
	private static void modelDistanceTest() throws Exception{
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/mUsers");
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		
		Sample[] sampleUserArray;
		double rDist, aDist, result;
		
		for(int i = 0; i < usersList.length; i++) {
			
			result = 0.0;
			sampleUserArray = usersList[i].getAllUserSamples();
			
			rDist = Distance.getRDistance(sampleUserArray[0], sampleUserArray[1]);
			aDist = Distance.getADistance(sampleUserArray[0], sampleUserArray[1]);
			result += (rDist + aDist);
			System.out.println("d("+ sampleUserArray[0].getSampleName() +", "+ sampleUserArray[1].getSampleName() +"): "+ (rDist + aDist));
			
			rDist = Distance.getRDistance(sampleUserArray[0], sampleUserArray[2]);
			aDist = Distance.getADistance(sampleUserArray[0], sampleUserArray[2]);
			result += (rDist + aDist);
			System.out.println("d("+ sampleUserArray[0].getSampleName() +", "+ sampleUserArray[2].getSampleName() +"): "+ (rDist + aDist));
			
			rDist = Distance.getRDistance(sampleUserArray[1], sampleUserArray[2]);
			aDist = Distance.getADistance(sampleUserArray[1], sampleUserArray[2]);
			result += (rDist + aDist);
			System.out.println("d("+ sampleUserArray[1].getSampleName() +", "+ sampleUserArray[2].getSampleName() +"): "+ (rDist + aDist));
			
			System.out.println("Expected m("+ usersList[i].getUserName() +") = " + (result/3));
			System.out.println("Computed m("+ usersList[i].getUserName() +") = " + Distance.getUserModelDistance(usersList[i]));
			
		}
	}
	
	private static void classificationTest() throws Exception {
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaSamples/SampleUsers");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/KEYSTROKE-SAMPLES-31-USERS/USERS");
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		
		if(usersList.length < 2) {
			JOptionPane.showMessageDialog(null, "There MUST be at least 2 Users in the Users' Directory in order for the application to work correctly.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Utils.setDebugMode(true);
		
		Results rs = PerformTasks.performClassificationTest(usersList);
		System.out.println("\nAttempts: "+ rs.getNumberOfAttempts() +", S["+rs.getNumberOfSuccesses()+"], F["+ rs.getNumberOfFailures() +"]");
	}
	
	private static void identificationTest() throws Exception {
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaSamples/SampleUsers");
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/KEYSTROKE-SAMPLES-31-USERS/USERS");
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		UserCollector.getInstance().collectImpostors("/home/eddy/Dropbox/Scuola/Tesi/KEYSTROKE-SAMPLES-31-USERS/IMPOSTORS");
		Impostor impostors = UserCollector.getInstance().getImpostors(); 
		
		if(usersList.length < 2) {
			JOptionPane.showMessageDialog(null, "There MUST be at least 2 Users in the Users' Directory in order for the application to work correctly.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Utils.setDebugMode(true);
		
		Results rs = PerformTasks.performIdentificationTest(usersList, impostors);
		System.out.println("\nAttempts: "+ rs.getNumberOfAttempts() +", S["+rs.getNumberOfSuccesses()+"], F["+ rs.getNumberOfFailures() +"], FA["+ rs.getNumberOfFalseAcceptances() +"], FR["+ rs.getNumberOfFalseRejections() +"]");
	}
	
	private static void authenticationTest() throws Exception {
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaSamples/SampleUsers");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/KEYSTROKE-SAMPLES-31-USERS/USERS");
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		UserCollector.getInstance().collectImpostors("/home/eddy/Dropbox/Scuola/Tesi/KEYSTROKE-SAMPLES-31-USERS/IMPOSTORS");
		Impostor impostors = UserCollector.getInstance().getImpostors(); 
		
		if(usersList.length < 2) {
			JOptionPane.showMessageDialog(null, "There MUST be at least 2 Users in the Users' Directory in order for the application to work correctly.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Utils.setDebugMode(true);
		
		Results rs = PerformTasks.performAuthenticationTest(usersList, impostors);
		System.out.println("\nAttempts: "+ rs.getNumberOfAttempts() +", S["+rs.getNumberOfSuccesses()+"], F["+ rs.getNumberOfFailures() +"], FA["+ rs.getNumberOfFalseAcceptances() +"], FR["+ rs.getNumberOfFalseRejections() +"] FAR["+ rs.getFAR() +"], FRR["+ rs.getFRR() +"]");
	}
	
	private static void sigleAuthenticationTest() throws Exception {
		Options.getInstance().setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/ProvaSamples/SampleUsers");
		//Options.setUserFolder("/home/eddy/Dropbox/Scuola/Tesi/KEYSTROKE-SAMPLES-31-USERS/USERS");
		Utils.setDebugMode(true);
		UserCollector.getInstance().collectUsers(Options.getInstance().getUserFolderPath());
		User [] usersList = UserCollector.getInstance().getUsersList(); 
		
		Options.getInstance().setRNGraph(true, 4);
		Options.getInstance().setANGraph(true, 4);
		Options.getInstance().setRNGraph(true, 5);
		Options.getInstance().setANGraph(true, 5);
		
		if(usersList.length < 2) {
			JOptionPane.showMessageDialog(null, "There MUST be at least 2 Users in the Users' Directory in order for the application to work correctly.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		UserCollector.getInstance().collectImpostors("/home/eddy/Dropbox/Scuola/Tesi/ProvaDistances/raUsers/user1");
		Impostor impostors = UserCollector.getInstance().getImpostors(); 
		Sample s = impostors.getAllUserSamples()[0];
		Utils.setDebugMode(true);
		
		System.out.println(PerformTasks.performIdentification(s, usersList, false));
	}
}
