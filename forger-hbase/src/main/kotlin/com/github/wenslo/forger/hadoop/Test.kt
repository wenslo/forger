package com.github.wenslo.forger.hadoop

import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapred.FileInputFormat
import org.apache.hadoop.mapred.FileOutputFormat
import org.apache.hadoop.mapreduce.Job


/**
 * @author wenhailin
 * @date 2022/11/14 17:22
 */
class Test {
    fun run(vararg args: String): Int {
        val job = Job(null)
        job.setJarByClass(Test::class.java)
        job.jobName = "Score_Process"

        job.outputKeyClass = Text::class.java
        job.outputValueClass = IntWritable::class.java

//        job.mapperClass = MyMap::class.java
//        job.combinerClass = MyReduce::class.java
//        job.reducerClass = MyReduce::class.java

//        job.inputFormatClass = TextInputFormat::class.java
//        job.outputFormatClass = TextInputFormat::class.java

        FileInputFormat.setInputPaths(null, Path(args[0]))
        FileOutputFormat.setOutputPath(null, Path(args[1]))
        return if (job.waitForCompletion(true)) 0 else 1
    }
}