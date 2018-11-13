package com.zhengdianfang.samplingpad.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OptionItem(val id: Int, val name: String, val level: Int): Parcelable {
    override fun toString(): String {
        return name
    }
}


data class Region(val id: Int?, val name: String?, val levelId: Int = 0, val parentId: Int = 0) {
    companion object {

        const val LEVEL_PROVINCE = 2
        const val LEVEL_TOWN = 3
        const val LEVEL_COUNTY = 4
        const val LEVEL_STREET = 5
    }
}
data class Category(val id: Int?, val name: String?, val level: Int = 0, val parentId: Int = 0)
