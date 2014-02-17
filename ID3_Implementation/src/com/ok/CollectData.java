/*****
This is the CollectData class through which reading of input file (which contains training data) is done.
From this class, Attribute Names, their respective number of values and the main table (training data) are collected.
By: Omkar Kannav
*****/

package com.ok;

import java.io.BufferedReader;
import java.io.FileReader;

public class CollectData 
{
	public void getAttributesDetails(String file_train)
	{
		try
		{
			FileReader fr1 = new FileReader(file_train);
			BufferedReader br = new BufferedReader(fr1);
			String temp = null;
			temp = br.readLine();
			if(temp.isEmpty())
				System.out.println("Invalid Input File");
			else
			{
				String[] parts = temp.split(" ");
				String[] attr_name = new String[parts.length/2];
				String[] attr_vals = new String[parts.length/2];
				System.out.println("First line of the Input File:");
				for(int i=0; i<parts.length; i++)
					System.out.print("	"+parts[i]);
				System.out.println();
				int k=0;
				for(int i=0; i<parts.length-1; i=i+2)
				{	
					attr_name[k] = parts[i]; 				//Collecting attributes' names
					attr_vals[k] = parts[i+1];				//Collecting number of values of a respective attributes'
					k++;
				}

				Constants.getConstantsObj().setAttrNames(attr_name);
				Constants.getConstantsObj().setAttrVals(attr_vals);
				
				System.out.println("Attribute Names:");
				for(int i=0; i<attr_name.length; i++)
					System.out.print("	"+attr_name[i]); 	//Printing all attributes' names
				System.out.println();			
				
				System.out.println("Attribute Values:");
				for(int i=0; i<attr_vals.length; i++)
					System.out.print("	"+attr_vals[i]);	//Printing number of values of a respective attributes'
				System.out.println();
				
				int count_lines=0;
				while((temp = br.readLine()) != null) 
				{ 
					count_lines++;
				} 
				
				System.out.println("No. of lines: "+count_lines);
				
				getMainTable(file_train, count_lines-1, attr_name.length+1, attr_name, attr_vals);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void getMainTable(String file_train, int m, int n, String[] attr_name, String[] attr_vals)
	{
		//Fnction to get main training data values
		FileReader fr2;
		try 
		{
			fr2 = new FileReader(file_train);
			BufferedReader br = new BufferedReader(fr2);
			String temp;
			String[][] tData = new String[m][n];
			for(int i=0; i<m+1; i++)
			{
				if(i==0)
				{
					temp=br.readLine();
					//System.out.println("---------->"+temp);
					continue;
				}
				else
				{
					temp=br.readLine();
					tData[i-1]=temp.split("	");
				}
			}
			Constants c = new Constants();
			c.setRows(m);
			c.setCols(n);
			System.out.println("\nTable Values: ");
			double[][] mainTable = new double[m][n];
			for(int i=0; i<m; i++)
			{
				for(int j=0; j<n; j++)
				{
					System.out.print(tData[i][j]+"	");
					mainTable[i][j]=Integer.parseInt(tData[i][j]);
				}
				System.out.println();
			}
			
			new ProcessData(mainTable, m, n, attr_name, attr_vals);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
