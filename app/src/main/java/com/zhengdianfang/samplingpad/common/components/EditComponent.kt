package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.loc.i
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

class EditComponent: BaseComponent, TextWatcher {

    private lateinit var editTextView: EditText
    var search: ((text: String)-> Unit)? = null
    private var lastTime = System.currentTimeMillis()

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    fun setEditTextContent(text: String?) {
        editTextView.setText(text)
    }

    fun getContent(): String? {
        val text = editTextView.text.toString()
        if (TextUtils.isEmpty(text)) {
            return null
        }
        return text
    }

    override fun clear() {
        editTextView.setText("")
    }

    override fun setDisable(disable: Boolean) {
        this.editTextView.isEnabled = disable
    }

    override fun checkFieldHasValue(): Boolean {
        return TextUtils.isEmpty(this.editTextView.text.toString()).not()
    }

    override fun afterTextChanged(editable: Editable) {
        if (System.currentTimeMillis() - lastTime > 1000) {
            lastTime = System.currentTimeMillis()
            this.search?.invoke(editable.toString().trim())
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_EditComponent)
        initLabelTextView(context, attrs)
        initEditTextView(context, attrs)
    }

    private fun initEditTextView(context: Context, attrs: TypedArray) {
        editTextView = EditText(context, null, R.attr.editTextStyle, R.style.AppTheme_EditText).apply {
            hint = attrs.getString(R.styleable.AppTheme_EditComponent_edit_hint)
            maxLines = 1
            setLines(1)
            setSingleLine(true)
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1f
                gravity = Gravity.CENTER_VERTICAL
            }
        }
        if (attrs.getBoolean(R.styleable.AppTheme_EditComponent_edit_search, false)) {
            editTextView.addTextChangedListener(this)
        }
        addView(editTextView)
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_EditComponent_edit_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_EditComponent_edit_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }
}