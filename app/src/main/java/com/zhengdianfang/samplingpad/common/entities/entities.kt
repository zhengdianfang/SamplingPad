package com.zhengdianfang.samplingpad.common.entities

data class SpinnerItem(val id: Int, val name: String) {
    override fun toString(): String {
        return name
    }
}


data class Region(val id: Int, val name: String, val levelId: Int, val parentId: Int) {
    companion object {

        const val LEVEL_PROVINCE = 2
        const val LEVEL_TOWN = 3
        const val LEVEL_COUNTY = 4
        const val LEVEL_STREET = 5
    }
}