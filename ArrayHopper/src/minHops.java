import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class minHops {
	public static void main(String[] args) {
		//File file = new File("ArrayInput.txt");
		if(args.length != 1)
		{
			// to check whether user has entered valid input file path
			System.out.println("Please, Enter Valid Input File Path in CLA !!");
		}
		else
		{
			// file reading
			File file = new File(args[0]);
			try 
			{
				boolean flag = false;
				FileReader fileReader = new FileReader(file);
				BufferedReader br = new BufferedReader(fileReader);
				ArrayList<Integer> aList = new ArrayList<Integer>();
				String line = null;
				while((line = br.readLine()) != null)
				{
					aList.add(Integer.parseInt(line.trim()));
				}
				br.close();
			
				// if an array contains just one element
				if(aList.size() == 1)
				{
					System.out.println("0, out");
				}
				else
				{
					int i = 0; // initial hop
					ArrayList<Integer> hops = new ArrayList<Integer>();
					
					int size = aList.size();
					while(i<size-1)
					{					
						hops.add(i); 
						int val = aList.get(i); 
						//System.out.println("i and val = "+i+"and"+val);
						
						// if hop is reached to the end
						if(i+val == size-1)
						{
							hops.add(size-1);
							break;
						}
						
						// if hop is skipped the end
						if(i+val >= size)
						{
							break;
						}
						
						// if hop is still behind end
						if(i+val < size)
						{
							int max = 0;
							int nextHop = 0;
							for(int j = i+1; j<=i+val; j++)
							{
								int temp = j+aList.get(j); 
								
								if(max <= temp)
								{
									max = temp;
									nextHop = j;
								}
							}
							i = nextHop;
							if(aList.get(i) == 0)
							{
								flag = true;
								break;
							}
						}	
					}
					
					if(flag == true)
					{
						System.out.println(" FAILURE !! :(");
					}
					else
					{
						System.out.println("HOPS #: "+hops.size());
						System.out.print("FINAL HOPS: ");
						String temp2 = "";
						
						for(int k = 0; k < hops.size(); k++)
							temp2 += hops.get(k) + ", ";
						
						System.out.print(""+temp2+"out");
					}
				}
			}
			catch (Exception e) 
			{
				System.out.println("Error Caught ------> ");
				e.printStackTrace();
			}
		}
	}
}
