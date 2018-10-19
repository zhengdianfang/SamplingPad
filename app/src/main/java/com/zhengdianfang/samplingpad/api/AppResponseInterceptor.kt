package com.zhengdianfang.samplingpad.api

import com.google.gson.Gson
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response

import okhttp3.ResponseBody

class AppResponseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", App.INSTANCE.token)
            .build()
        var response = chain.proceed(newRequest)
        if (response.isSuccessful && response.header("Content-Type")!!.contains("application/json")) {
            val responseText = response.body()?.string()
            if (responseText.isNullOrEmpty().not()) {
                val gson = Gson()
                val responseBean = gson.fromJson<com.zhengdianfang.samplingpad.api.Response<Any>>(
                    responseText, com.zhengdianfang.samplingpad.api.Response::class.java)
                if (responseBean.code == 403) {
                   App.INSTANCE.logout()
                }
                response = response
                    .newBuilder()
                    .code(responseBean.code)
                    .message(responseBean.msg)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseText))
                    .build()
            }
        }
        return response
    }
}