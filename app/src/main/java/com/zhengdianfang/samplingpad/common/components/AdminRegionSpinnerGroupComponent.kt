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

class AdminRegionSpinnerGroupComponent: BaseComponent {

    private var areaSpinnerTextView: TextView? = null
    private var areaSpinnerDialog: MaterialDialog? = null

    private var streetSpinnerTextView: TextView? = null
    private var streetSpinnerDialog: MaterialDialog? = null
    private var regionList = listOf<Region>()
    private var areaList: List<Region>? = null
    private var streetList: List<Region>? = null

    private var selectedArea: Region? = null
    private var selectedStreet: Region? = null

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context)
    }

    override fun clear() {
        areaSpinnerTextView!!.text = null
        streetSpinnerTextView!!.text = null
    }

    fun getContent(): String {
        val area = areaSpinnerTextView!!.text
        val street =streetSpinnerTextView!!.text
        return "$area$street"
    }

    fun setDefaultStreet(id: Int?) {
        if (id != null) {
            val streets = regionList.filter{ it.id == id }
            if (streets.isNotEmpty()) {
                val areas = regionList.filter { it.id == streets.first().parentId }
                streetSpinnerTextView?.text = streets.first().name
                if (areas.isNotEmpty()) {
                    areaSpinnerTextView?.text = areas.first().name
                }
            }
        }
    }

    fun getArea() = this.selectedArea
    fun getStreet() = this.selectedStreet

    fun fetchData() {
        doAsync {
            val response = ApiClient.getRetrofit()
                .create(TaskApi::class.java)
                .fetchRegionData()
                .execute()
            val body = response.body()
            if (body?.data != null) {
                regionList = body.data!!
                areaList = body.data?.filter { it.levelId == Region.LEVEL_COUNTY }
                if (areaList != null) {
                    uiThread {
                        areaSpinnerDialog?.setItems(*areaList!!.map { it.name }.toTypedArray())
                    }
                }
            }
        }
    }

    private fun setupViews(context: Context) {
        initLabelTextView(context)
        renderAreaSpinner()
        renderStreetSpinner()
    }

    private fun renderAreaSpinner() {
        areaSpinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 8, 16, 8)
            setText(R.string.spinner_region_hint)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                rightMargin = resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                weight = 1F
            }
        }

        areaSpinnerDialog = createSpinnerDataDialog(MaterialDialog.ListCallback { _, _, position, text ->
            areaSpinnerTextView!!.text = text
            selectedArea = areaList?.get(position)
            if (selectedArea != null) {
                streetList = regionList.filter { it.parentId == selectedArea!!.id && it.levelId == Region.LEVEL_STREET }
                if (streetList != null) {
                    streetSpinnerDialog!!.setItems(*streetList!!.map { it.name }.toTypedArray())
                }
            }
        })
        areaSpinnerTextView!!.setOnClickListener {
           areaSpinnerDialog!!.show()
        }
        addView(areaSpinnerTextView)
    }

    private fun renderStreetSpinner() {

        streetSpinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 8, 16, 8)
            setText(R.string.spinner_street_hint)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                rightMargin = resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                weight = 1F
            }
        }

        streetSpinnerDialog = createSpinnerDataDialog(MaterialDialog.ListCallback { _, _, position, text ->
            streetSpinnerTextView!!.text = text
            streetSpinnerTextView!!.setTextColor(Color.BLACK)
            selectedStreet = streetList?.get(position)
        })

        streetSpinnerTextView!!.setOnClickListener {
            streetSpinnerDialog!!.show()
        }
        addView(streetSpinnerTextView)

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