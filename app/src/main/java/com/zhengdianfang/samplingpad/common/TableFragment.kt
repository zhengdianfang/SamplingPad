package com.zhengdianfang.samplingpad.common

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.normal_product.fragments.TableFragmentViewModel

abstract class TableFragment: BaseFragment() {

    protected val taskItem by lazy { arguments?.getParcelable("task") as TaskItem }
    protected val tableFragmentViewModel by lazy { ViewModelProviders.of(this).get(TableFragmentViewModel::class.java) }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
    }

    open fun setupViews() {
        view?.findViewById<Button>(R.id.nextButtonButton)?.setOnClickListener {
            tableFragmentViewModel.saveSample(taskItem)
        }
        view?.findViewById<Button>(R.id.resetButton)?.setOnClickListener {
            this.clearAllFilledData()
        }
    }

    abstract fun submitSuccessful()
    abstract fun assembleSubmitTaskData()
    abstract fun clearAllFilledData()

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
            if (response!!.code == 200) {
                this.submitSuccessful()
            }
        })
    }
}