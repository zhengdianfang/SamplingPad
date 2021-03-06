package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.common.LabelView
import java.util.*


class DateComponent : BaseComponent {

    private lateinit var dateTextView: TextView

    private val datePickDialog by lazy {
        val dialog = DatePickDialog()
        dialog.onConfirmCallback = { date ->
            dateTextView.text = date
        }
        dialog
    }

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
    }

    override fun checkFieldHasValue(): Boolean {
        return TextUtils.isEmpty(dateTextView.text.toString()).not()
    }

    fun setDefaultDate(calendar: Calendar) {
        dateTextView.text = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)} 00:00:00"
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_DateComponent)
        initLabelTextView(context, attrs)
        initDateTextView(context, attrs)
    }

    private fun initDateTextView(context: Context, attrs: TypedArray) {
        dateTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            hint = attrs.getString(R.styleable.AppTheme_DateComponent_date_hint)
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_calendar, 0)
            setPadding(16, 16, 16, 16)
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1f
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

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_DateComponent_date_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_DateComponent_date_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }
}