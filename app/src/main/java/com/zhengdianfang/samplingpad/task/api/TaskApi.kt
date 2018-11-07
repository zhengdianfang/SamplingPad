package com.zhengdianfang.samplingpad.task.api

import com.zhengdianfang.samplingpad.common.entities.Region
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.task.entities.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface TaskApi {

    companion object {
        const val ATTACHMENT_URL = "/attachments/showPhotos/"
    }

    @GET("app/listtaskscount")
    fun fetchStatusCount(): Call<Response<StatusCount>>

    @GET("app/listtasks/{status}")
    fun fetchTaskListGroupByStatus(@Path("status") status: Int): Call<Response<MutableList<TaskItem>>>

    @GET("app/listabnormaltasks")
    fun fetchErrorTaskListGroupByStatus(): Call<Response<MutableList<TaskItem>>>

    @PUT("app/samples/{id}")
    fun saveSample(@Path("id") id: String, @Body taskItem: TaskItem): Call<Response<String>>

    @PUT("app/samplesubmits/{id}")
    fun submitSample(@Path("id") id: String, @Body taskItem: TaskItem): Call<Response<String>>

    @GET("app/arealis/allTwo")
    fun fetchRegionData(): Call<Response<MutableList<Region>>>

    @GET("enterprises/{licenseNumber}")
    fun fetchEnterpriseByLicenseCode(@Path("licenseNumber") licenseNumber: String): Call<Response<Enterprise>>

    @GET("app/goods")
    fun fetchGoodsByBarcode(@Query("barCode") code: String): Call<Response<Goods>>

    @GET("app/enterpriseproduct/{qsNo}")
    fun fetchEntrustByCsNo(@Query("qsNo") code: String): Call<Response<Enterprise>>

    @POST("app/sampleabnormals")
    fun sumbitExceptionTask(@Body params: Map<String, String?>): Call<Response<Any>>

    @Multipart
    @POST("attachments/uploadFilesToFTP")
    fun uploadFile(
        @Part("attachmentUnitId") attachmentUnitId: RequestBody,
        @Part("businessType") businessType: RequestBody,
        @Part("attTypeName") attTypeName:RequestBody,
        @Part("attTypeId") attTypeId: RequestBody,
        @Part files:  Array<MultipartBody.Part>): Call<Response<AttachmentIds>>

    @GET("attachments/samplepic/{id}")
    fun fetchAttachmentIdsBySampleId(@Path("id") id: String): Call<Response<Array<AttachmentItem>>>


    @DELETE("attachments/{ids}")
    fun deleteAttachment(@Path("ids") ids: String): Call<Response<String>>
}