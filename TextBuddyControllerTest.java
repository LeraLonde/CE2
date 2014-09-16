import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TextBuddyControllerTest {

	public static TextBuddyController tbc;
	public static String fileName = "C:\\Users\\ASUS\\Desktop\\CE2_\\src\\mytextfile.txt";
	
	@BeforeClass
	public static void beforeClass() {
		tbc = new TextBuddyController(fileName);
		tbc.init();
	}
	
	@Test 
	public void testExecuteCommand() throws NoSuchMethodException,InvocationTargetException, IllegalAccessException {
		
		Method addMethod = TextBuddyController.class.getDeclaredMethod("addNewEntries", String.class);
		addMethod.setAccessible(true);
		
		Method deleteMethod = TextBuddyController.class.getDeclaredMethod("deleteEntry", String.class);
		deleteMethod.setAccessible(true);
		
		Method searchMethod = TextBuddyController.class.getDeclaredMethod("searchEntry", String.class);
		searchMethod.setAccessible(true);
		
		Method clearMethod = TextBuddyController.class.getDeclaredMethod("clearAllEntries");
		clearMethod.setAccessible(true);
		
		Method sortMethod = TextBuddyController.class.getDeclaredMethod("sortEntry");
		sortMethod.setAccessible(true);
		
		//Reset the list for testing
		clearMethod.invoke(tbc);
		
		//Test case 1: Adding a new task entry
		String expected = "added to "+ fileName +" : this task is used to test the adding functions";
		String input = "add this task is used to test the adding functions";
		String output = (String) addMethod.invoke(tbc, input);
		assertEquals(output, expected);
		
		//Test case 2: selecting an invalid index
		input = "delete -1";
		output = (String) deleteMethod.invoke(tbc, input);
		assertEquals(output, "Invalid Command.");
		
		//Test case 3: delete the latest removed task
		input = "delete 1";
		output = (String) deleteMethod.invoke(tbc, input);
		assertEquals(output, "deleted from "+fileName+" : this task is used to test the adding functions");
		
		//Test case 4: clear the list
		output = (String)clearMethod.invoke(tbc);
		assertEquals(output, "all content deleted from " + fileName);
		
		//Test case 5: removing something when the list is empty
		input = "delete 1";
		output = (String) deleteMethod.invoke(tbc, input);
		assertEquals(output, fileName + " is empty");
		
		//Test case 6: testing the sorting functions
		
		//Test case 6.1: sorting the list when it is empty
		clearMethod.invoke(tbc);
		output = (String)sortMethod.invoke(tbc);
		assertEquals(output, fileName + " is empty");
        
		//Test case 6.2: sorting the list with a list of data
		String[] inputs = {"add a5", "add a3", "add a1", "add a2", "add a4"};
		for(int i=0; i < inputs.length; i++) {
			addMethod.invoke(tbc, inputs[i]);
		}
		
		expected ="1\ta1 \n2\ta2 \n3\ta3 \n4\ta4 \n5\ta5 \n";
		output = (String)sortMethod.invoke(tbc);
		assertEquals(output, expected);
		
		//Test case 7: testing the search functions
		
		//Test case 7.1: searching the list when it is empty
		input = "search 5";
		expected = "1\ta5 \n";
		output = (String)searchMethod.invoke(tbc, input);
		System.out.println(output);
		assertEquals(output, expected);
		
		//Test case 7.2: searching for a word that does not exist
		input = "search does not exist";
		expected = "Sorry there is no entries with the keyworld: does not exist";
		output = (String)searchMethod.invoke(tbc, input);
		assertEquals(output, expected);
		
	}
}
