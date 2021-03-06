package com.zhengdianfang.samplingpad.main.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.main.api.MainApi
import com.zhengdianfang.samplingpad.main.entites.VerifyParams
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class VerifyFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val responseLiveData = MutableLiveData<Response<String>>()

    fun postVerifySample(
        implPlanCode: String?,
        level1Name: String?,
        enterpriseLicenseNumber: String?,
        sampleName: String?,
        sampleProductDate: String?,
        chainBrand: String?,
        sampleDate: String?,
        producerCsNo: String?,
        enterpriseName: String?
) {
        isLoadingLiveData.postValue(true)
        doAsync {
            try {
                val response = ApiClient.getRetrofit().create(MainApi::class.java)
                    .postVerifySample(VerifyParams(implPlanCode, level1Name, enterpriseLicenseNumber, sampleName,
                        sampleProductDate, chainBrand, sampleDate , producerCsNo, enterpriseName))
                    .execute()

                val body = response.body()
                if (body != null) {
                    uiThread {
                        responseLiveData.postValue(body)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}