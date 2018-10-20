package com.zhengdianfang.samplingpad.common.components

import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhengdianfang.samplingpad.api.ApiClient
import com.zhengdianfang.samplingpad.api.Response
import com.zhengdianfang.samplingpad.common.entities.SpinnerItem
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SpinnerDialog(builder: Builder, private val url: String, val index: Int) : MaterialDialog(builder) {

    private var spinnerItems = listOf<SpinnerItem>()

    init {
        fetchData()
    }

    private fun fetchData() {
        doAsync {
            val request = Request.Builder()
                .url(url)
                .build()
            val call = ApiClient.okHttpClient.newCall(request)
            val response = call.execute()
            if (response.isSuccessful) {
                val responseData = response.body()?.string() ?: ""
                if (responseData.isNotEmpty()) {
                    val json2Pojo =
                        Gson().fromJson<Response<MutableList<SpinnerItem>>>(responseData, object: TypeToken<Response<MutableList<SpinnerItem>>>(){}.type)
                    if (json2Pojo.data != null) {
                        spinnerItems = json2Pojo.data!!.toList()
                        uiThread {
                            notifyItemsChanged()
                            setItems(*json2Pojo.data!!.map { it.name }.toTypedArray())
                        }
                    }
                }
            }
        }
    }
}