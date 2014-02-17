/*****
This class extracts the data table wrt the attribute having maximum information gain from main data table.
By: Omkar Kannav
*****/

package com.ok;

public class ProcessDataFromSecondLevel 
{
	double [][]table;
	double [] mIG;
	int flag = 0;
	
	
	public ProcessDataFromSecondLevel(double[][] table, double[] mIG, int flag) 
	{
		this.table = table;
		this.mIG = mIG;
		this.flag = flag;
		
		processTable();
	}
	
	void processTable()
	{
		
		int rows = Constants.getConstantsObj().getRows();
		int cols = Constants.getConstantsObj().getCols();
		String[] attrNames = Constants.getConstantsObj().getAttrNames();
		
		int[] attrVals = Constants.getConstantsObj().getAttrVals();
		int temp = (int)mIG[0];
		
		int vals = attrVals[temp];
		
		double[][][] eTable = new double[vals][rows][cols-1];
		
		double[] tempTable = getColumnwiseArray(table, temp-1, rows); 
		String[] a_name = new String[cols-1];
		String[] a_vals = new String[cols-1];
		System.out.println("\n\n");
		int x = 0, y = 0;
		
		int f=0;
		for(int i=0; i<cols-1; i++)
		{
			if(i==temp-1)
				continue;
			else
			{
				a_name[f]=attrNames[i];
				a_vals[f]=""+attrVals[i];
				f++;
			}
		}
		
		if(flag==0)
		{
			for(int k=0; k<vals; k++)
			{
				for(int i=0; i<rows && x<rows; i++)
				{
					if((k+1)==tempTable[i])
					{
						for(int j=0; j<cols && y<cols-1; j++)
						{
							if(j==temp-1)
								continue;
							else
							{
								
								eTable[k][x][y] = table[i][j];
								y++;
							}
						}
						x++;y=0;
					}
					
				}
				System.out.println("Table of Attribute="+attrNames[temp-1]+" Value="+(k+1));
				for(int i=0; i<x; i++)
				{
					for(int j=0; j<cols-1; j++)
					{
						System.out.print("	"+eTable[k][i][j]);
						
					}
					System.out.println("");
				}
				//new ProcessData(eTable[k], x, cols-1, a_name, a_vals);
				System.out.println();
				x=0; y=0;
			}
		}
		else
		{
			for(int k=0; k<vals; k++)
			{
				for(int i=0; i<rows && x<rows; i++)
				{
					if(k==tempTable[i])
					{
						for(int j=0; j<cols && y<cols-1; j++)
						{
							if(j==temp-1)
								continue;
							else
							{
								eTable[x][y][k] = table[i][j];
								y++;
							}
						}
						x++;y=0;
					}
					
				}
				System.out.println("Table of Attribute="+attrNames[temp-1]+" Value="+(k+1));
				for(int i=0; i<x; i++)
				{
					for(int j=0; j<cols-1; j++)
					{
						System.out.print("	"+eTable[i][j][k]);
					}
					System.out.println("");
				}
				System.out.println();
				x=0; y=0;
			}
		}
	}
	
	double[] getColumnwiseArray(double[][] mainTable, int index, int size)
	{
		double[] colSet = new double[size];
		for(int i=0; i<size; i++)
		{
			colSet[i]=mainTable[i][index];
		}
		return colSet;
	}
}
