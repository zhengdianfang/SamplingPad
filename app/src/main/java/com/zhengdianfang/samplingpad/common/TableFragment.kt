package com.zhengdianfang.samplingpad.common

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.normal_product.fragments.TableFragmentViewModel
import kotlinx.android.synthetic.main.fragment_fourth_normal_table_layout.*

abstract class TableFragment: BaseFragment() {

    protected val taskItem by lazy { arguments?.getParcelable("task") as TaskItem }
    val tableFragmentViewModel by lazy { ViewModelProviders.of(this).get(TableFragmentViewModel::class.java) }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
    }

    open fun setupViews() {
        view?.findViewById<Button>(R.id.nextButtonButton)?.setOnClickListener {
            if (validateRequiredField(tableFrame)) {
                assembleSubmitTaskData()
                tableFragmentViewModel.saveSample(taskItem)
            }
        }
        view?.findViewById<Button>(R.id.resetButton)?.setOnClickListener {
            this.clear()
        }
    }

    open fun clear() {
        if (tableFrame != null) {
            clearDataByFrameLayout(tableFrame)
        }
    }

    open fun validateRequiredField(viewGroup: ViewGroup): Boolean {
        if (viewGroup != null) {
            for (index in 0 until viewGroup.childCount) {
                val childView = viewGroup.getChildAt(index)
                if (childView is BaseComponent) {
                    if (childView.visibility == View.VISIBLE &&
                        childView.isRequired() &&
                        childView.checkFieldHasValue().not()) {
                        val toastText = childView.labelTextView.text.toString().replace("*", "")
                        Toast.makeText(context, "请填写$toastText", Toast.LENGTH_SHORT).show()
                        return false
                    }
                } else if(childView is ViewGroup && childView.visibility == View.VISIBLE) {
                   if (!validateRequiredField(childView)) {
                       return false
                   }
                }
            }
        }
        return true
    }

    private fun clearDataByFrameLayout(viewGroup: ViewGroup) {
        for (index in 0 until viewGroup.childCount) {
            val childView = viewGroup.getChildAt(index)
            if (childView is BaseComponent) {
                childView.clear()
            } else if(childView is ViewGroup) {
                this.clearDataByFrameLayout(childView)
            }
        }
    }

    abstract fun submitSuccessful()
    abstract fun assembleSubmitTaskData()

    open fun bindViewModel() {
        tableFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
           if (isLoading!!) {
               startLoading()
           } else {
               stopLoading()
           }
        })
        tableFragmentViewModel.responseLiveData.observe(this, Observer { response ->
            Toast.makeText(context, response!!.msg, Toast.LENGTH_SHORT).show()
            if (response.code == 200) {
                this.submitSuccessful()
            }
        })
    }
}