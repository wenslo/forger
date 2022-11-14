package com.github.wenslo.forger.hadoop

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapred.OutputCollector
import org.apache.hadoop.mapred.Reducer
import org.apache.hadoop.mapred.Reporter

/**
 * @author wenhailin
 * @date 2022/11/14 17:05
 */
class MyReduce : Reducer<Text, IntWritable, Text, IntWritable> {
    override fun configure(job: JobConf?) {
    }

    override fun close() {
    }

    override fun reduce(
        key: Text?,
        values: MutableIterator<IntWritable>?,
        output: OutputCollector<Text, IntWritable>?,
        reporter: Reporter?
    ) {
        var sum = 0
        var count = 0
        val iterator = values?.iterator()
        while (iterator?.hasNext() == true) {
            sum += iterator.next().get()
            count++
        }
        val average = sum / count
        output?.collect(key, IntWritable(average))
    }
}