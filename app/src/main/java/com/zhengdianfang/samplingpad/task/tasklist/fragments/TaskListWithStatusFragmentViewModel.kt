package com.zhengdianfang.samplingpad.task.tasklist.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.TaskException
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class TaskListWithStatusFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val taskListLiveData = MutableLiveData<MutableList<TaskItem>>()
    val exceptionTaskListLiveData = MutableLiveData<MutableList<TaskException>>()

    fun loadTaskDataByStatus(task_Status: Task_Status) {
        doAsync {
            try {
                val response = ApiClient.getRetrofit()
                    .create(TaskApi::class.java)
                    .fetchTaskListGroupByStatus(task_Status.value)
                    .execute()
                val data = response.body()?.data
                    uiThread {
                        if (data != null) {
                            taskListLiveData.postValue(data)
                        }
                    }
            }catch(e :Exception) {

            } finally {
                taskListLiveData.postValue(null)
            }
        }
    }

    fun loadErrorTaskDataByStatus() {
        doAsync {
            try {
                val response = ApiClient.getRetrofit()
                    .create(TaskApi::class.java)
                    .fetchErrorTaskListGroupByStatus()
                    .execute()
                val data = response.body()?.data
                uiThread {
                    if (data != null) {
                        exceptionTaskListLiveData.postValue(data)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                exceptionTaskListLiveData.postValue(null)
            }
        }
    }
}