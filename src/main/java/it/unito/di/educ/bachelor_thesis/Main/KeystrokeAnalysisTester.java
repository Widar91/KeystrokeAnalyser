package it.unito.di.educ.bachelor_thesis.Main;



/**
Keystroke Analysis Tester
@author Eddy Bertoluzzo
*/

public class KeystrokeAnalysisTester {
	public static void main (String [] args){
		
	    java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	SystemController.getInstance().createGui();
            }
        });
		
	}
}
