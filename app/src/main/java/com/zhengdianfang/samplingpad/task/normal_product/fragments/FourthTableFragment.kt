package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.QRCodeFragment
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_fourth_normal_table_layout.*

open class FourthTableFragment: TableFragment() {

    private val calendarUnitDialog by lazy {
        MaterialDialog.Builder(context!!)
            .items(*context!!.resources.getStringArray(R.array.calendar_unit_array))
            .itemsCallback { _, _, _, text ->
                unitSpinner.text = text
            }
            .build()
    }

    companion object {
        const val QR_SCAN_REQUEST = 0x00002

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
        producerBarcodeEditText.search = { code ->
           tableFragmentViewModel.getGoodsByBarcode(code)
        }
        sampleNameEditText.setEditTextContent(taskItem.sampleName)
        sampleBrandEditText.setEditTextContent(taskItem.sampleBrand)
        samplePriceEditText.setEditTextContent("${taskItem.samplePrice ?: ""}")
        sampleTypeRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleType)
        sampleAttributeRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleAttribute)
        sampleSourceRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleSource)
        samplePackageTypeRadioGroup.setDefaultCheckedRadioButton(taskItem.samplePackageType)
        samplePackagingRadioGroup.setDefaultCheckedRadioButton(taskItem.samplePackaging)
        sampleBatchNoEditText.setEditTextContent(taskItem.sampleBatchNo)
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
        sampleInspectAmountUnitEditText.setEditTextContent(taskItem.sampleInspectAmountUnit)
        samplePreparationUnitEditText.setEditTextContent(taskItem.samplePreparationUnit)

        unitSpinner.setOnClickListener {
            calendarUnitDialog.show()
        }

        qrScanButton.setOnClickListener {
            startForResult(QRCodeFragment.newInstance(), QR_SCAN_REQUEST)
        }

    }

    override fun assembleSubmitTaskData() {
        taskItem.producerBarcode = producerBarcodeEditText.getContent()
        taskItem.sampleName = sampleNameEditText.getContent()
        taskItem.sampleBrand = sampleBrandEditText.getContent()
        taskItem.samplePrice = samplePriceEditText.getContent()?.toDoubleOrNull()
        taskItem.sampleType = sampleTypeRadioGroup.getCheckedText()
        taskItem.sampleAttribute = sampleAttributeRadioGroup.getCheckedText()
        taskItem.sampleSource =  sampleSourceRadioGroup.getCheckedText()
        taskItem.samplePackageType = samplePackageTypeRadioGroup.getCheckedText()
        taskItem.samplePackaging = samplePackagingRadioGroup.getCheckedText()
        taskItem.sampleBatchNo = sampleBatchNoEditText.getContent()
        taskItem.sampleQgp = sampleQgpEditText.getContent()?.toIntOrNull()
        taskItem.sampleQgpUnit = unitSpinner.text.toString()
        taskItem.sampleSpecification = sampleSpecificationEditText.getContent()
        taskItem.sampleQualityLevel = sampleQualityLevelEditText.getContent()
        taskItem.sampleMode = sampleModeRadioGroup.getCheckedText()
        taskItem.sampleForm = sampleFormRadioGroup.getCheckedText()
        taskItem.sampleAmount = sampleAmountEditText.getContent()?.toIntOrNull()
        taskItem.sampleAmountForTest = sampleAmountForTestEditText.getContent()?.toIntOrNull()
        taskItem.sampleAmountForRetest = sampleAmountForRetestEditText.getContent()?.toIntOrNull()
        taskItem.sampleStorageEnvironment = sampleStorageEnvironmentRadioGroup.getCheckedText()
        taskItem.storagePlaceForRetest = storagePlaceForRetestRadioGroup.getCheckedText()
        taskItem.lableStandard = lableStandardEditText.getContent()
        taskItem.sampleDate = sampleDateView.getDate()
        taskItem.sampleProductDate = sampleProduceDateView.getDate()
        taskItem.sampleInspectAmountUnit = sampleInspectAmountUnitEditText.getContent()
        taskItem.samplePreparationUnit = samplePreparationUnitEditText.getContent()
    }

    override fun clear() {
        super.clear()
        unitSpinner.text = null
    }

    override fun submitSuccessful() {
        start(FifthTableFragment.newInstance(taskItem))
    }

    override fun bindViewModel() {
        super.bindViewModel()
        tableFragmentViewModel.goodsLiveData.observe(this, Observer { goods ->
            if (null != goods) {
                taskItem.mergeGoods(goods)
                this.setupViews()
            }
        })
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == QR_SCAN_REQUEST) {
            producerBarcodeEditText.setEditTextContent(data?.getString("result"))
        }
    }
}