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
import kotlinx.android.synthetic.main.fragment_third_normal_table_layout.*
import java.util.*
import io.github.xudaojie.qrcodelib.CaptureActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Path
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhengdianfang.samplingpad.common.entities.OptionItem
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.Response
import org.jetbrains.anko.defaultSharedPreferences


open class ThirdTableFragment: TableFragment() {

    private val calendarUnitDialog by lazy {
        MaterialDialog.Builder(context!!)
            .items(*context!!.resources.getStringArray(R.array.calendar_unit_array))
            .itemsCallback { _, _, _, text ->
                unitSpinner.text = text
            }
            .build()
    }

    private val priceUnitDialog by lazy {
        MaterialDialog.Builder(context!!)
            .items(unitofquantityOptions)
            .itemsCallback { _, _, _, text ->
                priceUnitSpinner.text = text
                sampleInspectAmountUnitSpinner.text = text
                samplePreparationUnitSpinner.text = text
            }
            .build()
    }

    private val sampleInspectAmountUnitDialog by lazy {
        MaterialDialog.Builder(context!!)
            .items(unitofquantityOptions)
            .itemsCallback { _, _, _, text ->
                sampleInspectAmountUnitSpinner.text = text
            }
            .build()
    }

    private val samplePreparationUnitDialog by lazy {
        MaterialDialog.Builder(context!!)
            .items(unitofquantityOptions)
            .itemsCallback { _, _, _, text ->
                samplePreparationUnitSpinner.text = text
            }
            .build()
    }



    private val unitofquantityOptions by lazy {
        val unitofquantityOptionsString = context?.defaultSharedPreferences?.getString("unitofquantityOptions", "") ?: ""
        var options = mutableListOf<OptionItem>()
        if (unitofquantityOptionsString.isEmpty().not()) {
                options = Gson().fromJson<MutableList<OptionItem>>(unitofquantityOptionsString, object: TypeToken<MutableList<OptionItem>>(){}.type)

        }
        options
    }

    companion object {
        const val QR_SCAN_REQUEST = 0x00002
        const val NCODE_QR_SCAN_REQUEST = 0x00003


        fun newInstance(taskItem: TaskItem?): ThirdTableFragment {
            val fragment = ThirdTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_third_normal_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        producerBarcodeEditText.setEditTextContent(taskItem.producerBarcode)
        producerBarcodeEditText.search = { code ->
           tableFragmentViewModel.getGoodsByBarcode(code)
        }
        sampleNCodeEditText.setEditTextContent(taskItem.sampleNcode)
        sampleNameEditText.setEditTextContent(taskItem.sampleName)
        sampleBrandEditText.setEditTextContent(taskItem.sampleBrand)
        samplePriceEditText.setEditTextContent("${taskItem.samplePrice ?: ""}")
        priceUnitSpinner.text = taskItem.priceUnit
        priceUnitSpinner.setOnClickListener {
            priceUnitDialog.show()
        }
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
        sampleInspectAmountUnitSpinner.text = taskItem.sampleInspectAmountUnit
        sampleInspectAmountUnitSpinner.setOnClickListener {
            sampleInspectAmountUnitDialog.show()
        }
        samplePreparationUnitSpinner.text = taskItem.samplePreparationUnit
        samplePreparationUnitSpinner.setOnClickListener {
            samplePreparationUnitDialog.show()
        }
        sampleDateView.setDefaultDate(Calendar.getInstance())
        sampleProduceDateView.setDefaultDate(Calendar.getInstance())
        sampleProduceDateView.setDefaultDateType(taskItem.sampleDateKind)
        unitSpinner.text = taskItem.sampleQgpUnit
        sampleQgpEditText.setEditTextContent(taskItem.sampleQgp?.toString())
        unitSpinner.setOnClickListener {
            calendarUnitDialog.show()
        }

        qrScanButton.setOnClickListener {
            val i = Intent(context, CaptureActivity::class.java)
            startActivityForResult(i, QR_SCAN_REQUEST)
        }

        ncodeQRScanButton.setOnClickListener {
            val i = Intent(context, CaptureActivity::class.java)
            startActivityForResult(i, NCODE_QR_SCAN_REQUEST)
        }
        if (taskItem.enterpriseLinkId == 3) {
            beautyFoodTypeGroupView.visibility = View.VISIBLE
            beautyFoodTypeGroupView.fetchData("${ApiClient.getHost()}beautyFoodTypesAll")
            beautyFoodTypeGroupView.setOptionItem(OptionItem(taskItem.beautyFoodTypeId, taskItem.beautyFoodType))
            wellBrandNameEditText.visibility = View.VISIBLE
            wellBrandNameEditText.setEditTextContent(taskItem.wellBrandName)
        } else {
            beautyFoodTypeGroupView.visibility = View.GONE
            wellBrandNameEditText.visibility = View.GONE
        }
        samplenominalDateEditText.setEditTextContent(taskItem.nominalDate)
        sampleCommentEditText.setDefaultContent(taskItem.comment)
        inspectionPackageNumberEditText.setEditTextContent(taskItem.inspectionPackageNumber)
        samplePackingNumberEditText.setEditTextContent(taskItem.samplePackingNumber)
        beautyFoodTypeGroupView.setOptionItem(OptionItem(taskItem.beautyFoodTypeId, taskItem.beautyFoodType))

        producerActiveRadioGroup.radioButtonCheckCallback = { _, option ->
            taskItem.sampleActive = option.id
            if (taskItem.sampleActive == 1) {
                resourceSpinnerGroupView.visibility = View.VISIBLE
            } else {
                resourceSpinnerGroupView.visibility = View.GONE
            }
        }
        producerActiveRadioGroup.setDefaultCheckedRadioButton(taskItem.sampleActive)

        resourceSpinnerGroupView.fetchData("${ApiClient.getHost()}app/areas/origin")
        resourceSpinnerGroupView.setOptionItem(OptionItem(1, taskItem.sampleSourceArea ?: "中国"))
    }

    override fun assembleSubmitTaskData() {
        taskItem.producerBarcode = producerBarcodeEditText.getContent()
        taskItem.sampleName = sampleNameEditText.getContent()
        taskItem.sampleBrand = sampleBrandEditText.getContent()
        taskItem.samplePrice = samplePriceEditText.getContent()?.toDoubleOrNull()
        taskItem.priceUnit = priceUnitSpinner.text.toString()
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
        taskItem.sampleAmount = sampleAmountEditText.getContent()?.toDoubleOrNull()
        taskItem.sampleAmountForTest = sampleAmountForTestEditText.getContent()?.toDoubleOrNull()
        taskItem.sampleAmountForRetest = sampleAmountForRetestEditText.getContent()?.toDoubleOrNull()
        taskItem.setSampleStorageEnvironment(sampleStorageEnvironmentRadioGroup.getCheckedOption())
        taskItem.setStoragePlaceForRetest(storagePlaceForRetestRadioGroup.getCheckedOption())
        taskItem.lableStandard = lableStandardEditText.getContent()
        taskItem.sampleDate = sampleDateView.getDate()
        taskItem.sampleDateKind = sampleProduceDateView.selectedType
        taskItem.sampleProductDate = sampleProduceDateView.getDate()
        taskItem.sampleInspectAmountUnit = sampleInspectAmountUnitSpinner.text.toString()
        taskItem.samplePreparationUnit = samplePreparationUnitSpinner.text.toString()
        taskItem.beautyFoodType = beautyFoodTypeGroupView.getSelectedOption()?.name
        taskItem.beautyFoodTypeId = beautyFoodTypeGroupView.getSelectedOption()?.id
        taskItem.wellBrandName = wellBrandNameEditText.getContent()
        taskItem.nominalDate = samplenominalDateEditText.getContent()
        taskItem.comment = sampleCommentEditText.getContent()
        taskItem.inspectionPackageNumber = inspectionPackageNumberEditText.getContent()
        taskItem.samplePackingNumber = samplePackingNumberEditText.getContent()
        taskItem.sampleNcode = sampleNCodeEditText.getContent()
        taskItem.sampleActive = producerActiveRadioGroup.getCheckedOption()?.id
        taskItem.setAgencyOriginArea(resourceSpinnerGroupView.getSelectedOption())
    }

    override fun clear() {
        super.clear()
        unitSpinner.text = null
    }

    override fun submitSuccessful() {
        start(FourthTableFragment.newInstance(taskItem))
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
        } else if (requestCode == NCODE_QR_SCAN_REQUEST) {
            sampleNCodeEditText.setEditTextContent(data?.getStringExtra("result"))
        }
    }
}