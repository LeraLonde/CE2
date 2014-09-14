/**
 * This class will be use to store information from each individual
 * text entry from the user.
 * 
 * @author Tan Young Sing (A0111824R)
 */

public class TextEntry implements Comparable<TextEntry> {
	
	private String textEntry;

	public TextEntry(String textEntry) {
		this.textEntry = textEntry;
	}

	public String getTextEntry() {
		return textEntry;
	}

	public void setTextEntry(String textEntry) {
		this.textEntry = textEntry;
	}
	
	public String toString() {
		return this.textEntry;
	}
	
	public int compareTo(TextEntry textEntries) throws ClassCastException {
		return textEntry.compareTo(textEntries.toString());
	}
}
