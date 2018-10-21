package com.zhengdianfang.samplingpad.task.network_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

class ThirdTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem): ThirdTableFragment {
            val fragment = ThirdTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_third_network_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
    }

    override fun submitSuccessful() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun assembleSubmitTaskData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllFilledData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}