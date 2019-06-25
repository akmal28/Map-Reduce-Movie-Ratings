package FinalProject;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MovieRatings {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // Check that the input and output path have been provided
        if (args.length != 2) {
            System.err.println("Syntax: FinalProject.MovieRatings <input path> <output path>");
            System.exit(-1);
        }
        Configuration conf = new Configuration();

        // Create an instance of the MapReduce job
        Job job = Job.getInstance(conf, "Movie Ratings");
        job.setJarByClass(MovieRatings.class);
        job.setJobName("Average movie rating");

        // Set input and output locations
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Set mapper and reducer classes
        job.setMapperClass(MovieRatingMapper.class);
        job.setReducerClass(MovieRatingReducer.class);

        // Set output key/value
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(DoubleWritable.class);

        // Run the job and then exit the program
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
