package com.zhengdianfang.samplingpad.http

open class Response<T> {
    var msg = ""
    var code = 0
    var data: T? = null
    var token = ""
}