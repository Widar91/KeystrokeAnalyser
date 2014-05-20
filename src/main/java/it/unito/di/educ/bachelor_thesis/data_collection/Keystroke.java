package it.unito.di.educ.bachelor_thesis.data_collection;
/**
 * This class implements a Keystroke, which is a keyboard event.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
*/

public class Keystroke {
	private char character; 	/* the character memorized */
	private long when;			/* when this key was pressed*/

	/**
	* Constructor for this class.
	* @param s the typed character.
	* @param time the time at which it was pressed.
	*/
	public Keystroke(char s, long time) {
		character = s;
		when = time;
	}

	/**
	* This method returns the given keyboard event.
	* @return the character associated to this Keystroke.
	*/
	public char getChar() {
		return character;
	}

	/**
	* This method returns when the keyboard event occurred.
	* @return a long representing when (in milliseconds) the keyboard event occurred.
	*/
	public long getWhen() {
		return when;
	}
	
	/**
	 * Returns a string representation of the object where the character is ASCII coded. 
	 * @return a string representation of the object where the character is ASCII coded.
	 */
	public String toASCIIString() {
		return when + "\n" + (int)character + "\n";
	}

	public String toString() {
		return when + "\n" + character + "\n";
	}
}