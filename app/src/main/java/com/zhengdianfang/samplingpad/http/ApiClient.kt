package com.zhengdianfang.samplingpad.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.CookieJar
import timber.log.Timber


object ApiClient {

    //http://118.190.137.152/inspection/
    //http://39.104.56.18:8080/inspection/
    //http://103.113.159.106/inspection/
    const val HOST = "http://118.190.137.152:8080/inspection/"
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    fun getHttpClient(): OkHttpClient {
        if (this. okHttpClient == null) {
            this.okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(LoggingInterceptor())
                .addNetworkInterceptor(AppResponseInterceptor())
                .cookieJar(AppCookieJar())
                .build()
        }
        return okHttpClient!!
    }

    fun getRetrofit() : Retrofit {
        if (retrofit == null) {
            this.retrofit = Retrofit.Builder()
                .baseUrl(HOST)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun reset() {
        this.okHttpClient = null
        this.retrofit = null
    }

    class AppCookieJar : CookieJar {

        private val cookieStore = hashMapOf<String, List<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host()] = cookies
            Timber.d("save cookie: ${this.cookieStore}")
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            Timber.d("load cookie: ${this.cookieStore[url.host()].toString()}")
            return cookieStore[url.host()] ?: listOf()

        }

        fun clearCookies() {
            cookieStore.clear()
        }
    }
}