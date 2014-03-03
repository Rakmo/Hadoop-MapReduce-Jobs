/*
 * Author: Omkar Kannav
 * Objective of this class: TO flatten a nested array of integers and strings
 * */

package ok.arraysnstrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlattenNestedArray 
{
	public static void main(String[] args) 
	{	
		// Input Nested Arrays
		List<Object> givenList = makeList(makeList(1, 2, makeList(3)), 4); //  [[1,2,[3]],4]
		System.out.println("Given Nested Array List:\n"+givenList);
		
		// Converting nested arrays to a string
		String givenListString = givenList.toString();
		System.out.println("\nString of Nested Array List:\n"+givenListString);
		System.out.println("\n");
		
		// Flattening the string of nested arrays  
		ArrayList<Object> aList = flattenList(givenListString);
		System.out.println("Flatten Array List:\n"+aList);
	}
	
	private static List<Object> makeList(Object ...a)	
	{
		return asList(a);
	}

	private static List<Object> asList(Object[] a) 
	{
		return Arrays.asList(a);
	}
	
	private static ArrayList<Object> flattenList(String givenListString)	
	{	
		char[] givenListCharArray = givenListString.toCharArray();
		List<Object> aList = new ArrayList<Object>();
		for(int i=0; i<givenListCharArray.length; i++)
		{
			// Looping the all the characters in the string and extracting only the objects and ignoring ',' && '[' && ']' && ' ' 
			if(givenListCharArray[i]!=',' && givenListCharArray[i]!='[' && givenListCharArray[i]!=']' && givenListCharArray[i]!=' ')
				aList.add(givenListCharArray[i]);
		}
		return (ArrayList<Object>) aList;
	}
}
