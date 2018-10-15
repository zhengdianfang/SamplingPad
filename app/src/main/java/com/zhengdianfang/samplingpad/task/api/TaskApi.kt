package com.zhengdianfang.samplingpad.task.api

import com.zhengdianfang.samplingpad.api.Response
import com.zhengdianfang.samplingpad.task.entities.StatusCount
import retrofit2.Call
import retrofit2.http.GET

interface TaskApi {

    @GET("app/listtaskscount")
    fun fetchStatusCount(): Call<Response<StatusCount>>
}