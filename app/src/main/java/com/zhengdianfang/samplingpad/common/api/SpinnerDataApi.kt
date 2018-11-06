package com.zhengdianfang.samplingpad.common.api

import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.common.entities.OptionItem
import retrofit2.Call
import retrofit2.http.GET

interface SpinnerDataApi {


    @GET("app/samplepackagelis")
    fun getPackageFunctionList(): Call<Response<MutableList<OptionItem>>>
}