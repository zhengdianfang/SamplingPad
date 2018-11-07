package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_fourth_normal_table_layout.*
import java.util.*
import io.github.xudaojie.qrcodelib.CaptureActivity
import android.content.Intent



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
        sampleDateView.setDefaultDate(Calendar.getInstance())
        sampleProduceDateView.setDefaultDate(Calendar.getInstance())
        unitSpinner.text = taskItem.sampleQgpUnit
        sampleQgpEditText.setEditTextContent(taskItem.sampleQgp?.toString())
        unitSpinner.setOnClickListener {
            calendarUnitDialog.show()
        }

        qrScanButton.setOnClickListener {
            val i = Intent(context, CaptureActivity::class.java)
            startActivityForResult(i, QR_SCAN_REQUEST)
        }

    }

    override fun assembleSubmitTaskData() {
        taskItem.producerBarcode = producerBarcodeEditText.getContent()
        taskItem.sampleName = sampleNameEditText.getContent()
        taskItem.sampleBrand = sampleBrandEditText.getContent()
        taskItem.samplePrice = samplePriceEditText.getContent()?.toDoubleOrNull()
        taskItem.setSampleTypeOption(sampleTypeRadioGroup.getCheckedOption())
        taskItem.setSampleAttributeOption(sampleAttributeRadioGroup.getCheckedOption())
        taskItem.setSampleSourceOption(sampleSourceRadioGroup.getCheckedOption())
        taskItem.setSamplePackageType(samplePackageTypeRadioGroup.getCheckedOption())
        taskItem.setSamplePackaging(samplePackagingRadioGroup.getCheckedOption())
        taskItem.sampleBatchNo = sampleBatchNoEditText.getContent()
        taskItem.sampleQgp = sampleQgpEditText.getContent()?.toIntOrNull()
        taskItem.sampleQgpUnit = unitSpinner.text.toString()
        taskItem.sampleSpecification = sampleSpecificationEditText.getContent()
        taskItem.sampleQualityLevel = sampleQualityLevelEditText.getContent()
        taskItem.setSampleMode(sampleModeRadioGroup.getCheckedOption())
        taskItem.setSampleForm(sampleFormRadioGroup.getCheckedOption())
        taskItem.sampleAmount = sampleAmountEditText.getContent()?.toIntOrNull()
        taskItem.sampleAmountForTest = sampleAmountForTestEditText.getContent()?.toIntOrNull()
        taskItem.sampleAmountForRetest = sampleAmountForRetestEditText.getContent()?.toIntOrNull()
        taskItem.setSampleStorageEnvironment(sampleStorageEnvironmentRadioGroup.getCheckedOption())
        taskItem.setStoragePlaceForRetest(storagePlaceForRetestRadioGroup.getCheckedOption())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == QR_SCAN_REQUEST) {
            producerBarcodeEditText.setEditTextContent(data?.getStringExtra("result"))
        }
    }
}