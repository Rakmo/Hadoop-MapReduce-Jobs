/* Q3. Given some genre, find all the movies belong to that genre
*/
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
        
public class A1Part3 
{       
 public static class Map extends Mapper<LongWritable, Text, NullWritable, Text> 
 {
	 
     public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
     {
    	 Configuration conf = context.getConfiguration();
    	 int flag_count = 0;
    	 int ig_length = Integer.parseInt(conf.get("igLen"));
         String input_genres[] = new String[ig_length];
    	 for(int i=0; i<ig_length; i++)
    	 {
    		 input_genres[i] = conf.get(i+"");
    	 }
    	 String line = value.toString();
    	 String[] line_elements = line.split("::");
    	 String movie_name = line_elements[1];
    	 String genre_set[] = line_elements[2].split("\\|");
    	 
    	 for(int i = 0; i<ig_length; i++)
    	 {
    		 for(int j = 0; j<genre_set.length; j++)
    		 {
    			 if(input_genres[i].equalsIgnoreCase(genre_set[j]))
    			 {
    				 flag_count = flag_count + 1;
    			 }
    		 }
    	 }
    	 
    	 if(flag_count == ig_length)
    	 {
    		 context.write(NullWritable.get(), new Text(movie_name));
    	 }	 
     }
 } 

 public static class Reduce extends Reducer<NullWritable, Text, NullWritable, Text> 
 {	
	 public void reduce(NullWritable key, Iterable<Text> movie_names, Context context) throws IOException, InterruptedException 
	 {
		 for (Text t : movie_names) 
	     {
				 context.write(NullWritable.get(), t);
		 }
    }
 }
 
	 public static void main(String[] args) throws Exception 
	 { 
		Configuration conf_job = new Configuration();
		
		int ig_length = args.length - 2;
		conf_job.set("igLen", ""+ig_length);
		for(int i=0; i<ig_length; i++)
		{
			conf_job.set(i+"", args[i+2]);
		}
		
		Job job = new Job(conf_job, "A1P3_MovieNames");
	    
	    job.setOutputKeyClass(NullWritable.class);
	    job.setOutputValueClass(Text.class);
	    job.setJarByClass(A1Part3.class);

	    job.setMapperClass(Map.class);
	   
	    job.setReducerClass(Reduce.class);
	        
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);
	        
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    
	    job.waitForCompletion(true);
	   
	 }       
}
