/**
 * This is the main class file for TextBuddy.
 * 
 * The following assumption is made :
 * 
 * 1) if the file directory is invalid, textBuddy will terminate immediately.
 * 2) Option A : For every operation done by the user, the data are being written back
 *               to the input file. 
 * 
 * @author Tan Young Sing (A0111824R)
 *
 */
public class TextBuddy {
	
	public static void main(String[] args) {
		
		TextBuddyController tbControl = new TextBuddyController(args[0]);
		
		if( tbControl.init() ) {
			tbControl.executeCommand();
		} else {
			//failed to initialize.
			return;
		}
		
	}
}

