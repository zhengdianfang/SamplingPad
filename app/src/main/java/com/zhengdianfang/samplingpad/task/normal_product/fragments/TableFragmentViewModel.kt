package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.AppResponseInterceptor
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.http.UploadInterceptor
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.*
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
import java.lang.Exception

class TableFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val responseLiveData = MutableLiveData<Response<String>>()
    val sumbitResponseLiveData = MutableLiveData<Response<String>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val goodsLiveData = MutableLiveData<Goods>()
    val enterpriseLiveData = MutableLiveData<Enterprise>()
    val uploadImageResponseLiveData = MutableLiveData<AttachmentIds>()
    val uploadVideoResponseLiveData = MutableLiveData<AttachmentIds>()
    val uploadErrorResponseLiveData = MutableLiveData<String>()
    val attachmentIdsLiveData = MutableLiveData<Array<AttachmentItem>>()
    val reportLiveData = MutableLiveData<Array<AttachmentItem>>()
    val sampleImageLiveData = MutableLiveData<Array<AttachmentItem>>()
    val deleteAttachmentLiveData = MutableLiveData<Boolean>()
    val generatePdfLiveData = MutableLiveData<Map<String, String>>()
    val pdfHistoryLiveData = MutableLiveData<Map<String, String>>()

    fun saveSample(taskItem: TaskItem) {
        taskItem.latitude = App.INSTANCE.latitude
        taskItem.longitude = App.INSTANCE.longitude
        isLoadingLiveData.postValue(true)
        doAsync {
            try {
                val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                    .saveSample(taskItem.id, taskItem)
                    .execute()
                val body = response.body()
                uiThread {
                    if (body != null) {
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

    fun submitSample(taskItem: TaskItem) {
        taskItem.latitude = App.INSTANCE.latitude
        taskItem.longitude = App.INSTANCE.longitude
        isLoadingLiveData.postValue(true)
        doAsync {
            try {
                val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                    .submitSample(taskItem.id, taskItem)
                    .execute()
                val body = response.body()
                uiThread {
                    if (body != null) {
                        sumbitResponseLiveData.postValue(body)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLiveData.postValue(false)
            }

        }
    }

    fun generatePdf(taskItem: TaskItem) {
        taskItem.latitude = App.INSTANCE.latitude
        taskItem.longitude = App.INSTANCE.longitude
        isLoadingLiveData.postValue(true)

        //先保存, 保存成功再执行生成pdf
        doAsync {
            try {
                val saveResponse = ApiClient.getRetrofit().create(TaskApi::class.java)
                    .saveSample(taskItem.id, taskItem)
                    .execute()
                val saveBody = saveResponse.body()
                if (saveBody?.code == 200) {
                    val pdfResponse = ApiClient.getRetrofit().create(TaskApi::class.java)
                        .generateSamplePdf(taskItem.id)
                        .execute()
                    val pdfBody = pdfResponse.body()
                    uiThread {
                        if (pdfBody != null) {
                            generatePdfLiveData.postValue(pdfBody.data)
                        }
                    }
                } else {
                    responseLiveData.postValue(saveBody)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLiveData.postValue(false)
            }


        }
    }

    fun getGoodsByBarcode(code: String) {
        isLoadingLiveData.postValue(true)
        doAsync {
            try {
                val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                    .fetchGoodsByBarcode(code.trim())
                    .execute()
                val goods = response.body()?.data
                uiThread {
                    if (goods != null) {
                        goodsLiveData.postValue(goods)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }  finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun fetchEnterpriseByLincenseCode(licenseNumber: String) {
        isLoadingLiveData.postValue(true)
        doAsync {
            try {
                val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                    .fetchEnterpriseByLicenseCode(licenseNumber.trim())
                    .execute()
                val enterprise = response.body()?.data
                uiThread {
                    if (enterprise != null) {
                        enterpriseLiveData.postValue(enterprise)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLiveData.postValue(false)
            }

        }
    }

    fun fetchEntrustByCsNo(code: String) {
        isLoadingLiveData.postValue(true)
        doAsync {
            try {
                val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                    .fetchEntrustByCsNo(code.trim())
                    .execute()
                val enterprise = response.body()?.data
                uiThread {
                    if (enterprise != null) {
                        enterpriseLiveData.postValue(enterprise)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingLiveData.postValue(false)
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

    fun fetchReportBySampleId(id: String) {
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .fetchReportBySampleId(id)
                .execute()
            val ids = response.body()?.data
            uiThread {
                if (ids != null) {
                    reportLiveData.postValue(ids)
                }
            }
        }
    }

    fun fetchSampleImageBySampleId(id: String) {
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .fetchSampleBySampleId(id)
                .execute()
            val ids = response.body()?.data
            uiThread {
                if (ids != null) {
                    sampleImageLiveData.postValue(ids)
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

            try {
                val response = Retrofit.Builder()
                    .baseUrl(ApiClient.getHost())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TaskApi::class.java)
                    .uploadFile(
                        RequestBody.create(MediaType.parse("multipart/form-data"), taskItem.attachmentUnitId ?: taskItem.id),
                        RequestBody.create(MediaType.parse("multipart/form-data"), businessType),
                        RequestBody.create(MediaType.parse("multipart/form-data"), attTypeName),
                        RequestBody.create(MediaType.parse("multipart/form-data"), attachmentType),
                        partFiles)
                    .execute()

                val body = response.body()
                uiThread {
                    if (body != null && body.code == 200 ) {
                        Timber.d("upload result ${response.body()?.data.toString()}")
                        if (attachmentType == "4") {
                            uploadVideoResponseLiveData.postValue(response.body()!!.data)
                        } else {
                            uploadImageResponseLiveData.postValue(response.body()!!.data)
                        }
                    } else {
                        uploadErrorResponseLiveData.postValue(body?.msg ?: "")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun deleteAttachments(ids: String) {
        doAsync {
            ApiClient.getRetrofit().create(TaskApi::class.java)
                .deleteAttachment(ids)
                .execute()
            deleteAttachmentLiveData.postValue(true)
        }
    }

    fun fetchPdfById(sampleReportAttachmentId: Int) {
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .fetchPdfById(sampleReportAttachmentId)
                .execute()
            val body = response.body()
            if (body != null) {
                uiThread {
                    pdfHistoryLiveData.postValue(body.data)
                }
            }
        }

    }
}