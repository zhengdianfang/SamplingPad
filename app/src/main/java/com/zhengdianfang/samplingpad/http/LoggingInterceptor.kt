package com.zhengdianfang.samplingpad.http

import okhttp3.*
import okhttp3.Response
import timber.log.Timber

class LoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.body() is FormBody) {
            val body = request.body() as FormBody
            val sb = StringBuffer()
            for(index in 0 until body.size()) {
                val name = body.encodedName(index)
                val value = body.encodedValue(index)
                sb.append("&").append(name).append("=").append(value)
            }
            Timber.tag("LoggingInterceptor")
                .d("${request.url()}, body: $sb")
        } else {
            Timber.tag("LoggingInterceptor").d("${request.url()}")
        }

        val response = chain.proceed(request)
        if (response.header("Content-Type")!!.contains("application/json")) {
            val responseText = response.body()?.string()
            Timber.tag("LoggingInterceptor")
                .d("${request.url().url().path}, response: $responseText")

            return response.newBuilder().body(ResponseBody.create(MediaType.parse("application/json"), responseText ?: "")).build()
        }
        return response
    }
}