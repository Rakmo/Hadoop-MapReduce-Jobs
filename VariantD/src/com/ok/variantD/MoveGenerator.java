/**
 * Objective: Move Generator contains the main method. It also contains various methods to generate different moves. 
 * Every class description is given in the pdf files (Nine-Morris-Variant-D.pdf and Project Description)
 * @author Omkar Kannav
 * 
 */

package com.ok.variantD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

 public class MoveGenerator
{

	public static void main(String[] args) 
	{
		
		try {
				FileReader fr = new FileReader(args[0]);
				BufferedReader br = new BufferedReader(fr);			
				
				System.out.println("***** WHITE *****");
				System.out.println("Depth: "+Integer.parseInt(args[2]));
				
				String boardPosition = br.readLine();
				br.close();
				System.out.println("Input board1.txt ----> "+boardPosition);
				System.out.println("\nMiniMax Opening Result:");
				MiniMaxOpening.setCountSE(0);
				System.out.println("Next Board Position --> "+MiniMaxOpening.maxMin(Integer.parseInt(args[2]), boardPosition, 'W'));			
				System.out.println("Positions Evaluated by Static Estimation: "+MiniMaxOpening.getCountSE());
				System.out.println("MINIMAX Estimation: "+MiniMaxOpening.getCountMinimax());
				
				System.out.println("\n\nAB Opening Result:");
				ABOpening.setCountSE(0);
				System.out.println("Next Board Position --> "+ABOpening.maxMin(Integer.parseInt(args[2]), boardPosition, Integer.MIN_VALUE, Integer.MAX_VALUE, 'W'));			
				System.out.println("Positions Evaluated by Static Estimation: "+ABOpening.getCountSE());
				System.out.println("AB Estimation: "+ABOpening.getCountAB());
							
				
				//FileReader fr = new FileReader(args[0]);
				//FileReader fr2 = new FileReader("board3.txt");
				//BufferedReader br2 = new BufferedReader(fr2);
				
				//int tempDepth = 3;
				//boardPosition = br2.readLine();
				
				System.out.println("\n\nInput board1.txt ----> "+boardPosition);
				System.out.println("\nMiniMax MidGame Result:");
				MiniMaxGame.setCountSE(0);
				System.out.println("Next Board Position --> "+MiniMaxGame.maxMin(Integer.parseInt(args[2]), boardPosition, 'W'));
				//System.out.println("Next Board Position --> "+MiniMaxGame.maxMin(tempDepth, boardPosition, 'W'));
				System.out.println("Positions Evaluated by Static Estimation: "+MiniMaxGame.getCountSE());
				System.out.println("MINIMAX Estimation: "+MiniMaxGame.getCountMinimax());
				
				
				System.out.println("\nAB MidGame Result:");
				ABGame.setCountSE(0);
				System.out.println(Integer.parseInt(args[2]));
				System.out.println("Next Board Position --> "+ABGame.maxMin(Integer.parseInt(args[2]), boardPosition, Integer.MIN_VALUE, Integer.MAX_VALUE, 'W'));
				//System.out.println("Next Board Position --> "+ABGame.maxMin(tempDepth, boardPosition, Integer.MIN_VALUE, Integer.MAX_VALUE, 'W'));
				System.out.println("Positions Evaluated by Static Estimation: "+ABGame.getCountSE());
				System.out.println("AB Estimation: "+ABGame.getCountAB());
				
				
				System.out.println("***** BLACK *****");
				
				System.out.println("Input board1.txt ----> "+boardPosition);
				System.out.println("\nMiniMax Opening Result:");
				MiniMaxOpeningBlack.setCountSE(0);
				System.out.println("Next Board Position --> "+MiniMaxOpeningBlack.maxMin(Integer.parseInt(args[2]), boardPosition, 'B'));			
				System.out.println("Positions Evaluated by Static Estimation: "+MiniMaxOpeningBlack.getCountSE());
				System.out.println("MINIMAX Estimation: "+MiniMaxOpeningBlack.getCountMinimax());
				
				System.out.println("\n\nAB Opening Result:");
				ABOpeningBlack.setCountSE(0);
				System.out.println("Next Board Position --> "+ABOpeningBlack.maxMin(Integer.parseInt(args[2]), boardPosition, Integer.MIN_VALUE, Integer.MAX_VALUE, 'B'));			
				System.out.println("Positions Evaluated by Static Estimation: "+ABOpeningBlack.getCountSE());
				System.out.println("AB Estimation: "+ABOpeningBlack.getCountAB());
				
				
				System.out.println("\n\nInput board3.txt ----> "+boardPosition);
				System.out.println("\nMiniMax MidGame Result:");
				MiniMaxGameBlack.setCountSE(0);
				System.out.println("Next Board Position --> "+MiniMaxGameBlack.maxMin(Integer.parseInt(args[2]), boardPosition, 'B'));
				//System.out.println("Next Board Position --> "+MiniMaxGame.maxMin(tempDepth, boardPosition, 'W'));
				System.out.println("Positions Evaluated by Static Estimation: "+MiniMaxGameBlack.getCountSE());
				System.out.println("MINIMAX Estimation: "+MiniMaxGameBlack.getCountMinimax());
				
				System.out.println("\nAB MidGame Result:");
				ABGameBlack.setCountSE(0);
				System.out.println("Next Board Position --> "+ABGameBlack.maxMin(Integer.parseInt(args[2]), boardPosition, Integer.MIN_VALUE, Integer.MAX_VALUE, 'B'));
				//System.out.println("Next Board Position --> "+ABGame.maxMin(tempDepth, boardPosition, Integer.MIN_VALUE, Integer.MAX_VALUE, 'W'));
				System.out.println("Positions Evaluated by Static Estimation: "+ABGameBlack.getCountSE());
				System.out.println("AB Estimation: "+ABGameBlack.getCountAB());
				
				br.close();
				//br2.close();
							
				//System.out.println("Close Mill Count: "+countMill("xxxxxxxxxWxxWxxxWxxWWWW", 'W'));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<String> generateMove(String boardPosition, char player)
	{
		ArrayList<String> L = new ArrayList<String>();
		
		for(int i=0; i<boardPosition.length(); i++)
		{
			if(boardPosition.charAt(i) == player)
			{
				ArrayList<Integer> nList = neighbors(i);
				for(int j = 0; j<nList.size(); j++)
				{
					if(boardPosition.charAt(j) == 'x')
					{
						StringBuffer b = new StringBuffer(boardPosition);
						b.setCharAt(i, 'x');
						b.setCharAt(j, player);
						if(closeMill(j, new String(b)))
							generateRemove(new String(b), L, player);
						else
							L.add(new String(b));						
					}
				}
			}
		}		
		return L;
	}

	public static ArrayList<String> generateMovesOpening(String boardPosition, char player)
	{
		ArrayList<String> list_b = new ArrayList<String>();
		list_b = generateAdd(boardPosition, player);
		return list_b;
	}
	
	public static ArrayList<String> generateAdd(String boardPosition, char player)
	{
		ArrayList<String> L = new ArrayList<String>();
		//System.out.println("inside gAdd: "+boardPosition.trim());
		for(int i=0; i<boardPosition.trim().length(); i++)
		{
			if(boardPosition.charAt(i)=='x')
			{
				StringBuffer b =new StringBuffer(boardPosition);
				b.setCharAt(i,player);
				if(closeMill(i, new String(b)))
					generateRemove(new String(b), L, player);
				else
					L.add(new String(b));
			}
		}
		
		return L;
	}
	
	public static void generateRemove(String boardPosition, ArrayList<String> L, char player)
	{
		for(int i=0; i<boardPosition.trim().length(); i++)
		{
			if(boardPosition.charAt(i)!='x' && boardPosition.charAt(i)!=player)
			{
				StringBuffer b =new StringBuffer(boardPosition);
				b.setCharAt(i, player);
				if(!closeMill(i, boardPosition))
				{					
					b.setCharAt(i, 'x');
					L.add(new String(b));
				}
			}
		}
	}
	
	public static int staticEstimateOpening(String boardPosition, char player)
	{
		int numWhite = countPieces(boardPosition, 'W');
		int numBlack = countPieces(boardPosition, 'B');
		//System.out.println("w = "+numWhite+"   b = "+numBlack);
				
		if (player == 'W' || player == 'w')
			return ((numWhite-numBlack) + MoveGenerator.countMill(boardPosition, player))%22;
		else
			return ((numBlack-numWhite) + MoveGenerator.countMill(boardPosition, player))%22;
		
	}
		
	public static int staticEstimateMidgame(String boardPosition, char player)
	{
		int numWhite = countPieces(boardPosition, 'W');
		int numBlack = countPieces(boardPosition, 'B');
		//System.out.println("w = "+numWhite+"   b = "+numBlack);
		
		
				
		if(player == 'W')
		{
			int numBlackMoves = countMoves(boardPosition, player);
			
			if (numBlack <= 2) 
				return(Integer.MAX_VALUE);
			else if (numWhite <= 2) 
				return(Integer.MIN_VALUE);
			else if (numBlackMoves == 0) 
				return(Integer.MAX_VALUE);
			else 
				return ((1000*(numWhite - numBlack) - numBlackMoves)+(MoveGenerator.countMill(boardPosition, player)))%22;
		}
		else
		{
			int numWhiteMoves = countMoves(boardPosition, player);
			
			if (numWhite <= 2) 
				return(Integer.MAX_VALUE);
			else if (numBlack <= 2) 
				return(Integer.MIN_VALUE);
			else if (numWhiteMoves == 0) 
				return(Integer.MAX_VALUE);
			else 
				return ((1000*(numBlack - numWhite) - numWhiteMoves)+(MoveGenerator.countMill(boardPosition, player)))%22;
		}
		
	}
	
	public static int countMoves(String boardPosition, char player)
	{
		if(player=='W')
			return generateMovesMidgameEndgame(boardPosition, 'B').size();
		else
			return generateMovesMidgameEndgame(boardPosition, 'W').size();
	}
	
	public static int countPieces(String boardPosition, char player)
	{
		int count = 0;
		if(player=='W')
		{
			for(int i=0; i<boardPosition.length(); i++)
			{
				if(boardPosition.charAt(i) == 'W')
					count++;
			}
			
			return count;
		}
		else
		{
			for(int i=0; i<boardPosition.length(); i++)
			{
				if(boardPosition.charAt(i) == 'B')
					count++;
			}
			
			return count;
		}
	}
	
	public static ArrayList<String> generateMovesMidgameEndgame(String boardPosition, char player)
	{
		//System.out.println("countPieces ====== "+countPieces(boardPosition, player));
		if(countPieces(boardPosition, player) == 3)
			return generateHopping(boardPosition, player);
		else
			return generateMove(boardPosition, player);
	}
	
	public static ArrayList<String> generateHopping(String boardPosition, char player)
	{
		ArrayList<String> L = new ArrayList<String>();
		for(int i=0; i<boardPosition.length(); i++)
		{
			if(boardPosition.charAt(i) == player)
			{
				for(int j=0; j<boardPosition.length(); j++)
				{
					if(boardPosition.charAt(j) == 'x')
					{
						StringBuffer b = new StringBuffer(boardPosition);
						b.setCharAt(i, 'x');
						b.setCharAt(j, player);
						if(closeMill(j, new String(b)))
							generateRemove(new String(b), L, player);
						else
							L.add(new String(b));
					}
				}
			}
		}
		return L;
	}
	
	public static int countMill(String boardPosition, char player)
	{
		int count = 0;
		String positionsToCheck[] = {"0::1::2","0::8::20","0::3::6","2::5::7","2::13::22","3::9::17","3::4::5","5::12::19","5::12::14","6::10::14","7::11::16","8::9::10","11::12::13","14::15::16","15::18::21","14::15::16","15::18::21","16::19::22","14::17::20","20::21::22","17::18::19"};
		
		for(int i=0; i<positionsToCheck.length; i++)
		{
			String temp[] = positionsToCheck[i].split("::");
			if((boardPosition.charAt(Integer.parseInt(temp[0])) == player) && (boardPosition.charAt(Integer.parseInt(temp[1])) == player) && (boardPosition.charAt(Integer.parseInt(temp[2])) == player))
				count++;
		}		
		return count;
	}
	
	public static ArrayList<Integer> neighbors(int position)
	{
		ArrayList<Integer> neighborsList = new ArrayList<Integer>();
		switch (position) 
		{
		case 0:
			neighborsList.add(1);
			neighborsList.add(3);
			neighborsList.add(8);
			break;
		case 1:
			neighborsList.add(0);
			neighborsList.add(4);
			neighborsList.add(2);
			break;
		case 2:
			neighborsList.add(1);
			neighborsList.add(5);
			neighborsList.add(13);
			break;
		case 3:
			neighborsList.add(6);
			neighborsList.add(0);
			neighborsList.add(9);
			neighborsList.add(4);
			break;
		case 4:
			neighborsList.add(1);
			neighborsList.add(3);
			neighborsList.add(5);
			break;
		case 5:
			neighborsList.add(12);
			neighborsList.add(4);
			neighborsList.add(7);
			neighborsList.add(2);
			break;
		case 6:
			neighborsList.add(10);
			neighborsList.add(3);
			neighborsList.add(7);
			break;
		case 7:
			neighborsList.add(11);
			neighborsList.add(6);
			neighborsList.add(5);
			break;
		case 8:
			neighborsList.add(0);
			neighborsList.add(20);
			neighborsList.add(9);
			break;
		case 9:
			neighborsList.add(8);
			neighborsList.add(10);
			neighborsList.add(3);
			neighborsList.add(17);
			break;
		case 10:
			neighborsList.add(9);
			neighborsList.add(6);
			neighborsList.add(14);
			break;
		case 11:
			neighborsList.add(16);
			neighborsList.add(7);
			neighborsList.add(12);
			break;
		case 12:
			neighborsList.add(11);
			neighborsList.add(13);
			neighborsList.add(5);
			neighborsList.add(19);
			break;
		case 13:
			neighborsList.add(12);
			neighborsList.add(2);
			neighborsList.add(22);
			break;
		case 14:
			neighborsList.add(10);
			neighborsList.add(15);
			neighborsList.add(17);
			break;
		case 15:
			neighborsList.add(14);
			neighborsList.add(16);
			neighborsList.add(18);
			break;
		case 16:
			neighborsList.add(11);
			neighborsList.add(15);
			neighborsList.add(19);
			break;
		case 17:
			neighborsList.add(20);
			neighborsList.add(14);
			neighborsList.add(9);
			neighborsList.add(18);
			break;
		case 18:
			neighborsList.add(17);
			neighborsList.add(19);
			neighborsList.add(15);
			neighborsList.add(21);
			break;
		case 19:
			neighborsList.add(12);
			neighborsList.add(18);
			neighborsList.add(16);
			neighborsList.add(22);
			break;
		case 20:
			neighborsList.add(8);
			neighborsList.add(21);
			neighborsList.add(17);
			break;
		case 21:
			neighborsList.add(20);
			neighborsList.add(22);
			neighborsList.add(18);
			break;
		case 22:
			neighborsList.add(13);
			neighborsList.add(19);
			neighborsList.add(21);
			break;
		}
		return neighborsList;
	}
	
	public static boolean closeMill(int position, String boardPosition)
	{
		char C = boardPosition.charAt(position);
		if(C!='x')
		{
			switch(position)
			{
			case 0: 
				if((boardPosition.charAt(8) == C && boardPosition.charAt(20) == C) || (boardPosition.charAt(3) == C && boardPosition.charAt(6) == C) || (boardPosition.charAt(1) == C && boardPosition.charAt(2) == C))
					return true;
				else
					return false;
			
			case 1:
				if(boardPosition.charAt(0) == C && boardPosition.charAt(2) == C)
					return true;
				else
					return false;
				
			case 2:
				if((boardPosition.charAt(0) == C && boardPosition.charAt(1) == C) || (boardPosition.charAt(5) == C && boardPosition.charAt(7) == C) || (boardPosition.charAt(13) == C && boardPosition.charAt(22) == C))
					return true;
				else
					return false;
				
			case 3:
				if((boardPosition.charAt(0) == C && boardPosition.charAt(6) == C) || (boardPosition.charAt(4) == C && boardPosition.charAt(5) == C) || (boardPosition.charAt(9) == C && boardPosition.charAt(17) == C))
					return true;
				else
					return false;
			
			case 4:
				if(boardPosition.charAt(3) == C && boardPosition.charAt(5) == C)
					return true;
				else
					return false;
				
			case 5:
				if((boardPosition.charAt(3) == C && boardPosition.charAt(4) == C) || (boardPosition.charAt(2) == C && boardPosition.charAt(7) == C) || (boardPosition.charAt(12) == C && boardPosition.charAt(19) == C))
					return true;
				else
					return false;
			
			case 6:
				if((boardPosition.charAt(0) == C && boardPosition.charAt(3) == C) || (boardPosition.charAt(10) == C && boardPosition.charAt(14) == C))
					return true;
				else
					return false;
			
			case 7:
				if((boardPosition.charAt(2) == C && boardPosition.charAt(5) == C) || (boardPosition.charAt(11) == C && boardPosition.charAt(16) == C))
					return true;
				else
					return false;
				
			case 8:
				if((boardPosition.charAt(0) == C && boardPosition.charAt(20) == C) || (boardPosition.charAt(9) == C && boardPosition.charAt(10) == C))
					return true;
				else
					return false;
				
			case 9:
				if((boardPosition.charAt(8) == C && boardPosition.charAt(10) == C) || (boardPosition.charAt(3) == C && boardPosition.charAt(17) == C))
					return true;
				else
					return false;
				
			case 10:
				if((boardPosition.charAt(8) == C && boardPosition.charAt(9) == C) || (boardPosition.charAt(6) == C && boardPosition.charAt(14) == C))
					return true;
				else
					return false;
				
			case 11:
				if((boardPosition.charAt(12) == C && boardPosition.charAt(13) == C) || (boardPosition.charAt(7) == C && boardPosition.charAt(16) == C))
					return true;
				else
					return false;
				
			case 12:
				if((boardPosition.charAt(11) == C && boardPosition.charAt(13) == C) || (boardPosition.charAt(15) == C && boardPosition.charAt(19) == C))
					return true;
				else
					return false;
				
			case 13:
				if((boardPosition.charAt(11) == C && boardPosition.charAt(12) == C) || (boardPosition.charAt(2) == C && boardPosition.charAt(22) == C))
					return true;
				else
					return false;
				
			case 14:
				if((boardPosition.charAt(6) == C && boardPosition.charAt(10) == C) || (boardPosition.charAt(15) == C && boardPosition.charAt(16) == C) || (boardPosition.charAt(17) == C && boardPosition.charAt(20) == C))
					return true;
				else
					return false;
				
			case 15:
				if((boardPosition.charAt(14) == C && boardPosition.charAt(16) == C) || (boardPosition.charAt(18) == C && boardPosition.charAt(21) == C))
					return true;
				else
					return false;
			
			case 16:
				if((boardPosition.charAt(14) == C && boardPosition.charAt(15) == C) || (boardPosition.charAt(19) == C && boardPosition.charAt(22) == C) || (boardPosition.charAt(7) == C && boardPosition.charAt(11) == C))
					return true;
				else
					return false;
			
			case 17:
				if((boardPosition.charAt(3) == C && boardPosition.charAt(9) == C) || (boardPosition.charAt(14) == C && boardPosition.charAt(20) == C) || (boardPosition.charAt(18) == C && boardPosition.charAt(19) == C))
					return true;
				else
					return false;
			
			case 18:
				if((boardPosition.charAt(15) == C && boardPosition.charAt(21) == C) || (boardPosition.charAt(17) == C && boardPosition.charAt(19) == C))
					return true;
				else
					return false;
			
			case 19:
				if((boardPosition.charAt(17) == C && boardPosition.charAt(18) == C) || (boardPosition.charAt(16) == C && boardPosition.charAt(22) == C) || (boardPosition.charAt(5) == C && boardPosition.charAt(12) == C))
					return true;
				else
					return false;
			
			case 20:
				if((boardPosition.charAt(8) == C && boardPosition.charAt(0) == C) || (boardPosition.charAt(14) == C && boardPosition.charAt(17) == C) || (boardPosition.charAt(21) == C && boardPosition.charAt(22) == C))
					return true;
				else
					return false;
			
			case 21:
				if((boardPosition.charAt(15) == C && boardPosition.charAt(18) == C) || (boardPosition.charAt(20) == C && boardPosition.charAt(22) == C))
					return true;
				else
					return false;
			
			case 22:
				if((boardPosition.charAt(20) == C && boardPosition.charAt(21) == C) || (boardPosition.charAt(16) == C && boardPosition.charAt(19) == C) || (boardPosition.charAt(13) == C && boardPosition.charAt(2) == C))
					return true;
				else
					return false;
				
			default: return false;
			
			}
		}
		else
		{
			return false;
		}		
	}
 } 