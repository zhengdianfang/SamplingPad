package com.zhengdianfang.samplingpad.task.api

import com.zhengdianfang.samplingpad.api.Response
import com.zhengdianfang.samplingpad.task.entities.StatusCount
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskApi {

    @GET("app/listtaskscount")
    fun fetchStatusCount(): Call<Response<StatusCount>>

    @GET("app/listtasks/{status}")
    fun fetchTaskListGroupByStatus(@Path("status") status: Int): Call<Response<MutableList<TaskItem>>>
}