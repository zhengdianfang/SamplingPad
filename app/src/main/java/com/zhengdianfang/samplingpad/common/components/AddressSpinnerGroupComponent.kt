package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.common.LabelView
import com.zhengdianfang.samplingpad.common.entities.Region
import com.zhengdianfang.samplingpad.task.api.TaskApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddressSpinnerGroupComponent: BaseComponent {

    private var provincialSpinnerTextView: TextView? = null
    private var provincialSpinnerDialog: MaterialDialog? = null

    private var townSpinnerTextView: TextView? = null
    private var townSpinnerDialog: MaterialDialog? = null

    private var countySpinnerTextView: TextView? = null
    private var countySpinnerDialog: MaterialDialog? = null

    private var regionList = listOf<Region>()
    private var provincialList: List<Region>? = null
    private var townList: List<Region>? = null

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context)
    }

    override fun clear() {
        provincialSpinnerTextView!!.setText(R.string.province_hint)
        townSpinnerTextView!!.setText(R.string.town_hint)
        countySpinnerTextView!!.setText(R.string.county_hint)
        provincialSpinnerTextView!!.setTextColor(ContextCompat.getColor(context, R.color.colorLightGray))
        townSpinnerTextView!!.setTextColor(ContextCompat.getColor(context, R.color.colorLightGray))
        countySpinnerTextView!!.setTextColor(ContextCompat.getColor(context, R.color.colorLightGray))
    }

    fun getContent(): String {
        val area =
            if (provincialSpinnerTextView!!.text == resources.getString(R.string.spinner_region_hint)) "" else provincialSpinnerTextView!!.text
        val street =
            if (townSpinnerTextView!!.text == resources.getString(R.string.spinner_street_hint)) "" else townSpinnerTextView!!.text
        return "$area$street"
    }

    fun fetchData() {
        doAsync {
            val response = ApiClient.INSTANCE
                .create(TaskApi::class.java)
                .fetchRegionData()
                .execute()
            val body = response.body()
            if (body?.data != null) {
                regionList = body.data!!
                provincialList = body.data?.filter { it.levelId == Region.LEVEL_PROVINCE }
                if (provincialList != null) {
                    uiThread {
                        provincialSpinnerDialog?.setItems(*provincialList!!.map { it.name }.toTypedArray())
                    }
                }
            }
        }
    }

    private fun setupViews(context: Context) {
        initLabelTextView(context)
        renderProvincialSpinner()
        renderTownSpinner()
        renderCountySpinner()
    }

    private fun renderProvincialSpinner() {
        provincialSpinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 8, 16, 8)
            setText(R.string.province_hint)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                rightMargin = resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                weight = 1F
            }
        }

        provincialSpinnerDialog = createSpinnerDataDialog(MaterialDialog.ListCallback { _, _, position, text ->
            provincialSpinnerTextView!!.text = text
            provincialSpinnerTextView!!.setTextColor(Color.BLACK)
            val selected = provincialList?.get(position)
            if (selected != null) {
                townList = regionList.filter { it.parentId == selected.id && it.levelId == Region.LEVEL_TOWN }
                if (townList != null) {
                    townSpinnerDialog!!.setItems(*townList!!.map { it.name }.toTypedArray())
                }
            }
        })
        provincialSpinnerTextView!!.setOnClickListener {
           provincialSpinnerDialog!!.show()
        }
        addView(provincialSpinnerTextView)
    }

    private fun renderTownSpinner() {

        townSpinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 8, 16, 8)
            setText(R.string.town_hint)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                rightMargin = resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                weight = 1F
            }
        }

        townSpinnerDialog = createSpinnerDataDialog(MaterialDialog.ListCallback { _, _, position, text ->
            townSpinnerTextView!!.text = text
            townSpinnerTextView!!.setTextColor(Color.BLACK)
            val selected = regionList[position]
            val countyList = regionList.filter { it.parentId == selected.id && it.levelId == Region.LEVEL_COUNTY }
            countySpinnerDialog!!.setItems(*countyList.map { it.name }.toTypedArray())
        })

        townSpinnerTextView!!.setOnClickListener {
            townSpinnerDialog!!.show()
        }
        addView(townSpinnerTextView)

    }
     private fun renderCountySpinner() {
        countySpinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 8, 16, 8)
            setText(R.string.county_hint)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                weight = 1F
            }
        }

        countySpinnerDialog = createSpinnerDataDialog(MaterialDialog.ListCallback { _, _, _, text ->
            countySpinnerTextView!!.text = text
            countySpinnerTextView!!.setTextColor(Color.BLACK)
        })

        countySpinnerTextView!!.setOnClickListener {
            countySpinnerDialog!!.show()
        }
        addView(countySpinnerTextView)

    }

    private fun initLabelTextView(context: Context) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = resources.getString(R.string.admin_region_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
        }

        addView(labelTextView)
    }

    private fun createSpinnerDataDialog(listCallback: MaterialDialog.ListCallback): MaterialDialog {
        return MaterialDialog.Builder(context)
            .items("")
            .itemsCallback(listCallback)
            .build()
    }

}