import java.io.File;
import java.util.Scanner;

/**
 * This class contain the main logic behind TextBuddy
 * it will facilitate the data flow between the text input file
 * and the local storage. Besides, it also responsible to take in
 * user command and generate the respective feedback messages.
 * 
 * @author Tan Young Sing(A0111824R)
 *
 */
public class TextBuddyController {
	
	//System Messages - These messages serve as a feedback to the user base on their input.
	String MSG_TEXT_ADDED = "added to %s : %s";
	String MSG_TEXT_DELETED = "deleted from %s : %s";
	String MSG_CLEAR_ALL_TEXT = "all content deleted from %s";
	String MSG_NO_ENTRIES_FOUND = "%s is empty";
	String MSG_SYS_EXIT_TEXT = "Shutting down TextBuddy.";
	String MSG_WELCOME_TEXT = "Welcome to TextBuddy. %S is ready for use.";
	String MSG_SEARCH_NOT_FOUND = "Sorry there is no entries with the keyworld: %s";

	//Error System Messages - These messages are use to notify the user that TextBuddy is not running normally.
	String ERR_MSG_TEXT_DELETED = "Fail to delete selected text.";
	String ERR_MSG_FILE_NOT_FOUND = "TextBuddy is not able to locate the file in the specified directory.";
	String ERR_MSG_FAILED_TO_INITIALIZE = "Sorry, TextBuddy is unable to initialize.";
	String ERR_MSG_INVALID_COMMAND = "Invalid Command.";

	//User Prompt Messages - This message is use to prompt user for input.
	String USER_PROMPT_CMD_INPUT = "command : ";
	
	//Command - These are the constants for the respective command that user  can use for TextBuddy.
	final String OPTION_EXIT = "exit";
	final String OPTION_ADD = "add";
	final String OPTION_CLEAR = "clear";
	final String OPTION_DISPLAY = "display";
	final String OPTION_DELETE = "delete";
	final String OPTION_SORT   = "sort";
	final String OPTION_SEARCH = "search";

	private TextBuddyList<TextEntry> textEntriesList; 
	private TextBuddyFileManager fileController;
	private String fileName;
	private Scanner scn = new Scanner(System.in);

	public TextBuddyController(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * This operation is responsible to perform the first
	 * initialization of the arraylist for TextBuddy by withdrawing 
	 * any archive entries stored in the input file.
	 * 
	 * @return the boolean flag will determine if the program had
	 *         failed to initalize and terminate the program accordingly.
	 */
	public boolean init() {
		fileController = readFileByName(fileName);

		if (fileController != null) {
			textEntriesList = new TextBuddyList<TextEntry>(fileController.getTextEntriesList());
		} else {
			printOutputMsg(ERR_MSG_FAILED_TO_INITIALIZE);
			return false;
		}

		return true;
	}
	
	/**
	 * This operation is use to check if the file that is passed in through
	 * the command line args is an valid directory and it is responsible to 
	 * instanstiate the FileManager object to intiate the reading of the input
	 * textfile.
	 * 
	 * @param fileName contains the input file directory
	 * @return FileManager is being return to facilitate the read/write operation
	 *         of TextBuddy.
	 */
	private TextBuddyFileManager readFileByName(String fileName) {
		File file = new File(fileName);

		if (file.exists() && !file.isDirectory()) {
			return new TextBuddyFileManager(file);
		} else {
			printOutputMsg(ERR_MSG_FILE_NOT_FOUND);
			return null;
		}
	}

	public void start() {
		printOutputMsg(String.format(MSG_WELCOME_TEXT,fileName));
		readUserInput();
	}
	
	/**
	 * This operation is responsible to take in the input from
	 * the user and branch them according to their respective commands
	 * entered.
	 */
	private void readUserInput() {
		String input = "";
		
		while (!(input = promptUserForInput().trim().toLowerCase()).equals(OPTION_EXIT)) {
			if (input.startsWith(OPTION_ADD)) {
				addNewEntries(input);
			} else if (input.startsWith(OPTION_CLEAR)) {
				clearAllEntries();
			} else if (input.startsWith(OPTION_DISPLAY)) {
				displayAllEntries();
			} else if (input.startsWith(OPTION_DELETE)) {
				deleteEntry(input);
			} else if (input.startsWith(OPTION_SORT)) {
				sortEntry();
			} else if (input.startsWith(OPTION_SEARCH)) {
				searchEntry(input);
			} else {
				printOutputMsg(ERR_MSG_INVALID_COMMAND);
			}
		}
	}
	
	private void sortEntry () {
		if (textEntriesList.isEmpty()) {
			printOutputMsg(String.format(MSG_NO_ENTRIES_FOUND,fileName));
		} else {
			textEntriesList.sortEntrieList();
		}
	}

	private void deleteEntry(String input) {
		
		if (textEntriesList.isEmpty()) {
			printOutputMsg(String.format(MSG_NO_ENTRIES_FOUND,fileName));
		} else {
			input = removeCMDFromInput(input);
			int index = Integer.parseInt(input);
			TextEntry entry = textEntriesList.deleteEntry(index-1);
			
			if (entry == null) {
				printOutputMsg(ERR_MSG_TEXT_DELETED);
			} else {
				printOutputMsg(String.format(MSG_TEXT_DELETED, fileName ,entry.toString()));
				fileController.writeBackToFile(textEntriesList.getTextEntries());
			}
		}
	}
	
	private void searchEntry(String input) {
		String searchKey = removeCMDFromInput(input);
		String searchResult = textEntriesList.filterDisplayEntries(searchKey);
		
		if (searchResult.length() == 0) {
			printOutputMsg(String.format(MSG_SEARCH_NOT_FOUND, searchKey));
		} else {
			printOutputMsg(searchResult);
		}
	}

	private void addNewEntries(String input) {
		String entryText = removeCMDFromInput(input);
		textEntriesList.addToTextBuddyList(new TextEntry(entryText));
		fileController.writeBackToFile(textEntriesList.getTextEntries());
		printOutputMsg(String.format(MSG_TEXT_ADDED, fileName, entryText));
	}

	private void clearAllEntries() {
		textEntriesList.clearEntireList();
		fileController.writeBackToFile(textEntriesList.getTextEntries());
		printOutputMsg(String.format(MSG_CLEAR_ALL_TEXT, fileName));
	}

	private void displayAllEntries() {
		String output = textEntriesList.displayAllEntries();
		
		if (output == null) {
			printOutputMsg(String.format(MSG_NO_ENTRIES_FOUND, fileName));
		} else {
			printOutputMsg(output);	
		}
	}
	
	private String promptUserForInput () {		
		System.out.print(USER_PROMPT_CMD_INPUT);
		String input = scn.nextLine();
		return input;
	}

	private void printOutputMsg(String msg) {
		System.out.println(msg);
	}
	
	private String removeCMDFromInput(String input) {
		return input.substring(input.indexOf(" "), input.length()).trim();
	}
}
