package com.zhengdianfang.samplingpad.task.tasklist.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TaskListWithStatusFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val taskListLiveData = MutableLiveData<MutableList<TaskItem>>()

    fun loadTaskDataByStatus(task_Status: Task_Status) {
        doAsync {
            val response = ApiClient.INSTANCE
                .create(TaskApi::class.java)
                .fetchTaskListGroupByStatus(task_Status.value)
                .execute()
            val data = response.body()?.data
            if (data != null) {
                uiThread {
                    taskListLiveData.postValue(data)
                }
            }
        }
    }

    fun loadErrorTaskDataByStatus() {
        doAsync {
            val response = ApiClient.INSTANCE
                .create(TaskApi::class.java)
                .fetchErrorTaskListGroupByStatus()
                .execute()
            val data = response.body()?.data
            if (data != null) {
                uiThread {
                    taskListLiveData.postValue(data)
                }
            }
        }
    }
}