package com.zhengdianfang.samplingpad.common.api

import com.zhengdianfang.samplingpad.api.Response
import com.zhengdianfang.samplingpad.common.entities.SpinnerItem
import retrofit2.Call
import retrofit2.http.GET

interface SpinnerDataApi {


    @GET("app/samplepackagelis")
    fun getPackageFunctionList(): Call<Response<MutableList<SpinnerItem>>>
}