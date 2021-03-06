/**
 * @author Omkar Kannav
 */
package com.ok.variantD;

import java.util.ArrayList;

public class MiniMaxGame {
	
	private static int countSE;
	private static int countMinimax;
	
	public static void setCountSE(int n)
	{
		countSE = n;
	}
	
	public static int getCountSE()
	{
		return countSE;
	}
	
	public static void setCountMinimax(int n)
	{
		countMinimax = n;
	}
	
	public static int getCountMinimax()
	{
		return countMinimax;
	}
	

	public static String minMax(int depth, String boardPosition, char player)
	{
		if(depth == 0)
		{
			setCountSE(getCountSE()+1);
			return MoveGenerator.staticEstimateMidgame(boardPosition, player)+"::"+boardPosition;
		}
		else
		{			
			int v = Integer.MAX_VALUE;
			//System.out.println("In minmax, b --> "+boardPosition+" d --> "+depth);
			ArrayList<String> L = MoveGenerator.generateMovesMidgameEndgame(boardPosition, player);
			//System.out.println("In MinMax --> "+L);
			String nextPosition = null;
			for (String l : L) 
			{
				String t = maxMin(depth-1, l, player);
				//System.out.println("t = "+t);
				String temp[] = t.split("::");
				int vFromParent = Integer.parseInt(temp[0]);
				//System.out.println("Length: "+temp.length);
				if(v>vFromParent)
					nextPosition = l;
									
				v = Math.min(v, vFromParent);
			
			}
			setCountMinimax(v);
			return v+"::"+nextPosition;
		}
		
	}
	
	public static String maxMin(int depth, String boardPosition, char player)
	{
		if(depth == 0)
		{
			setCountSE(getCountSE()+1);
			return MoveGenerator.staticEstimateMidgame(boardPosition, player)+"::"+boardPosition;
		}
		else
		{
			int v = Integer.MIN_VALUE;

			//System.out.println("In minmax, b --> "+boardPosition+" d --> "+depth);
			ArrayList<String> L = MoveGenerator.generateMovesMidgameEndgame(boardPosition, player);
			//System.out.println("In MaxMin --> "+L);
			
			String nextPosition = null;
			for (String l : L) 
			{
				String temp[] = minMax(depth-1, l, player).split("::");
				int vFromParent = Integer.parseInt(temp[0]);
				if(v<vFromParent)
					nextPosition = l;
									
				v = Math.max(v, vFromParent);
			
			}
			setCountMinimax(v);
			return v+"::"+nextPosition;
		}		
	}
}
