package com.github.wenslo.forger.security.filter

import java.io.*
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper


class RequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    //Use this method to read the request body N times
    private val body: String

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        val byteArrayInputStream = ByteArrayInputStream(body.toByteArray())
        return object : ServletInputStream() {
            override fun isReady(): Boolean {
                return isReady
            }

            override fun isFinished(): Boolean {
                return isFinished
            }

            @Throws(IOException::class)
            override fun read(): Int {
                return byteArrayInputStream.read()
            }

            override fun setReadListener(listener: ReadListener?) {
                return
            }
        }
    }

    @Throws(IOException::class)
    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(this.inputStream))
    }

    init { //So that other request method behave just like before
        val stringBuilder = StringBuilder()
        var bufferedReader: BufferedReader? = null
        try {
            val inputStream: InputStream? = request.inputStream
            if (inputStream != null) {
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val charBuffer = CharArray(128)
                var bytesRead = -1
                while (bufferedReader.read(charBuffer).also { bytesRead = it } > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead)
                }
            } else {
                stringBuilder.append("")
            }
        } catch (ex: IOException) {
            throw ex
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (ex: IOException) {
                    throw ex
                }
            }
        }
        //Store request pody content in 'body' variable
        body = stringBuilder.toString()
    }
}