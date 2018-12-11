package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.entities.TaskItem

class TaskSearchBar: LinearLayout, AdapterView.OnItemSelectedListener {

    private val SELECT_ALL_TASK_NUMBERS_HINT = "筛选任务"
    private val taskNumberSpinner by lazy { Spinner(context, null, R.attr.spinnerStyle, R.style.AppTheme_SpinnerStyle, Spinner.MODE_DIALOG) }
    private val sampleNumberEditText by lazy { EditText(context, null, R.attr.editTextStyle, R.style.AppTheme_EditText) }
    private val filterMeTaskCheckBox by lazy { CheckBox(context) }
    private val zeroLayoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT)
    private val taskNumberList = mutableListOf<String>()
    private val taskNumberAdapter by lazy { ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, taskNumberList) }
    private val allTasks = mutableListOf<TaskItem>()
    private var keyword = ""
    private var selectedTaskNumber = ""
    private var filterMeTaskChecked = false

    var filterTaskCallback: ((filters: MutableList<TaskItem>) -> Unit)? = null

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews()
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews()
    }

    fun updateTaskList(newTasks: List<TaskItem>) {
        allTasks.clear()
        allTasks.addAll(newTasks)
        this.filterTaskItems()
        this.updateTaskNumbers()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.selectedTaskNumber = if (position == 0) "" else taskNumberList[position]
        this.filterTaskItems()
    }

    private fun filterTaskItems() {
        if (this.filterTaskCallback != null) {
            this.filterTaskCallback!!(
                allTasks
                    .filter {
                        TextUtils.isEmpty(this.selectedTaskNumber) || it.implPlanCode == this.selectedTaskNumber
                    }
                    .filter {
                        TextUtils.isEmpty(this.keyword) || it.code == this.keyword
                    }
                    .filter { this.filterMeTaskChecked.not() ||  it.workerOneId == null || it.workerOneId == App.INSTANCE.user?.id || it.workerOneId == App.INSTANCE.user?.id2 }
                    .toMutableList()
            )
        }
    }

    private fun updateTaskNumbers() {
        taskNumberList.clear()
        taskNumberList.add(SELECT_ALL_TASK_NUMBERS_HINT)
        taskNumberList.addAll(allTasks.map { it.implPlanCode ?: "" }.distinct())
        taskNumberAdapter.notifyDataSetChanged()
    }

    private fun setupViews() {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setBackgroundColor(Color.WHITE)
        setPadding(16, 8, 16, 8)
        renderTaskNumberSpinner()
        renderSampleNumberEditText()
        renderFilterMeTaskCheckbox()
    }

    private fun renderTaskNumberSpinner() {
        taskNumberList.add(SELECT_ALL_TASK_NUMBERS_HINT)
        taskNumberSpinner.apply {
            zeroLayoutParams.weight = 1F
            layoutParams = zeroLayoutParams
            adapter = taskNumberAdapter

        }
        taskNumberSpinner.onItemSelectedListener = this
        addView(taskNumberSpinner)
    }

    private fun renderSampleNumberEditText() {
        sampleNumberEditText.apply {
            zeroLayoutParams.weight = 1F
            zeroLayoutParams.leftMargin = 16
            zeroLayoutParams.rightMargin = 16
            layoutParams = zeroLayoutParams
            hint = "输入抽样单号"
            maxLines = 1
            setLines(1)
            setSingleLine(true)
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = InputType.TYPE_CLASS_TEXT

        }
        sampleNumberEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                this.keyword = textView.text.toString()
                this.filterTaskItems()
                true
            }
            false
        }
        addView(sampleNumberEditText)

    }

    private fun renderFilterMeTaskCheckbox() {
        filterMeTaskCheckBox.text = "仅看指定任务"
        filterMeTaskCheckBox.setOnCheckedChangeListener { _, checked ->
            this.filterMeTaskChecked = checked
            this.filterTaskItems()
        }
        addView(filterMeTaskCheckBox)
    }
}