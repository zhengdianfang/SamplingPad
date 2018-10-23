package com.zhengdianfang.samplingpad.main.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.main.api.MainApi
import com.zhengdianfang.samplingpad.main.api.VerifySampleParam
import org.jetbrains.anko.doAsync

class VerifyFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()

    fun postVerifySample(params: Map<String, String>) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.INSTANCE.create(MainApi::class.java)
                .postVerifySample(params)
                .execute()

            val body = response.body()
            if (body != null) {
                messageLiveData.postValue(body.msg)
                isLoadingLiveData.postValue(false)
            }
        }
    }
}