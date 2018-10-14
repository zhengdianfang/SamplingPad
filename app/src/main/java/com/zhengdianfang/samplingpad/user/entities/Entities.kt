package com.zhengdianfang.samplingpad.user.entities

import com.zhengdianfang.samplingpad.api.Response

data class User(
    val username: String,
    val token: String
): Response()