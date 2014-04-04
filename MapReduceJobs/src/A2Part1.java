import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class A2Part1 extends Configured implements Tool
{
 	
	public static class JoinMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> 
	{
		HashMap<String, String> moviesData;
		
		@Override
		public void configure(JobConf job) 
		{
			try 
			{
				Path[] path = DistributedCache.getLocalCacheFiles(job);
				//System.out.println("******* length of path: "+path.length);
				BufferedReader bufferedReader = new BufferedReader(new FileReader(path[0].toString()));
				moviesData = new HashMap<String, String>();
				String record;
				while ((record = bufferedReader.readLine()) != null) 
				{
					String line_elements[] = record.split("::");
					if (line_elements.length == 3) 
					{
						String movieId = line_elements[0];
						String movieGenres = line_elements[2];
						if(movieGenres.indexOf("Action")>=0)
						{
							moviesData.put(movieId, line_elements[1]+ "\t" + line_elements[2]);
						}
					}
				}
				bufferedReader.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		@Override
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException
		{
			String line = value.toString();
			String line_elements[] = line.split("::");
			
			if (line_elements.length == 4) 
			{
				String movieId = line_elements[1];
				//String value2 = "";
				
				if (moviesData.containsKey(movieId)) 
				{
					String valueText = moviesData.get(movieId) + "\t" + line_elements[2] + "\t" + line_elements[3];
					output.collect(new Text(movieId), new Text(valueText));
				}
			}
			else
			{
				System.out.println("Erroneous Data !!");
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception 
	{
		JobConf conf = new JobConf(getConf(), A2Part1.class);
		conf.setJobName("InMemoryMapSideJoin");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(JoinMapper.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		String moviesFile = args[0] + "/movies";
		String ratingsFile = args[0] + "/ratings";
		
		DistributedCache.addCacheFile(new Path(moviesFile).toUri(), conf);
		FileInputFormat.setInputPaths(conf,new Path(ratingsFile));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		if(JobClient.runJob(conf).isSuccessful())
		{
			A1Part1 a1p1 = new A1Part1();
			a1p1.main2(args[1], args[2], args[3]);
		}
		return 0;
	}
	
 	public static void main(String[] args) throws Exception 
 	{
		Configuration conf = new Configuration();
		A2Part1 instanceA2Part1 = new A2Part1();
 		int exitCode = ToolRunner.run(conf, instanceA2Part1, args);
		System.exit(exitCode);
	}
}