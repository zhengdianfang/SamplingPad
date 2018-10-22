package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_fourth_normal_table_layout.*

open class FourthTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem?): FourthTableFragment {
            val fragment = FourthTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fourth_normal_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        producerBarcodeEditText.setEditTextContent(taskItem.producerBarcode)
        sampleNameEditText.setEditTextContent(taskItem.sampleName)
        sampleBrandEditText.setEditTextContent(taskItem.sampleBrand)
        samplePriceEditText.setEditTextContent("${taskItem.samplePrice ?: ""}")
        sampleTypeRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleType)
        sampleAttributeRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleAttribute)
        sampleSourceRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleSource)
        samplePackageTypeRadioGroup.setDefaultCheckedRadioButton(taskItem.samplePackageType)
        samplePackagingRadioGroup.setDefaultCheckedRadioButton(taskItem.samplePackaging)
        sampleBatchNoEditText.setEditTextContent(taskItem.sampleBatchNo)
        sampleQgpEditText.setEditTextContent(taskItem.sampleQgp)
        sampleSpecificationEditText.setEditTextContent(taskItem.sampleSpecification)
        sampleQualityLevelEditText.setEditTextContent(taskItem.sampleQualityLevel)
        sampleModeRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleMode)
        sampleFormRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleForm)
        sampleAmountEditText.setEditTextContent("${taskItem.sampleAmount ?: ""}")
        sampleAmountForTestEditText.setEditTextContent("${taskItem.sampleAmountForTest ?: ""}")
        sampleAmountForRetestEditText.setEditTextContent("${taskItem.sampleAmountForRetest ?: ""}")
        sampleStorageEnvironmentRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleStorageEnvironment)
        storagePlaceForRetestRadioGroup.setDefaultCheckedRadioButton(taskItem.storagePlaceForRetest)
        lableStandardEditText.setEditTextContent(taskItem.lableStandard)

    }

    override fun assembleSubmitTaskData() {
        taskItem.producerBarcode = producerBarcodeEditText.getContent()
        taskItem.sampleName = sampleNameEditText.getContent()
        taskItem.sampleBrand = sampleBrandEditText.getContent()
        taskItem.samplePrice = samplePriceEditText.getContent().toDouble()
        taskItem.sampleType = sampleTypeRadioGroup.getCheckedText()
        taskItem.sampleAttribute = sampleAttributeRadioGroup.getCheckedText()
        taskItem.sampleSource =  sampleSourceRadioGroup.getCheckedText()
        taskItem.samplePackageType = samplePackageTypeRadioGroup.getCheckedText()
        taskItem.samplePackaging = samplePackagingRadioGroup.getCheckedText()
        taskItem.sampleBatchNo = sampleBatchNoEditText.getContent()
        taskItem.sampleQgp = sampleQgpEditText.getContent()
        taskItem.sampleSpecification = sampleSpecificationEditText.getContent()
        taskItem.sampleQualityLevel = sampleQualityLevelEditText.getContent()
        taskItem.sampleMode = sampleModeRadioGroup.getCheckedText()
        taskItem.sampleForm = sampleFormRadioGroup.getCheckedText()
        taskItem.sampleAmount = sampleAmountEditText.getContent().toInt()
        taskItem.sampleAmountForTest = sampleAmountForTestEditText.getContent().toInt()
        taskItem.sampleAmountForRetest = sampleAmountForRetestEditText.getContent().toInt()
        taskItem.sampleStorageEnvironment = sampleStorageEnvironmentRadioGroup.getCheckedText()
        taskItem.storagePlaceForRetest = storagePlaceForRetestRadioGroup.getCheckedText()
        taskItem.lableStandard = lableStandardEditText.getContent()
    }

    override fun clearAllFilledData() {
       for (index in 0 until tableFrame.childCount) {
           val view = tableFrame.getChildAt(index)
           if (view is BaseComponent) {
               view.clear()
           }
       }
    }

    override fun submitSuccessful() {
        start(FifthTableFragment.newInstance(taskItem))
    }
}