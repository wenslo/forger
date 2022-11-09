package com.github.wenslo.forger.hbase

import org.apache.hadoop.hbase.*
import org.apache.hadoop.hbase.client.*
import org.apache.hadoop.hbase.filter.BinaryComparator
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.hadoop.hbase.filter.PrefixFilter
import org.apache.hadoop.hbase.filter.QualifierFilter
import org.apache.hadoop.hbase.util.Bytes


/**
 * @author wenhailin
 * @date 2022/11/8 17:53
 */
class Example {
    companion object {
        val row1: ByteArray = Bytes.toBytes("row1")
        const val tableName = "testtable"
        const val qualifier1 = "qualifier1"
        const val family1 = "Family1"
        const val family2 = "Family2"

    }

    fun example() {
        val config = HBaseConfiguration.create()
        val path = this.javaClass.classLoader
            .getResource("hbase-site.xml")?.path
        config.addResource(path)

        HBaseAdmin.available(config)

        val table = TableName.valueOf("testtable")

        val connection = ConnectionFactory.createConnection(config)
        createTable(connection, table)
        put(connection)
        get(connection)
        scan(connection)
        scan2(connection)
        delete(connection)
        batchPut(connection)
        tableInfo(connection)
    }

    private fun tableInfo(connection: Connection) {
        val table = connection.getTable(TableName.valueOf(tableName))
        // invoke flushCache
        table.close()
        println("table configuration is ${table.configuration}")
        println("table name is ${table.name}")
        println("table descriptor is ${table.descriptor}")
    }

    private fun batchPut(connection: Connection) {
        val table = connection.getTable(TableName.valueOf(tableName))
        val p = Put(row1)
        val p1 = Put(row1)
        val p2 = Put(row1)
        p.add(
            createCell(
                row1,
                Bytes.toBytes(family1),
                Bytes.toBytes(qualifier1),
                Bytes.toBytes("cell_value1")
            )
        )
        p1.add(
            createCell(
                row1,
                Bytes.toBytes(family1),
                Bytes.toBytes(qualifier1),
                Bytes.toBytes("cell_value3")
            )
        )
        p2.add(
            createCell(
                row1,
                Bytes.toBytes(family1),
                Bytes.toBytes(qualifier1),
                Bytes.toBytes("cell_value3")
            )
        )
        val puts = listOf(p, p1, p2)
        table.put(puts)
    }

    private fun delete(connection: Connection) {
        val table = connection.getTable(TableName.valueOf(tableName))
        val delete = Delete(row1)
        delete.addColumn(family1.toByteArray(), qualifier1.toByteArray())
        table.delete(delete)
    }

    private fun scan2(connection: Connection) {
        val table = connection.getTable(TableName.valueOf(tableName))
        val filter1 = PrefixFilter(row1)
        val filter2 = QualifierFilter(CompareOperator.GREATER_OR_EQUAL, BinaryComparator(qualifier1.toByteArray()))
        val filters = listOf(filter1, filter2)
        val scan = Scan()
        scan.filter = FilterList(FilterList.Operator.MUST_PASS_ALL, filters)
        table.getScanner(scan).use {
            for (result in it) {
                println("Found row: $result")
            }
        }
    }

    private fun scan(connection: Connection) {
        val table = connection.getTable(TableName.valueOf(tableName))
        val scan = Scan()
        scan.addColumn(family1.toByteArray(), qualifier1.toByteArray())
        val scanner = table.getScanner(scan)
        for (result in scanner) {
            println("Found row: $result")
        }
    }

    private fun get(connection: Connection) {
        val table = connection.getTable(TableName.valueOf(tableName))
        val g = Get(row1)
        //check data is exists
        val exists = table.exists(g)
        val r = table.get(g)
        val value: ByteArray = r.getValue(family1.toByteArray(), Bytes.toBytes(qualifier1))
        println("value is ${Bytes.toString(value)}")
    }

    private fun createTable(
        connection: Connection,
        table: TableName?
    ): Connection {

        val admin = connection.admin

        val tableDescriptor = TableDescriptorBuilder.newBuilder(table)
            .setColumnFamilies(
                listOf(
                    ColumnFamilyDescriptorBuilder.of(family1),
                    ColumnFamilyDescriptorBuilder.of(family2)
                )
            ).build()
        admin.createTable(tableDescriptor)
        return connection
    }

    private fun put(connection: Connection) {
        val table = connection.getTable(TableName.valueOf(tableName))

        val p = Put(row1)
        p.add(
            createCell(
                row1,
                Bytes.toBytes(family1),
                Bytes.toBytes(qualifier1),
                Bytes.toBytes("cell_value")
            )
        )
        table.put(p)
    }

    private fun createCell(
        row: ByteArray,
        family: ByteArray,
        qualifier: ByteArray,
        value: ByteArray,
        ts: Long = System.currentTimeMillis(),
    ): Cell {
        return CellBuilderFactory
            .create(CellBuilderType.SHALLOW_COPY)
            .setRow(row)
            .setFamily(family)
            .setQualifier(qualifier)
            .setTimestamp(ts)
            .setValue(value)
            .build()

    }
}