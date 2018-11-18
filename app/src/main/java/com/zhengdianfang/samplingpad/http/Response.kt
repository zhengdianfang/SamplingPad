package com.zhengdianfang.samplingpad.http

open class Response<T> {
    var msg = ""
    var code = 0
    var data: T? = null
    var userInfo: T? = null
    var token = ""
    var verdict = ""
}