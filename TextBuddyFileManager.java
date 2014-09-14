import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

/**
 * This class will be responsible for all the File reading and writing operation
 * for TextBuddy. Whenever there is a need to write the data back or read the data
 * from the text input file this class will be used.
 * 
 * @author Tan Young Sing (A0111824R)
 *
 */

public class TextBuddyFileManager {
	
	private File inputFile;
	private ArrayList<TextEntry> textEntriesList;
	
	public TextBuddyFileManager(File inputFile) {
		this.inputFile = inputFile;
		this.textEntriesList = new ArrayList<TextEntry>();
		readFromFile();
	}
	
	/**
	 * This operation will be use to read file from the input file and
	 * the entries will be stored into an Arraylist for processing. 
	 */
	private boolean readFromFile() {
		String entries = null;
		BufferedReader bReader = null;
		try {
        	bReader = new BufferedReader(new FileReader(inputFile));
        	while ((entries = bReader.readLine()) != null) {
        		TextEntry textEntry = new TextEntry(entries); 
        	    textEntriesList.add(textEntry);
        	}
        } catch (IOException e) {
        	return false;
        } 
		
		return closeReaderStream(bReader);
	}
	
	/**
	 * This operation will be use for writing the data that are currently resides in the 
	 * Arraylist back to the storage text file.
	 * 
	 * @param textEntriesList stores the current data in the arraylist.
	 * 
	 * @return the boolean flag will be used to inform if there is any caught IOexception
	 *         while writing the data back to the file.
	 */
	public boolean writeBackToFile (ArrayList<TextEntry> textEntriesList) {
		this.textEntriesList = textEntriesList; 
		return writeBackToFile();
	}
	
	private boolean writeBackToFile() {
		BufferedWriter bWriter = null;
		try { 
			bWriter = new BufferedWriter(new FileWriter(inputFile));
			for (TextEntry entry : textEntriesList) {
				bWriter.write(entry.toString());
				bWriter.newLine();
			}
		} catch (IOException e) {
			return false;
		} 
		
		return closeWriterStream(bWriter);
	} 
	
	private boolean closeWriterStream(BufferedWriter bw) {
		try {
			bw.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	private boolean closeReaderStream(BufferedReader br) {
		try {
			br.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	public ArrayList<TextEntry> getTextEntriesList() {
		return textEntriesList;
	}
}
