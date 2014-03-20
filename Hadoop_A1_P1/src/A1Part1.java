/*Q1. Find top 10 average rated movies with descending order of rating 
 * (Use of chaining of multiple map-reduce jobs.) */

import java.io.IOException;
import java.text.DecimalFormat;

import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
        
public class A1Part1 
{       
 public static class Map extends Mapper<LongWritable, Text, Text, DoubleWritable> 
 {
     public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
     {
    	 String line = value.toString();
    	 String[] line_elements = line.toString().split("::"); // Splitting the input line with delimiter ::
    	 if(line_elements.length==4)
    	 {
	         Text movie_id = new Text(line_elements[1]);  
	         final DoubleWritable rating = new DoubleWritable(Double.parseDouble(line_elements[2]));
	         context.write(movie_id, rating);
    	 }
    	 else
    		 System.out.println("Warning! Errorneous Tuple is present !!");
            
     }
 } 

 public static class Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> 
 {	
	 public void reduce(Text movie_id, Iterable<DoubleWritable> movie_ratings, Context context) throws IOException, InterruptedException 
	 {
    	double sum = 0.0;
	 	double count = 0.0;
	 	double average = 0.0;

        for (DoubleWritable r : movie_ratings) 
        {
            sum = sum + r.get();
            count = count + 1;
        }
        average = sum/count;
        context.write(movie_id, new DoubleWritable(average));
    }
	 
	
 }
	   
 
 public static class Map_Top10 extends Mapper<LongWritable, Text, NullWritable, Text> 
 {
	// Stores a map of avg_movie_rating and string of movie_id + avg_movie_rating
	 private TreeMap<Text, Text>  ratingMap = new TreeMap<Text, Text>();
	 
     public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
     {
    	 String line = value.toString();
    	 String[] line_elements = line.toString().split("\t");
    	 
    	 
    	 
    	 double avg_movie_rating = Double.parseDouble(line_elements[1]);
    	 String movie_id = line_elements[0];
    	 String main_avgrat_mid = formatNumber(5, avg_movie_rating) + movie_id; 
    	 
    	// Add this record to our map with the avg_movie_rating + movie_id as the key
    	 ratingMap.put(new Text(main_avgrat_mid), new Text(value));
    	 
    	// If we have more than ten records, remove the one with the lowest avg_movie_rating + movie_id
    	// As this tree map is sorted in the ascending order, the key with the lowest avg_movie_rating is the first key.
    	if (ratingMap.size() > 10) 
    	{
    		ratingMap.remove(ratingMap.firstKey());
    	}
     }
     
     
     protected void cleanup(Context context) throws IOException, InterruptedException 
     {
     // Output the ten records to the reducers with a null key
     for (Text t : ratingMap.values()) 
     	{
    	 context.write(NullWritable.get(), t);
     	}
     }
 } 

 public static class Reduce_Top10 extends Reducer<NullWritable, Text, NullWritable, Text> 
 {	
	 private TreeMap<Text, Text>  ratingMap = new TreeMap<Text, Text>();
	 private NavigableMap<Text, Text> rMap;
	 
	 public void reduce(NullWritable key, Iterable<Text> avg_ratingPlusId, Context context) throws IOException, InterruptedException 
	 {
		 for (Text value : avg_ratingPlusId) 
		 {
			 String line = value.toString();
	    	 String[] line_elements = line.toString().split("\t");
	    	 
	    	 double avg_movie_rating = Double.parseDouble(line_elements[1]);
	    	 String movie_id = line_elements[0];
	    	 String main_avgrat_mid = A1Part1.formatNumber(5, avg_movie_rating) + movie_id; 
	    	 
	    	 
	    	 ratingMap.put(new Text(main_avgrat_mid), new Text(value));
	     
			 if (ratingMap.size() > 10) 
		    	{
		    		ratingMap.remove(ratingMap.firstKey());
		    	}
		 }
		 rMap = ratingMap.descendingMap();
	     for (Text t : rMap.values()) 
	     {
			 // Output our ten records to the file system with a null key
				 context.write(NullWritable.get(), t);
		 }

     }
	 
 }
 
 public static String formatNumber(int decimals, double number) 
 {
	    StringBuilder sb = new StringBuilder(decimals + 2);
	    sb.append("#.");
	    for(int i = 0; i < decimals; i++) {
	        sb.append("0");
	    }
	    return new DecimalFormat(sb.toString()).format(number);
 }
 
	 public static void main(String[] args) throws Exception 
	 {
		 // ***** Job 1 Start *****
		 
		Configuration conf_job1 = new Configuration();        
	    Job job1 = new Job(conf_job1, "A1P1_MID_AvgR");
	    
	    job1.setOutputKeyClass(Text.class);
	    job1.setOutputValueClass(DoubleWritable.class);
	    job1.setJarByClass(A1Part1.class);

	    job1.setMapperClass(Map.class);
	   
	    job1.setReducerClass(Reduce.class);
	        
	    job1.setInputFormatClass(TextInputFormat.class);
	    job1.setOutputFormatClass(TextOutputFormat.class);
	        
	    FileInputFormat.addInputPath(job1, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job1, new Path(args[1]));

	    job1.waitForCompletion(true);

	    
	    // ***** Job 2 Start *****
	    
	    if(job1.isSuccessful())
	    {
		    Configuration conf_job2 = new Configuration();        
		    Job job2 = new Job(conf_job2, "A1P1_Top_10");
		    
		    job2.setOutputKeyClass(NullWritable.class);
		    job2.setOutputValueClass(Text.class);
		    job2.setJarByClass(A1Part1.class);
	
		    job2.setMapperClass(Map_Top10.class);
		   
		    job2.setReducerClass(Reduce_Top10.class);
		        
		    job2.setInputFormatClass(TextInputFormat.class);
		    job2.setOutputFormatClass(TextOutputFormat.class);
		        
		    FileInputFormat.addInputPath(job2, new Path(args[1]));
		    FileOutputFormat.setOutputPath(job2, new Path(args[2]));
		
		    job2.waitForCompletion(true);
	    }
	 }
	        
}
