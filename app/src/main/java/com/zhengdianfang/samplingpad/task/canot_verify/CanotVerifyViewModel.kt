package com.zhengdianfang.samplingpad.task.canot_verify

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.task.api.TaskApi
import org.jetbrains.anko.doAsync

class CanotVerifyViewModel(application: Application) : AndroidViewModel(application) {
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val responseLiveData = MutableLiveData<Response<Any>>()

    fun submitCanotVerifyTask(params: Map<String, String?>) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.INSTANCE.create(TaskApi::class.java)
                .sumbitExceptionTask(params)
                .execute()
            isLoadingLiveData.postValue(false)
            if (response.body() != null) {
                responseLiveData.postValue(response.body())
            }
        }
    }
}