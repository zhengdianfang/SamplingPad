package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.common.LabelView
import java.util.*


class ProduceDateComponent : BaseComponent {

    private lateinit var spinnerTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var spinnerDialog: MaterialDialog
    private val datePickDialog by lazy {
        val dialog = DatePickDialog()
        dialog.onConfirmCallback = { date ->
            dateTextView.text = date
        }
        dialog
    }
    var selectedType = ""

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    fun getDate(): String? {
        val text = dateTextView.text.toString()
        if (TextUtils.isEmpty(text)) {
            return null
        }
        return text
    }

    override fun clear() {
        dateTextView.text = ""
        this.selectedType = ""
    }

    override fun noValueTip(): String {
        return "请选择日期"
    }

    override fun checkFieldHasValue(): Boolean {
        return TextUtils.isEmpty(dateTextView.text.toString()).not() && TextUtils.isEmpty(this.selectedType).not()
    }

    fun setDefaultDate(calendar: Calendar) {
        dateTextView.text = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)} 00:00:00"
    }

    fun setDefaultDateType(type: String?) {
        this.selectedType = type ?: ""
        this.spinnerTextView.text = type
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_DateComponent)
        initLabelTextView(context, attrs)
        initSpinnerView(context, attrs)
        initDateTextView(context, attrs)
    }

    private fun initDateTextView(context: Context, attrs: TypedArray) {
        dateTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            hint = attrs.getString(R.styleable.AppTheme_DateComponent_date_hint)
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_calendar, 0)
            setPadding(16, 18, 16, 18)
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1f
                leftMargin = 16
                gravity = Gravity.CENTER_VERTICAL
            }
        }

        dateTextView.setOnClickListener {
            if (context is BaseActivity ) {
                datePickDialog.show(context.supportFragmentManager, "datePicker" )
            }
        }
        addView(dateTextView)
    }


    private fun initSpinnerView(context: Context, attrs: TypedArray) {
        spinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setTextColor(Color.BLACK)
            hint = "选择日期类型"
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 16, 16, 16)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            }
        }
        spinnerDialog = createSpinnerDataDialog(MaterialDialog.ListCallback { _, _, _, text ->
            spinnerTextView.setTextColor(Color.BLACK)
            spinnerTextView.text = text
            this.selectedType = text.toString()
        })
        spinnerTextView.setOnClickListener {
            spinnerDialog.show()
        }
        addView(spinnerTextView)
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = "*"
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_DateComponent_date_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }

    private fun createSpinnerDataDialog(listCallback: MaterialDialog.ListCallback): MaterialDialog {
        return MaterialDialog.Builder(context)
            .items(R.array.date_type_array)
            .itemsCallback(listCallback)
            .build()
    }
}