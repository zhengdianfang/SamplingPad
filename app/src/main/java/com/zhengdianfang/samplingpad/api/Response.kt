package com.zhengdianfang.samplingpad.api

open class Response<T> {
    var msg = ""
    var code = 0
    var data: T? = null
    var token = ""
}