package com.zhengdianfang.samplingpad.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val HOST = "http://39.104.56.18:8080/inspection/"

    val INSTANCE: Retrofit = Retrofit.Builder()
        .baseUrl(HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}