package catagry_mr;

//top 5 categories of videos in youtube


import java.io.IOException;
import java.util.Scanner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

   public class App extends Configured implements Tool{
    static int flag=0;
     
     public static class Map3 extends Mapper<LongWritable, Text, Text,
     IntWritable> {

            int count =0;
            private Text length_cnt = new Text();
            private IntWritable bal = new IntWritable();
            public void map(LongWritable key, Text value, Context context )
     throws IOException, InterruptedException {
  
         	   Configuration conf = context.getConfiguration();

                String line = value.toString();
                String str[]=line.split("\t");
                
               if(str.length > 5){
		            	   bal.set(Integer.parseInt(str[5]));
		             	   length_cnt.set(str[3]);
	                 	    //System.out.println(str[0]);
	                 	    //count++;
	                 	    //System.out.println("count = " + count);
	                 	    
	                        context.write(length_cnt, bal);
               }

           }

         }

     public static class Reduce3 extends Reducer<Text, IntWritable,
     Text, IntWritable> {
            
            public void reduce(Text key, Iterable<IntWritable> values,
     Context context)
              throws IOException, InterruptedException {
                int sum = 0;
                int count2=0;
                for (IntWritable val : values) 
                {

                    sum += val.get();
                    count2++;
                    //System.out.println("COUNT2 = " + count2);
                }
                   int average=sum/count2;
                   System.out.println( "Key="+ key + ", " +  "Sum=  " +  sum +", " + "Frequencies = " + count2 +", " + "average=  " +  average);
                   System.out.println();
                   context.write(key, new IntWritable(average));
                
            }
         }
	
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Mean Number of View Count By Video Category ");

	    
		Configuration conf = new Configuration();
		
        Job job = new Job(conf);
		job.setJarByClass(App.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(Map3.class);
		job.setReducerClass(Reduce3.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		Path out=new Path(args[1]);
		out.getFileSystem(conf).delete(out);
        job.waitForCompletion(true);

	    return (job.waitForCompletion(true) ? 0 : 1);
	}
	
	 public static void main(String[] args) throws Exception {
	    	
	    	int ecode = ToolRunner.run( new App(), args);
	    	System.exit(ecode);
	    }
}
