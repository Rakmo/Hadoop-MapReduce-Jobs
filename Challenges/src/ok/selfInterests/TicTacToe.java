package ok.selfInterests;

import java.io.*;
import java.util.*;

public class TicTacToe 
{
	private static int size = 3;
	public static String mainMatrix[][] = new String[size][size];
	private static boolean visitedIndex[][] = new boolean[size][size];
	public static Map<Integer, String> coordinates = new HashMap<Integer, String>();
	public static Map<Integer, String> positionsToCheck = new HashMap<Integer, String>();
	
	
	public static void main(String[] args) throws Exception, IOException
	{
		positionsToCheck.put(1, "1,2,3::1,5,9::1,4,7");
		positionsToCheck.put(2, "1,2,3::2,5,8");
		positionsToCheck.put(3, "1,2,3::3,5,7::3,6,9");
		positionsToCheck.put(4, "4,5,6::1,4,7");
		positionsToCheck.put(5, "2,5,8::1,5,9::4,5,6::3,5,7");
		positionsToCheck.put(6, "1,2,3::1,5,9::1,4,7");
		positionsToCheck.put(7, "1,4,7::7,8,9::3,5,7");
		positionsToCheck.put(8, "2,5,8::7,8,9");
		positionsToCheck.put(9, "1,5,9::3,6,9,::7,8,9");
		
		int k = 1;
		
		System.out.println("Initial Positional State: \n");
		for(int i = 0; i<size; i++)
		{	
			for(int j = 0; j<size; j++)
			{
				mainMatrix[i][j] = ""+k;
				System.out.print("\t"+mainMatrix[i][j]);
				coordinates.put(k, ""+i+","+j);
				k++;
			}
			System.out.println();
		}
		//-------------------------------------------------------------------
		
		boolean flagWin = false;
		boolean flagSquaresFilled = false;
		boolean flagTurn = true;
		
		while(!(flagWin || flagSquaresFilled))
		{
			if(flagTurn == true)
			{
				int position = getPositionFromUser(flagTurn);
				fillThePosition(flagTurn, position);
				printCurrentState(flagTurn, position);
				flagWin = isGameFinished(flagTurn, position);
				flagSquaresFilled = areSqauresFilled(flagTurn, position);
				
				if(flagWin != true)
					flagTurn = false;
			}
			else
			{
				int position = getPositionFromUser(flagTurn);
				fillThePosition(flagTurn, position);
				printCurrentState(flagTurn, position);
				flagWin = isGameFinished(flagTurn, position);
				flagSquaresFilled = areSqauresFilled(flagTurn, position);
				
				if(flagWin != true)
					flagTurn = true;
			}
		}
		
		if(flagWin == true)
		{
			if(flagTurn == true)
				System.out.println("***** The Winner of the Game: X *****");
			else
				System.out.println("***** The Winner of the Game: O *****");
		}
		
		if(flagSquaresFilled == true)
		{
			System.out.println("___________________ Draw Game ___________________");
		}
		
	}
	

	private static boolean areSqauresFilled(boolean flagTurn, int position)
	{
		int count = 0;
		for(int i=0; i<size; i++)
		{
			for(int j = 0; j<size; j++)
			{
				if(visitedIndex[i][j] == true)
					count++;
			}
		}
		if(count == 9)
			return true;
		
		return false;
	}



	private static boolean isGameFinished(boolean flagTurn, int position)
	{
		boolean flagFinished = false;
		String checks[] = positionsToCheck.get(position).split("::");
		for(int i=0; i<checks.length; i++)
		{
			String positions[] = checks[i].split(",");
			flagFinished = checkPositions(flagTurn, positions) ;
			if(flagFinished == true)
				return flagFinished;
		}
		return flagFinished;
	}



	private static boolean checkPositions(boolean flagTurn, String[] positions) 
	{
		int countX = 0;
		int countO = 0;
		for(int i=0; i<positions.length; i++)
		{
			String ordinates[] = coordinates.get(Integer.parseInt(positions[i])).split(",");
			int x = Integer.parseInt(ordinates[0]);
			int y = Integer.parseInt(ordinates[1]);
			if(visitedIndex[x][y] == true)
			{
				if(mainMatrix[x][y].equals("X"))
					countX++;
				else if(mainMatrix[x][y].equals("O"))
						countO++;
			}
			else
			{
				return false;
			}
		}
		
		if(flagTurn == true && countX == 3)
			return true;
		else if(flagTurn == false && countO == 3)
			return true;
		else					
			return false;
	}

	static void printCurrentState(boolean turn, int position)
	{
		if(turn == true)
		{
			System.out.println("--------------------------------------");
			System.out.println("After X Turn, the current board state:");
			printMainMatrix();
		}
		else
		{
			System.out.println("--------------------------------------");
			System.out.println("After O Turn, the current board state:");
			printMainMatrix();
		}
	}
	
	static void printMainMatrix()
	{
		for(int i=0; i<size; i++)
		{
			for(int j = 0; j<size; j++)
			{
				System.out.print("\t"+mainMatrix[i][j]);
			}
			System.out.println();
		}
		
		System.out.println("--------------------------------------");
	}
	
	static void fillThePosition(boolean turn, int position)	
	{
		String[] ordinates = coordinates.get(position).split(",");
		int x = Integer.parseInt(ordinates[0]);
		int y = Integer.parseInt(ordinates[1]);
		
		if(turn == true)
		{
			mainMatrix[x][y] = "X";
			visitedIndex[x][y] = true;
		}
		else
		{
			mainMatrix[x][y] = "O";
			visitedIndex[x][y] = true;
		}
	}
	
	static int getPositionFromUser(boolean flagTurn)	
	{
		boolean validated = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int index = 0;
		if(flagTurn == true)
			System.out.print("X Turn: Enter a position -> ");
		else
			System.out.print("O Turn: Enter a position -> ");
		
		while(validated == false)
		{
			try {
				index = Integer.parseInt(br.readLine());
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//System.out.println("Please, enter a valid position (A number between 1 to 9)...");
			}
			validated = validatePositionIndex(index);
			if(validated == false)
				System.out.println("Please, enter a valid position (A number between 1 to 9)...");
			else
				return index;
		}
		return index;
	}

	static boolean validatePositionIndex(int position)
	{
		if(position > 0 && position <=9)
		{
			String[] ordinates = coordinates.get(position).split(",");
			int x = Integer.parseInt(ordinates[0]);
			int y = Integer.parseInt(ordinates[1]);
			if(visitedIndex[x][y] != true)
				return true;
		}
			
		return false;
	}
}
