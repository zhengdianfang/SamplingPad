package com.zhengdianfang.samplingpad.main.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class TaskListFragmentViewModel(application: Application): AndroidViewModel(application) {

    val taskListLiveData = MutableLiveData<MutableList<TaskItem>>()

    fun loadTaskData() {
        doAsync {
            try {
                val response = ApiClient.getRetrofit()
                    .create(TaskApi::class.java)
                    .fetchTaskListGroupByStatus(Task_Status.WAIT_VERIFY.value)
                    .execute()
                val data = response.body()?.data
                if (data != null) {
                    uiThread {
                        taskListLiveData.postValue(data)
                    }
                } else {
                    taskListLiveData.postValue(null)
                }
            }catch (e: Exception) {
                e.printStackTrace()
                taskListLiveData.postValue(null)
            }

        }
    }
}