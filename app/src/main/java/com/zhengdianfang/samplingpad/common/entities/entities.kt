package com.zhengdianfang.samplingpad.common.entities

data class SpinnerItem(val id: Int, val name: String) {
    override fun toString(): String {
        return name
    }
}
