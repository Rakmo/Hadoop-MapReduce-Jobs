/* Q2. List each genre of movie and count the movies of each genre.
*/
import java.io.IOException;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
        
public class A1Part2 
{       
	
 public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> 
 {
	 private final static IntWritable oneForCounting = new IntWritable(1);
	 
     public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
     {
    	 String line = value.toString();
    	 String[] line_elements = line.toString().split("::");
    	 String genre_set[] = line_elements[2].split("\\|");
    	 for(int i = 0; i<genre_set.length; i++)
    	 {
    		 Text genre_name = new Text(genre_set[i]);  
    		 context.write(genre_name, oneForCounting);
    	 }   
     }
 } 

 public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> 
 {	
	 public void reduce(Text genre_name, Iterable<IntWritable> genre_values, Context context) throws IOException, InterruptedException 
	 {
		 int sum = 0;
	     for (IntWritable i : genre_values) 
	     {
	    	 sum += i.get();
	     }
	     if(genre_name.getLength()<=7)
	    	 context.write(new Text(genre_name+"\t"), new IntWritable(sum));
	     else
	    	 context.write(genre_name, new IntWritable(sum));
    }
 }
 
	 public static void main(String[] args) throws Exception 
	 {
		
		Configuration conf_job = new Configuration();        
	    Job job = new Job(conf_job, "A1P2_Genre_nItsCount");
	    
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    job.setJarByClass(A1Part2.class);

	    job.setMapperClass(Map.class);
	   
	    job.setReducerClass(Reduce.class);
	        
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);
	        
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.waitForCompletion(true);

	    
	 }
	        
}
