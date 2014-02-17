/*****
This is the main class through which the implemented ID3 algorithm is executed.
This class takes only 2 command line arguments such as "train.txt test.txt".
Sequence of execution of classes: ID3byOK.java -> CollectData.java -> ProcessData.java -> ProcessDataFromSecondLevel.java
By: Omkar Kannav
*****/
package com.ok;
import java.io.*;

public class ID3byOK
{
	public static void main(String files[])throws IOException
	{
		try
		{
			String file_train = files[0];
			CollectData collectData = new CollectData();
			collectData.getAttributesDetails(file_train);
			String file_test = files[1];
			
			Constants.getConstantsObj().setTestFile(file_test);
			System.out.println("Test File Name: "+Constants.getConstantsObj().getTestFile());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}


