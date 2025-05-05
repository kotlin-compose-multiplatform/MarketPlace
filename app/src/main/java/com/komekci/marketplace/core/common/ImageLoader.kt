package com.komekci.marketplace.core.common

import android.content.Context
import coil.ImageLoader
import okhttp3.Call
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun getUnsafeClient(): OkHttpClient {
    val trustAllCerts: Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    )
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())
    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    okHttpClientBuilder.hostnameVerifier { _, _ -> true }
    return okHttpClientBuilder.build()
}

fun imageLoader(context: Context): ImageLoader {
    val insecureOkHttpClient = getUnsafeClient()

    // Create a Call.Factory using the insecure client
    val callFactory = OkHttpClientCallFactory(insecureOkHttpClient)

    // Create an ImageLoader using the insecure client
    return ImageLoader.Builder(context)
        .callFactory(callFactory)
        .build()
}

class OkHttpClientCallFactory(private val client: OkHttpClient) : Call.Factory {
    override fun newCall(request: okhttp3.Request): Call {
        return client.newCall(request)
    }
}