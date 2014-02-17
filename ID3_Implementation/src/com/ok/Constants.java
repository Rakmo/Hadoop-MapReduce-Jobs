/*****
This Constants class is implemented to use declared constants at any point any where in the project 
which eases out the procedure to access such constantly required variables.
By: Omkar Kannav
*****/
package com.ok;

public class Constants 
{
	static Constants global=null;
	public static  Constants getConstantsObj()
	{
		if(global==null)
			global=new Constants(); 	
		else 
			return global;
		
		return global;
	}
	String file_train = new String();
	int rows=0, cols=0;
	String[] attrNames;
	int[] attrVals;
	
	void setTestFile(String ftrain)
	{
		file_train = ftrain;
	}
	String getTestFile()
	{
		return file_train;
	}
	
	void setRows(int r)
	{
		rows = r;
	}
	int getRows()
	{
		return rows;
	}
	
	void setCols(int c)
	{
		cols = c;
	}
	int getCols()
	{
		return cols;
	}
	
	void setAttrNames(String[] attr_name) 
	{
		attrNames = attr_name;
	}
	String[] getAttrNames() 
	{
		return attrNames;
	}
	
	void setAttrVals(String[] attr_vals) 
	{
		attrVals = new int[attr_vals.length];
		for(int i=0; i<attr_vals.length; i++)
			attrVals[i] = Integer.parseInt(attr_vals[i].trim());
	}
	int[] getAttrVals() 
	{
		return attrVals;
	}
}
