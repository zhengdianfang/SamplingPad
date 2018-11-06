package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.ApiClient.HOST
import com.zhengdianfang.samplingpad.http.AppResponseInterceptor
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.http.UploadInterceptor
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.AttachmentIds
import com.zhengdianfang.samplingpad.task.entities.Enterprise
import com.zhengdianfang.samplingpad.task.entities.Goods
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File

class TableFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val responseLiveData = MutableLiveData<Response<String>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val goodsLiveData = MutableLiveData<Goods>()
    val enterpriseLiveData = MutableLiveData<Enterprise>()
    val uploadImageResponseLiveData = MutableLiveData<AttachmentIds>()
    val uploadVideoResponseLiveData = MutableLiveData<AttachmentIds>()
    val attachmentIdsLiveData = MutableLiveData<Array<Int>>()

    fun saveSample(taskItem: TaskItem) {
        taskItem.latitude = App.INSTANCE.latitude
        taskItem.longitude = App.INSTANCE.longitude
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .saveSample(taskItem.id, taskItem)
                .execute()
            val body = response.body()
            uiThread {
                isLoadingLiveData.postValue(false)
                if (body != null) {
                    responseLiveData.postValue(body)
                }
            }
        }
    }

    fun submitSample(taskItem: TaskItem) {
        taskItem.latitude = App.INSTANCE.latitude
        taskItem.longitude = App.INSTANCE.longitude
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .submitSample(taskItem.id, taskItem)
                .execute()
            val body = response.body()
            uiThread {
                isLoadingLiveData.postValue(false)
                if (body != null) {
                    responseLiveData.postValue(body)
                }
            }
        }
    }

    fun getGoodsByBarcode(code: String) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .fetchGoodsByBarcode(code.trim())
                .execute()
            val goods = response.body()?.data
            uiThread {
                isLoadingLiveData.postValue(false)
                if (goods != null) {
                    goodsLiveData.postValue(goods)
                }
            }
        }
    }

    fun fetchEnterpriseByLincenseCode(licenseNumber: String) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .fetchEnterpriseByLicenseCode(licenseNumber.trim())
                .execute()
            val enterprise = response.body()?.data
            uiThread {
                isLoadingLiveData.postValue(false)
                if (enterprise != null) {
                    enterpriseLiveData.postValue(enterprise)
                }
            }
        }
    }

    fun fetchEntrustByCsNo(code: String) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .fetchEntrustByCsNo(code.trim())
                .execute()
            val enterprise = response.body()?.data
            uiThread {
                isLoadingLiveData.postValue(false)
                if (enterprise != null) {
                    enterpriseLiveData.postValue(enterprise)
                }
            }
        }
    }

    fun fetchAttachmentIdsBySampleId(id: String) {
       doAsync {
           val response = ApiClient.getRetrofit().create(TaskApi::class.java)
               .fetchAttachmentIdsBySampleId(id)
               .execute()
           val ids = response.body()?.data
           uiThread {
               if (ids != null) {
                   attachmentIdsLiveData.postValue(ids)
               }
           }
       }
    }

    fun uploadFile(taskItem: TaskItem, businessType: String, attTypeName: String, attachmentType: String, files: Array<File>, progressCallback: (progress: Int) -> Unit) {
        doAsync {

            val okHttpClient = OkHttpClient
                .Builder()
                .addNetworkInterceptor(UploadInterceptor { bytesWritten, contentLength ->
                    uiThread {
                        val progress = (bytesWritten.toDouble() / contentLength.toDouble() * 100).toInt()
                        progressCallback.invoke(progress)
                    }
                })
                .addNetworkInterceptor(AppResponseInterceptor())
                .build()

            val partFiles = files.map {file ->
                val requestFile = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    file
                )
                MultipartBody.Part.createFormData("file", file.name, requestFile)
            }.toTypedArray()

            val response = Retrofit.Builder()
                .baseUrl(HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaskApi::class.java)
                .uploadFile(
                    RequestBody.create(MediaType.parse("multipart/form-data"), taskItem.attachmentUnitId),
                    RequestBody.create(MediaType.parse("multipart/form-data"), businessType),
                    RequestBody.create(MediaType.parse("multipart/form-data"), attTypeName),
                    RequestBody.create(MediaType.parse("multipart/form-data"), attachmentType),
                    partFiles)
                .execute()

            uiThread {
                if (response.body() != null ) {
                    Timber.d("upload result ${response.body()?.data.toString()}")
                    if (attachmentType == "1") {
                        uploadImageResponseLiveData.postValue(response.body()!!.data)
                    } else if (attachmentType == "1") {
                        uploadVideoResponseLiveData.postValue(response.body()!!.data)
                    }
                }
            }
        }
    }
}