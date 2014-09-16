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
	
	@BeforeClass
	public static void beforeClass() {
		tbc = new TextBuddyController("C:\\Users\\LeraLonde\\workspace\\CE2\\src\\mytextfile.txt");
		tbc.init();
	}
	
	@Test
	public void testAddNewEntries() throws NoSuchMethodException,InvocationTargetException, IllegalAccessException {
		String expected = "added to C:\\Users\\LeraLonde\\workspace\\CE2\\src\\mytextfile.txt : this task is used to test the adding functions";
		String input = "add this task is used to test the adding functions";
		
		Method method = TextBuddyController.class.getDeclaredMethod("addNewEntries", String.class);
		method.setAccessible(true);
		String output = (String) method.invoke(tbc, input);
		
		assertEquals(output, expected);
	}
	
	@Test 
	public void testDeleteEntry() throws NoSuchMethodException,InvocationTargetException, IllegalAccessException {
		Method deleteMethod = TextBuddyController.class.getDeclaredMethod("deleteEntry", String.class);
		deleteMethod.setAccessible(true);
		
		Method clearMethod = TextBuddyController.class.getDeclaredMethod("clearAllEntries");
		clearMethod.setAccessible(true);
		
		String input = "delete -1";
		String output = (String) deleteMethod.invoke(tbc, input);
		assertEquals(output, "Invalid Command.");
		
		input = "delete 1";
		output = (String) deleteMethod.invoke(tbc, input);
		assertEquals(output, "deleted from C:\\Users\\LeraLonde\\workspace\\CE2\\src\\mytextfile.txt : this task is used to test the adding functions");
		
		output = (String)clearMethod.invoke(tbc);
		assertEquals(output, "all content deleted from C:\\Users\\LeraLonde\\workspace\\CE2\\src\\mytextfile.txt");
		
		input = "delete 1";
		output = (String) deleteMethod.invoke(tbc, input);
		assertEquals(output, "C:\\Users\\LeraLonde\\workspace\\CE2\\src\\mytextfile.txt is empty");
	}
	
	@Test
	public void testSortEntries() throws NoSuchMethodException,InvocationTargetException, IllegalAccessException {
		
		
	}
}
