package com.zhengdianfang.samplingpad.common.entities

data class SpinnerItem(val id: Int, val name: String) {
    override fun toString(): String {
        return name
    }
}


data class Region(val id: Int, val name: String, val levelId: Int, val parentId: Int) {
    companion object {

        const val LEVEL_AREA = 4
        const val LEVEL_STREET = 5
    }
}