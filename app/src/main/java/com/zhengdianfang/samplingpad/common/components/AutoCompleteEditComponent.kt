package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import android.widget.LinearLayout
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AutoCompleteEditComponent: BaseComponent {

    private lateinit var editTextView: AutoCompleteTextView
    var search: ((text: String)-> Unit)? = null
    private val selectableItems = mutableListOf<String>()
    private val adapter by lazy { ItemAdapter(context, selectableItems) }
    private val publishSubject = PublishSubject.create<String>()
    private val disposable =
        publishSubject
            .throttleLast(500, TimeUnit.MILLISECONDS)
            .subscribe {text ->
                search?.invoke(text)
            }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (disposable.isDisposed.not()) {
            disposable.dispose()
        }
    }

    override fun checkFieldHasValue(): Boolean {
        return TextUtils.isEmpty(this.editTextView.text.toString()).not()
    }

    fun setEditTextContent(text: String?) {
        editTextView.setText(text)
    }

    fun notifySelectItems(data: MutableList<String>) {
        selectableItems.clear()
        selectableItems.addAll(data)
        adapter.notifyDataSetChanged()
    }

    fun getContent(): String {
        return editTextView.text.toString()
    }

    override fun clear() {
        editTextView.text = null
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_EditComponent)
        initLabelTextView(context, attrs)
        initEditTextView(context, attrs)
    }

    private fun initEditTextView(context: Context, attrs: TypedArray) {
        editTextView = AutoCompleteTextView(context, null, R.attr.editTextStyle, R.style.AppTheme_AutoCompleteEditText).apply {
            hint = attrs.getString(R.styleable.AppTheme_EditComponent_edit_hint)
            maxLines = 1
            setLines(1)
            setSingleLine(true)
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1f
                gravity = Gravity.CENTER_VERTICAL
            }
            setDropDownBackgroundResource(R.drawable.autocomplete_dropdown_background)
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    publishSubject.onNext(editable.toString())
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })
        }
        editTextView.setAdapter(adapter)
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

    private inner class ItemAdapter(context: Context, private val data: MutableList<String>):
        ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data) {

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(text: CharSequence?): FilterResults {
                    val filterResults = FilterResults()
                    filterResults.count = count
                    filterResults.values = data
                    return filterResults
                }

                override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                }
            }
        }
    }
}