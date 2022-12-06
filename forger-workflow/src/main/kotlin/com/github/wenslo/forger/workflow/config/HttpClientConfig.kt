package com.github.wenslo.forger.workflow.config

import com.github.wenslo.forger.core.inline.getLogger
import org.apache.hc.client5.http.ConnectionKeepAliveStrategy
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.apache.hc.client5.http.socket.ConnectionSocketFactory
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy
import org.apache.hc.core5.http.HeaderElement
import org.apache.hc.core5.http.HttpHeaders
import org.apache.hc.core5.http.HttpResponse
import org.apache.hc.core5.http.config.RegistryBuilder
import org.apache.hc.core5.http.io.SocketConfig
import org.apache.hc.core5.http.message.BasicHeaderElementIterator
import org.apache.hc.core5.http.protocol.HttpContext
import org.apache.hc.core5.ssl.SSLContextBuilder
import org.apache.hc.core5.util.TimeValue
import org.apache.hc.core5.util.Timeout
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit


/**
 * @author wenhailin
 * @date 2022/12/6 23:14
 */
@Configuration
class HttpClientConfig {

    companion object {
        val logger = getLogger<HttpClientConfig>()

        // Determines the timeout in milliseconds until a connection is established.
        private const val CONNECT_TIMEOUT: Long = 30000

        // The timeout when requesting a connection from the connection manager.
        private const val REQUEST_TIMEOUT: Long = 30000

        // The timeout for waiting for data
        private const val SOCKET_TIMEOUT = 60000

        private const val MAX_TOTAL_CONNECTIONS = 50
        private const val DEFAULT_KEEP_ALIVE_TIME_MILLIS: Long = 20 * 1000
        private const val CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS = 30
    }


    @Bean
    fun poolingConnectionManager(): PoolingHttpClientConnectionManager? {
        val builder = SSLContextBuilder()
        try {
            builder.loadTrustMaterial(null, TrustSelfSignedStrategy())
        } catch (e: NoSuchAlgorithmException) {
            logger.error("Pooling Connection Manager Initialisation failure because of " + e.message, e)
        } catch (e: KeyStoreException) {
            logger.error("Pooling Connection Manager Initialisation failure because of " + e.message, e)
        }
        var sslsf: SSLConnectionSocketFactory? = null
        try {
            sslsf = SSLConnectionSocketFactory(builder.build())
        } catch (e: KeyManagementException) {
            logger.error("Pooling Connection Manager Initialisation failure because of " + e.message, e)
        } catch (e: NoSuchAlgorithmException) {
            logger.error("Pooling Connection Manager Initialisation failure because of " + e.message, e)
        }
        val socketFactoryRegistry = RegistryBuilder.create<ConnectionSocketFactory>()
            .register("https", sslsf)
            .register("http", PlainConnectionSocketFactory())
            .build()
        val poolingConnectionManager = PoolingHttpClientConnectionManager(socketFactoryRegistry)
        poolingConnectionManager.maxTotal = MAX_TOTAL_CONNECTIONS
        poolingConnectionManager.defaultSocketConfig = SocketConfig.custom()
            .setSoTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
            .setSoReuseAddress(true)
            .build()
        return poolingConnectionManager
    }

    @Bean
    fun connectionKeepAliveStrategy(): ConnectionKeepAliveStrategy? {
        return object : ConnectionKeepAliveStrategy {
            override fun getKeepAliveDuration(response: HttpResponse, context: HttpContext?): TimeValue? {
                val it =
                    BasicHeaderElementIterator(response.headerIterator(HttpHeaders.KEEP_ALIVE))
                while (it.hasNext()) {
                    val he: HeaderElement? = it.next()
                    val param = he?.name ?: ""
                    val value = he?.value ?: ""
                    if (param.equals("timeout", ignoreCase = true)) {
                        return TimeValue.ofMilliseconds(value.toLong() * 1000)
                    }
                }
                return TimeValue.ofMilliseconds(DEFAULT_KEEP_ALIVE_TIME_MILLIS)
            }
        }
    }

    @Bean
    fun httpClient(): CloseableHttpClient? {
        val requestConfig: RequestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(REQUEST_TIMEOUT))
            .setConnectTimeout(Timeout.ofMilliseconds(CONNECT_TIMEOUT))
            .build()
        return HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(poolingConnectionManager())
            .setKeepAliveStrategy(connectionKeepAliveStrategy())
            .build()
    }

    @Bean
    fun idleConnectionMonitor(connectionManager: PoolingHttpClientConnectionManager?): Runnable? {
        return Runnable {
            try {
                if (connectionManager != null) {
                    logger.trace("run IdleConnectionMonitor - Closing expired and idle connections...")
                    connectionManager.closeExpired()
                    connectionManager.closeIdle(TimeValue.ofSeconds(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS.toLong()))
                } else {
                    logger.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised")
                }
            } catch (e: Exception) {
                logger.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.message, e)
            }
        }
    }
}