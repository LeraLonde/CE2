import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is suppose to take care of all textEntries related operation
 * such as data processing, displaying and data formatting.
 * 
 * @author Tan Young Sing (A0111824R)
 *
 * @param <E>
 */
public class TextBuddyList<E extends Comparable<E>> {

	private ArrayList<E> textEntries;
	
	public TextBuddyList(ArrayList<E> textEntries) {
		this.textEntries = textEntries;
	}
	
	/**
	 * This operation is responsible to withdraw all the text from 
	 * each text entry stored in the list and return the formatted String
	 * back for display.
	 * 
	 * @return formattedTextEntriesOutput is being returned back to the controller for display.
	 */
	public String getDisplayAllEntries () {
		if (isEmpty()) 
			return null;
		
		int count = 1;
		String formattedTextEntriesOutput = "";
		
		for (E entries : textEntries) {
			formattedTextEntriesOutput += count + "\t" + entries.toString() + " \n";
			count++;
		}
		
		return formattedTextEntriesOutput;
	}
	
	public String getFilteredDisplayEntries (String searchKey) {
		int count = 1;
		String formattedTextEntriesOutput = "";
		
		for (E entries : textEntries) {
			String entryText = entries.toString();
			if(entryText.indexOf(searchKey) != -1) {
				formattedTextEntriesOutput += count + "\t" + entries.toString() + " \n";
				count++;
			}
		}
		
		return formattedTextEntriesOutput;
	}

	public E deleteEntry(int index) {
		// To handle the case when user enter a number that is not within the bound of an array.
		if (index >= textEntries.size()) { 
			return null;
		}
		
		E entry = textEntries.remove(index);
		return entry;
	}
	
    public void sortEntriesList() {
    	Collections.sort(textEntries);
    }
	
	public void clearEntireList() {
		textEntries.clear();
	}
	
	public ArrayList<E> getTextEntries () {
		return textEntries;
	}
	
    public boolean addToTextBuddyList(E entries) {
    	return textEntries.add(entries);
    }	
	
    public boolean isEmpty() {
    	return textEntries.isEmpty();
    }
}
