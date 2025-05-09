package catagry_mr;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

   public class App {
    public static class Map extends Mapper<LongWritable, Text, Text,
    IntWritable> {
           int count =0;
           private Text length_cnt = new Text();
           private final static IntWritable bal = new IntWritable(1);
           public void map(LongWritable key, Text value, Context context )
    throws IOException, InterruptedException {
               String line = value.toString();
               String str[]=line.split("\t");

              if(str.length > 5){
            	    int temp = Integer.parseInt(str[4]);
            	    
            	    int range_freq = temp/100;
            	    String str_freq1 = Integer.toString(range_freq*100);
            	    String str_freq2 = Integer.toString(range_freq*100+100);
            	    length_cnt.set("frequencies of Length from "+ str_freq1 + " to " + str_freq2 + " is = ");
              }

          context.write(length_cnt, bal);
          //System.out.println("Category = " + length_cnt + " "+ "Bal = " + bal);
          count++;
          //System.out.println("COUNT1 = "+count);
          }

        }
   
    public static class Reduce extends Reducer<Text, IntWritable,
Text, IntWritable> {
       int count2=0;
       public void reduce(Text key, Iterable<IntWritable> values,
Context context)
         throws IOException, InterruptedException {
           int sum = 0;
           for (IntWritable val : values) {

               sum += val.get();
           }
           //if(sum>=20)
           {
        	   //System.out.println("Key = " + key +  "  " + "Value = "+ sum);
        	   System.out.println(key +  "  " +  sum);
           }
           count2++;
           //System.out.println("COUNT2 = " + count2);
           context.write(key, new IntWritable(sum));
           
       }
    }

    public static void main(String[] args) throws Exception {
       Configuration conf = new Configuration();

           @SuppressWarnings("deprecation")
                Job job = new Job(conf, "categories");
           job.setJarByClass(App.class);

           job.setMapOutputKeyClass(Text.class);
           job.setMapOutputValueClass(IntWritable.class);
      //job.setNumReduceTasks(0);
       job.setOutputKeyClass(Text.class);
       job.setOutputValueClass(IntWritable.class);

       job.setMapperClass(Map.class);
       job.setReducerClass(Reduce.class);

       job.setInputFormatClass(TextInputFormat.class);
       job.setOutputFormatClass(TextOutputFormat.class);

       FileInputFormat.addInputPath(job, new Path(args[0]));
       FileOutputFormat.setOutputPath(job, new Path(args[1]));
        Path out=new Path(args[1]);
        out.getFileSystem(conf).delete(out);
       job.waitForCompletion(true);
    }

}
