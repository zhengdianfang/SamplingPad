package com.zhengdianfang.samplingpad.task.tasklist

import android.os.Bundle
import com.zhengdianfang.samplingpad.task.tasklist.fragments.MyTaskStatusListFragment
import me.yokeyword.fragmentation.SupportActivity

class MyTaskListActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, MyTaskStatusListFragment.newInstance())
    }
}
