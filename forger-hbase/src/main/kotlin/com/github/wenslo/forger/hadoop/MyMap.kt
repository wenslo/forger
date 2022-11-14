package com.github.wenslo.forger.hadoop

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapred.Mapper
import org.apache.hadoop.mapred.OutputCollector
import org.apache.hadoop.mapred.Reporter
import java.util.*

/**
 * @author wenhailin
 * @date 2022/11/14 16:50
 */
class MyMap : Mapper<LongWritable, Text, Text, IntWritable> {
    override fun configure(job: JobConf?) {
    }

    override fun close() {
    }

    override fun map(
        key: LongWritable?,
        value: Text?,
        output: OutputCollector<Text, IntWritable>?,
        reporter: Reporter?
    ) {
        val line = value.toString()
        println(line)
        val tokenizerArticle = StringTokenizer(line, "\n")
        while (tokenizerArticle.hasMoreTokens()) {
            val tokenizerLine = StringTokenizer(tokenizerArticle.nextToken())
            val strName = tokenizerLine.nextToken()
            val strScore = tokenizerLine.nextToken()
            val name = Text(strName)
            output?.collect(name, IntWritable(strScore.toInt()))
        }
    }
}