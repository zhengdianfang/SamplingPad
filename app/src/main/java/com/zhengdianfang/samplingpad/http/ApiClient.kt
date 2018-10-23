package com.zhengdianfang.samplingpad.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.CookieJar
import timber.log.Timber


object ApiClient {

    const val HOST = "http://39.104.56.18:8080/inspection/"

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(LoggingInterceptor())
            .addNetworkInterceptor(AppResponseInterceptor())
            .cookieJar(AppCookieJar())
            .build()
    }

    val INSTANCE: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    class AppCookieJar : CookieJar {

        private var cookies: List<Cookie>? = null

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            this.cookies = cookies
            Timber.d("save cookie: ${this.cookies.toString()}")
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            Timber.d("load cookie: ${this.cookies.toString()}")
            return if (cookies != null) cookies!! else ArrayList()

        }

        fun clearCookies() {
            cookies = null
        }
    }
}