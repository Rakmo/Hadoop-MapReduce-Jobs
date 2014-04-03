package com.ok.variantD;

import java.util.ArrayList;

public class ABOpening{
	
	private static int countSE;
	private static int countAB;
	
	public static void setCountSE(int n)
	{
		countSE = n;
	}
	
	public static int getCountSE()
	{
		return countSE;
	}
	
	public static void setCountAB(int n)
	{
		countAB = n;
	}
	
	public static int getCountAB()
	{
		return countAB;
	}
	

	public static String minMax(int depth, String boardPosition, int alpha, int beta, char player)
	{
		if(depth == 0)
		{
			setCountSE(getCountSE()+1);
			return MoveGenerator.staticEstimateOpening(boardPosition, player)+"::"+boardPosition;
		}
		else
		{

			
			int v = Integer.MAX_VALUE;
			ArrayList<String> L = MoveGenerator.generateMovesOpening(boardPosition, player);
			String nextPosition = null;
			for (String l : L) 
			{
				String t = maxMin(depth-1, l, alpha, beta, player);
				//System.out.println("t = "+t);
				String temp[] = t.split("::");
				int vFromParent = Integer.parseInt(temp[0]);
				
				//System.out.println("Length: "+temp.length);
				if(v>vFromParent)
					nextPosition = l;
									
				v = Math.min(v, vFromParent);
				
				if(v<=alpha)
					return v+"::"+nextPosition;
				else
					beta = Math.min(v, beta);
			
			}
			setCountAB(v);
			
			
			return v+"::"+nextPosition;
		}
		
	}
	
	public static String maxMin(int depth, String boardPosition, int alpha, int beta, char player)
	{
		if(depth == 0)
		{
			setCountSE(getCountSE()+1);
			return MoveGenerator.staticEstimateOpening(boardPosition, player)+"::"+boardPosition;
		}
		else
		{

			
			int v = Integer.MIN_VALUE;
			ArrayList<String> L = MoveGenerator.generateMovesOpening(boardPosition, player);
			String nextPosition = null;
			for (String l : L) 
			{
				String t = minMax(depth-1, l, alpha, beta, player);
				//System.out.println("t = "+t);
				String temp[] = t.split("::");
				int vFromParent = Integer.parseInt(temp[0]);
				//System.out.println("Length: "+temp.length);
				
				if(v<vFromParent)
					nextPosition = l;
									
				v = Math.max(v, vFromParent);
				
				if(v>=beta)
					return v+"::"+nextPosition;
				else
					alpha = Math.max(v, alpha);
			
			}
			setCountAB(v);
			
			
			return v+"::"+nextPosition;
		}
	}
}
