package logic;
/**
 * This class is used to test the Map logic class
 * 
 * @author Group 7, adapted from Almas Baimagambetov: https://www.youtube.com/
	 watch?v=l2XhUHW8Oa4&list=PLurZmf6mNWh4oNzAph6vk14xj9NdS-RCP&index=2&t=0s
 * @version 1.0
 * @since 2019-03-21
 */

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;


public class MapTest{
	Map testMap = new Map("/resources/GUI/Test.txt");
	
	/**
	 *Testing the setter method for the Char array
	 */
	@Test 
	public void test_setCharMap(){
			char [][] testarray;
			testarray = new char[][]{{'|',' ','|','#'},{' ',' ','#','#'}};
			testMap.setCharMap(testarray);
			assertArrayEquals("Set Character Map to a new array. Unexpected character returned",testarray, testMap.getCharMap());
	}
	
	/**
	 *Testing the getter method for the Char array
	 */
	@Test 
	public void test_getCharMap(){
			char [][] testarray;
			testarray = new char[][]{{'|',' ','^','#'},{' ','|','#','^'}};
			assertArrayEquals("Unexpected character array returned",testarray, testMap.getCharMap());
	}
	
	/**
	 *Testing the proper construction of the char array
	 */
	@Test 
	public void test_createdcharMap(){
			char [][] testarray;
			testarray = new char[][]{{'|',' ','^','#'},{' ','|','#','^'}};
			assertArrayEquals("Unexpected character array conversion of Map textfile",testarray, testMap.getCharMap());
	}
	
	/**
	 *Testing the getter method for the "height" of the created map 
	 */
	@Test 
	public void test_getHeight(){
			assertEquals("Unexpected height of charMap returned ",2,testMap.getHeight());
	}
	
	/**
	 *Testing the setter method for the "height" of the created map
	 */
	@Test 
	public void test_setHeight(){
			testMap.setHeight(5);
			assertEquals("Set height of charMap to 5. Unexpected height of charMap returned ",5,testMap.getHeight());
	}
	
	/**
	 *Testing the getter method for the "width" of the created map 
	 */
	@Test 
	public void test_getWidth(){
			assertEquals("Unexpected width of charMap returned ",4,testMap.getWidth());
	}
	
	/**
	 *Testing the setter method for the "width" of the created map 
	 */
	@Test 
	public void test_setWidth(){
			testMap.setWidth(2);
			assertEquals("Set width of charMap to 2. Unexpected width of charMap returned ",2,testMap.getWidth());
	}
	
	
}