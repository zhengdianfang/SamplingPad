package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.*
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.api.ApiClient
import com.zhengdianfang.samplingpad.common.LabelView
import com.zhengdianfang.samplingpad.common.entities.SpinnerItem

class SpinnerComponent: BaseComponent {

    private lateinit var spinnerTextView: TextView
    private lateinit var spinnerDialog: SpinnerDialog
    private var hint = ""

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    fun fetchSpinnerItems(url: String) {
        spinnerDialog.fetchData(url)
    }

    fun setDefaultText(text: String?) {
        if (TextUtils.isEmpty(text).not()) {
            spinnerTextView.text = text
        }
    }

    override fun clear() {
        spinnerTextView.text = hint
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_SpinnerComponent)
        initLabelTextView(context, attrs)
        initSpinnerView(context, attrs)
    }

    private fun initSpinnerView(context: Context, attrs: TypedArray) {
        hint = resources.getString(attrs.getResourceId(R.styleable.AppTheme_SpinnerComponent_spinner_hint, 0))
        val apiUrl = resources.getString(attrs.getResourceId(R.styleable.AppTheme_SpinnerComponent_spinner_api, 0))
        spinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setTextColor(Color.BLACK)
            text = hint
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 8, 16, 8)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                weight = 1F
            }
        }

        if (TextUtils.isEmpty(apiUrl).not()) {
            createSpinnerDataDialog( 0)
            spinnerTextView.setOnClickListener {
                spinnerDialog.show()
            }
        }
        addView(spinnerTextView)
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = resources.getString(attrs.getResourceId(R.styleable.AppTheme_SpinnerComponent_spinner_label, 0))
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_SpinnerComponent_spinner_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }

    private fun createSpinnerDataDialog(index: Int): SpinnerDialog {
        val spinnerItems = mutableListOf<SpinnerItem>()
        val builder = MaterialDialog.Builder(context)
            .items(spinnerItems)
            .itemsCallback { dialog, _, _, text ->
                if (dialog is SpinnerDialog) {
                    spinnerTextView.text = text
                }
            }
        return SpinnerDialog(builder, spinnerItems, index)
    }

}