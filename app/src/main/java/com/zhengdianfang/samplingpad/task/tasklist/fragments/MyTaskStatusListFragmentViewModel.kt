package com.zhengdianfang.samplingpad.task.tasklist.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.StatusCount
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class MyTaskStatusListFragmentViewModel(application: Application): AndroidViewModel(application) {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val countLiveData = MutableLiveData<StatusCount>()

    fun fetchStatusCount() {
        isLoadingLiveData.postValue(true)
        doAsync {
            try {
                val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                    .fetchStatusCount()
                    .execute()
                uiThread {
                    countLiveData.postValue(response.body()?.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLiveData.postValue(false)
            }

        }
    }

}