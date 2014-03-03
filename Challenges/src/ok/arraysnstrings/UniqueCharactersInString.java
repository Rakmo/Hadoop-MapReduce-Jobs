/* Implement an algorithm to determine if a string has all unique characters. What if you can not use additional data structures? */

package ok.arraysnstrings;

public class UniqueCharactersInString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s1 = "vannak";
		String s2 = "rakmo";
		
		boolean flag = checkForUniqueChars(s2);
		if(flag == false)
			System.out.println(s1+" is having all the unique characters.");
		else
			System.out.println(s1+" does contain some duplicate characters.");

	}

	private static boolean checkForUniqueChars(String str) {
	
		boolean[] charVals = new boolean[256];
		for(int i=0; i<str.length(); i++)
		{
			int val = (int)str.charAt(i);
			if(charVals[val]) return true;
			charVals[val] = true;
		}
		return false;
	}

}
