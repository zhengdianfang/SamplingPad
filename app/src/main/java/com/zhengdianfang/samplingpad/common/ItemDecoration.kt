package com.zhengdianfang.samplingpad.common

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect.bottom = 12
        outRect.top = 12
        outRect.left = 24
        outRect.right = 24
    }
}