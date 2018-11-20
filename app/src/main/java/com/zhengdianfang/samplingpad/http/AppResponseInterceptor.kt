package com.zhengdianfang.samplingpad.http

import com.google.gson.Gson
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.user.api.UserApi
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class AppResponseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", App.INSTANCE.user?.token ?: "")
            .build()
        var response = chain.proceed(newRequest)
        if (!request.url().toString().contains("logout") &&
            response.isSuccessful &&
            response.header("Content-Type")?.contains("application/json") == true) {
            val responseText = response.body()?.string()
            if (response.code() != 302 && responseText.isNullOrEmpty().not()) {
                val gson = Gson()
                val responseBean = gson.fromJson<com.zhengdianfang.samplingpad.http.Response<Any>>(
                    responseText, com.zhengdianfang.samplingpad.http.Response::class.java)
                if (responseBean.code == 403) {
                    App.INSTANCE.logout()
                }
                response = response
                    .newBuilder()
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseText!!))
                    .build()
            }
        }
        return response
    }
}