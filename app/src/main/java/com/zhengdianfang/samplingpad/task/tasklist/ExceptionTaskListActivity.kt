package com.zhengdianfang.samplingpad.task.tasklist

import android.os.Bundle
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.task.tasklist.fragments.TaskListWithExceptionFragment

class ExceptionTaskListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, TaskListWithExceptionFragment.newInstance())
    }

}
