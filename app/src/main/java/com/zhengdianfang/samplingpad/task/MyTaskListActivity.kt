package com.zhengdianfang.samplingpad.task

import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.fragments.MyTaskListFragment
import me.yokeyword.fragmentation.SupportActivity

class MyTaskListActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, MyTaskListFragment.newInstance())
    }
}
