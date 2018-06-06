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
     static int int_range;
     static int fin_range;
     
     public static class Map2 extends Mapper<LongWritable, Text, Text,
     IntWritable> {

            int count =0;
            private Text length_cnt = new Text();
            private final static IntWritable bal = new IntWritable(1);
            public void map(LongWritable key, Text value, Context context )
     throws IOException, InterruptedException {
  
         	   Configuration conf = context.getConfiguration();
         	  String scan3 = conf.get("third");
         	   String scan1 = conf.get("first");
         	   String scan2 = conf.get("second");
         	   
         	   int_range = Integer.parseInt(scan1);
        	       fin_range = Integer.parseInt(scan2);
                String line = value.toString();
                String str[]=line.split("\t");
                
               if(str.length > 5){
            	    
             	    int temp = Integer.parseInt(str[4]);
             	    //System.out.println("temp = " + temp);
             	    //System.out.println("scan3 = "+ scan3+ " str[3] " +str[3] + " value = " + scan3.equals(str[3]));
             	    if(scan3.equals(str[3]))
             	    {
	             	    if(temp>= int_range && temp <= fin_range )
	             	    {
	             	    	String str_freq1 = Integer.toString(int_range);
	                 	    String str_freq2 = Integer.toString(fin_range);
	                 	    System.out.println(str[0]);
	                 	    length_cnt.set("frequencies of Length from "+ str_freq1 + " KB to " + str_freq2 + " KB for  " + scan3 +" is = ");
	                 	    count++;
	                        //System.out.println("temp_2=" +temp + "      COUNT1 = "+count);
	                        context.write(length_cnt, bal);
	             	    }
             	    } 
               }

           
           //System.out.println("Category = " + length_cnt + " "+ "Bal = " + bal);
           //count++;
           //System.out.println("COUNT1 = "+count);
           }

         }

     public static class Reduce2 extends Reducer<Text, IntWritable,
     Text, IntWritable> {
            int count2=0;
            public void reduce(Text key, Iterable<IntWritable> values,
     Context context)
              throws IOException, InterruptedException {
                int sum = 0;
                for (IntWritable val : values) 
                {

                    sum += val.get();
                }
                   count2++;
                   System.out.println("COUNT2 = " + count2);
             	   System.out.println( key +  "  " +  sum);
                
                context.write(key, new IntWritable(sum));
                
            }
         }
     
	
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Which category Do you like to select \n 1) Entertainment \n 2) Music \n 3) Comedy \n 4) People & Blogs \n 5) Travel & Places \n 6) News & Politics \n 7) Sports \n 8) Film & Animation \n ");//Foe Category Search
		Scanner scan3 = new Scanner(System.in); //for category specific search
		Scanner scan1 = new Scanner(System.in);
	    Scanner scan2 = new Scanner(System.in);
	    
	    String str3=scan3.nextLine(); //for category specific search
	    String str1=scan1.nextLine();
	    String str2=scan2.nextLine();
	    
		Configuration conf = new Configuration();
		conf.set("third", str3); //for category specific search
		conf.set("first", str1);
		conf.set("second", str2);
		
        Job job = new Job(conf);
		job.setJarByClass(App.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//job.setMapperClass(Map1.class);
		//job.setReducerClass(Reduce1.class);
		job.setMapperClass(Map2.class);
		job.setReducerClass(Reduce2.class);
		
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
