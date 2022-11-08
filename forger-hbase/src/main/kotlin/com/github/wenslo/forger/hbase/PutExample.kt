package com.github.wenslo.forger.hbase

import org.apache.hadoop.hbase.*
import org.apache.hadoop.hbase.client.*
import org.apache.hadoop.hbase.util.Bytes
import java.nio.charset.Charset


/**
 * @author wenhailin
 * @date 2022/11/8 17:53
 */
class PutExample {
    companion object {
        val row1: ByteArray = Bytes.toBytes("row1")
        const val tableName = "testtable"
        const val qualifier1 = "qualifier1"
    }

    fun example() {
        val config = HBaseConfiguration.create()
        val path = this.javaClass.classLoader
            .getResource("hbase-site.xml")?.path
        config.addResource(path)

        HBaseAdmin.available(config)

        val table = TableName.valueOf("testtable")

        val family1 = "Family1"
        val family2 = "Family2"

        val connection = ConnectionFactory.createConnection(config)
        createTable(connection, table, family1, family2)
        put(connection, family1)
        get(connection, family1)

    }

    private fun get(connection: Connection, family1: String) {
        val table1 = connection.getTable(TableName.valueOf(tableName))
        val g = Get(row1)
        val r = table1.get(g)
        val value: ByteArray = r.getValue(family1.toByteArray(), Bytes.toBytes(qualifier1))
        println("value is ${String(value, Charset.defaultCharset())}")
    }

    private fun createTable(
        connection: Connection,
        table: TableName?,
        family1: String,
        family2: String
    ): Connection? {

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

    private fun put(connection: Connection, family1: String) {
        val table1 = connection.getTable(TableName.valueOf(tableName))

        val p = Put(row1)
        p.add(
            createCell(
                row1,
                Bytes.toBytes(family1),
                Bytes.toBytes(qualifier1),
                Bytes.toBytes("cell_value")
            )
        )
        table1.put(p)
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