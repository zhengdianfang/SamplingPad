package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.common.LabelView
import com.zhengdianfang.samplingpad.common.entities.OptionItem
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SpinnerComponent: BaseComponent {

    private lateinit var spinnerTextView: TextView
    private lateinit var spinnerDialog: MaterialDialog
    private var options = mutableListOf<OptionItem>()
    private var selectedOption: OptionItem? = null

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    fun setDisable() {
        this.spinnerTextView.setBackgroundResource(0)
        this.spinnerTextView.setOnClickListener {  }
    }

    fun fetchData(url: String, predicate: (OptionItem) -> Boolean = { true }) {
        doAsync {
            val request = Request.Builder()
                .url(url)
                .build()
            val call = ApiClient.getHttpClient().newCall(request)
            val response = call.execute()
            if (response.isSuccessful) {
                val responseData = response.body()?.string() ?: ""
                if (responseData.isNotEmpty()) {
                    val json2Pojo =
                        Gson().fromJson<Response<MutableList<OptionItem>>>(responseData, object: TypeToken<Response<MutableList<OptionItem>>>(){}.type)
                    if (json2Pojo.data != null) {
                        options = json2Pojo.data!!.filter(predicate).toMutableList()
                        uiThread {
                            spinnerDialog.setItems(*options.asSequence().map { it.name }.toList().toTypedArray())
                        }
                    }
                }
            }
        }
    }

    fun getSelectedOption(): OptionItem? {
        return this.selectedOption
    }

    fun setOptionItem(optionItem: OptionItem) {
        this.selectedOption = optionItem
    }

    override fun clear() {
        spinnerTextView.text = null
    }

    override fun checkFieldHasValue(): Boolean {
        return TextUtils.isEmpty(spinnerTextView.text.toString()).not()
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_SpinnerComponent)
        initLabelTextView(context, attrs)
        initSpinnerView(context, attrs)
    }

    private fun initSpinnerView(context: Context, attrs: TypedArray) {
        spinnerTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            gravity = Gravity.CENTER_VERTICAL
            setTextColor(Color.BLACK)
            hint = resources.getString(attrs.getResourceId(R.styleable.AppTheme_SpinnerComponent_spinner_hint, 0))
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            setPadding(16, 16, 16, 16)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                weight = 1F
            }
        }
        spinnerDialog = createSpinnerDataDialog(MaterialDialog.ListCallback { _, _, position, text ->
            spinnerTextView.setTextColor(Color.BLACK)
            spinnerTextView.text = text
            this.selectedOption = options[position]
        })
        spinnerTextView.setOnClickListener {
            spinnerDialog.show()
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

    private fun createSpinnerDataDialog(listCallback: MaterialDialog.ListCallback): MaterialDialog {
        return MaterialDialog.Builder(context)
            .items("")
            .itemsCallback(listCallback)
            .build()
    }

}