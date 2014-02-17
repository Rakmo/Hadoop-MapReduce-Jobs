/*****
This is the ProcessData class, in which, after collecting main training data, ClassEntropy, Attributes' 
Entropies and Information Gain (IG) are calculated.
Then maximum IG is taken and forwarded to ProcessDataFromSecondLevel.java
By: Omkar Kannav
*****/
package com.ok;

public class ProcessData 
{
	double [][] mTable;
	int rows=0, cols=0;
	int flag=0;
	ProcessData(double[][] mainTable, int m, int n, String[] attr_name, String[] attr_vals)
	{
		mTable = mainTable;
		rows = m;
		cols = n;
		
		Constants.getConstantsObj().setRows(rows);
		Constants.getConstantsObj().setCols(cols);
		
		double colSet[] = getColumnwiseArray(mainTable, n-1, rows);
		System.out.println("Column Set -------->");
		for(int i=0; i<colSet.length; i++)
			System.out.print("	"+colSet[i]);
		int ocForClass0 = getOccurrenceCount(colSet, 0);
		int ocForClass1 = getOccurrenceCount(colSet, 1);
		System.out.println("\nCount For Class 1---------->"+ocForClass1);
		double cEntropy = calClassEntropy(ocForClass0, ocForClass1); // Class Entropy
		System.out.println("	oc0 = "+ocForClass0+"	oc1 = "+ocForClass1+"\nClass entropy ------>"+cEntropy);
		double[][] IG = new double[attr_name.length][2];
		
		for(int i=0; i<n-1; i++)
		{
			System.out.println("*********************************");
			double colSet2[] = getColumnwiseArray(mainTable, i, m);
			int attr1_vals = Integer.parseInt(attr_vals[i]);
			
			double attrEntropy = calEntropy(attr1_vals, colSet2, colSet);
			System.out.println("Entropy("+attr_name[i]+") = "+attrEntropy);
			IG[i][0] = i+1;
			IG[i][1] = calIG(cEntropy, attrEntropy);
			System.out.println("IG ----> "+IG[i][1]);
		}
		double mIG[] = maxIG(getColumnwiseArray(IG, 1, n-1));
		System.out.println("Max IG -----> "+mIG[1]+" of attribute ----> "+mIG[0]);
		
		new ProcessDataFromSecondLevel(mTable, mIG, flag);
		
	}
	
	double[] getColumnwiseArray(double[][] mainTable, int index, int size)	//Return a column array of a 2D array
	{
		double[] colSet = new double[size];
		for(int i=0; i<size; i++)
		{
			colSet[i]=mainTable[i][index];
		}
		return colSet;
	}
	
	int getOccurrenceCount(double[] set, int element)						//Return a count of occurrences of an element in an array 
	{
		int count=0;
		for(int i=0; i<set.length; i++)
		{
			if(set[i]==element)
				count++;
		}
		return count;
	}
	
	double calClassEntropy(int oc0, int oc1)								//Calculating class entropy
	{
		double cEntropy = 0;
		double t = oc0 + oc1;
		cEntropy = -(oc0/t)*Math.log(oc0/t)/Math.log(2)-(oc1/t)*Math.log(oc1/t)/Math.log(2);
		return cEntropy;
	}
	
	double calEntropy(int attr_vals, double[] colSet2, double[] colSet) 	//Calculating attribute entropy
	{
		double occurrenceCount[] = new double[attr_vals];
		double vEntropy[] = new double[attr_vals];
		double entropy = 0;
		
		double[] count0 = new double[attr_vals];
		double[] count1 = new double[attr_vals];
		
		
		
		for(int i=0; i<colSet2.length; i++)
		{
			if(colSet2[i]==0)
			{
				flag=1;
				break;
			}
		}
		
		
		for(int j=0; j<attr_vals; j++)
		{
			for(int i=0; i<colSet2.length; i++)
			{
				if(flag==0)
				{
					if(colSet2[i]==(j+1) && colSet[i]==0)
						count0[j]++;
				}
				else
				{
					if(colSet2[i]==j && colSet[i]==0)
						count0[j]++;
				}
			}
			
			if(flag==0)
				occurrenceCount[j]=getOccurrenceCount(colSet2, j+1);
			else
				occurrenceCount[j]=getOccurrenceCount(colSet2, j);
			
			count1[j]=occurrenceCount[j]-count0[j];
			
			if(flag==0)
				System.out.println("Occurrence of "+(j+1)+" Total = "+occurrenceCount[j]+":: Class 0 -> "+count0[j]+"	Class 1 -> "+count1[j]);
			else
				System.out.println("Occurrence of "+(j)+" Total = "+occurrenceCount[j]+":: Class 0 -> "+count0[j]+"	Class 1 -> "+count1[j]);
		}
		
		for(int i=0; i<attr_vals; i++)
		{
			double c0 = count0[i]/occurrenceCount[i];
			double c1 = count1[i]/occurrenceCount[i];
			if(occurrenceCount[i]==0 || c0==0 || c1==0)
				vEntropy[i] = 0;
			else
				vEntropy[i] = -c0*Math.log(c0)/Math.log(2)-(c1)*Math.log(c1)/Math.log(2);
			
			entropy += (occurrenceCount[i]/rows)*vEntropy[i];
			System.out.println(">>>>> "+vEntropy[i]);
		}
		return entropy;
	}
	
	double calIG(double cEntropy, double attrEntropy)						//Calculates Information Gain
	{
		double IG = cEntropy - attrEntropy;
		return IG;
	}
	
	double[] maxIG(double[] IG)												//Returning Maximum IG
	{
		double[] maxIG= new double[2];
		maxIG[1]=IG[0];
		 for(int i=0;i<IG.length;i++)
		 { 
			if(IG[i]>maxIG[1]) 
			{
				maxIG[1] = IG[i];
				maxIG[0] = i+1;
			}
		 }
		 //System.out.println("$$$$$$$$$$ ----> "+maxIG[0]);
		return maxIG;
	}
}
