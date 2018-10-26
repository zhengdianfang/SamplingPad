package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.Goods
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TableFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val responseLiveData = MutableLiveData<Response<String>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val goodsLiveData = MutableLiveData<Goods>()

    fun saveSample(taskItem: TaskItem) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.INSTANCE.create(TaskApi::class.java)
                .saveSample(taskItem.id, taskItem)
                .execute()
            val body = response.body()
            if (body != null) {
                uiThread {
                    isLoadingLiveData.postValue(false)
                    responseLiveData.postValue(body)
                }
            }
        }
    }

    fun submitSample(taskItem: TaskItem) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.INSTANCE.create(TaskApi::class.java)
                .submitSample(taskItem.id, taskItem)
                .execute()
            val body = response.body()
            if (body != null) {
                uiThread {
                    isLoadingLiveData.postValue(false)
                    responseLiveData.postValue(body)
                }
            }
        }
    }

    fun getGoodsByBarcode(code: String) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.INSTANCE.create(TaskApi::class.java)
                .fetchGoodsByBarcode(code.trim())
                .execute()
            val goods = response.body()?.data
            if (goods != null) {
                uiThread {
                    isLoadingLiveData.postValue(false)
                    goodsLiveData.postValue(goods)
                }
            }
        }
    }
}