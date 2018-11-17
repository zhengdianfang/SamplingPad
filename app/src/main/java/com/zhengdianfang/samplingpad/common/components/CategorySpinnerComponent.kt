package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView
import com.zhengdianfang.samplingpad.common.entities.Category
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CategorySpinnerComponent: BaseComponent {

    private val level1NameSpinner by lazy { findViewById<TextView>(R.id.level1NameSpinner) }
    private val level2NameSpinner by lazy { findViewById<TextView>(R.id.level2NameSpinner) }
    private val level3NameSpinner by lazy { findViewById<TextView>(R.id.level3NameSpinner) }
    private val level4NameSpinner by lazy { findViewById<TextView>(R.id.level4NameSpinner) }
    private lateinit var levelNameViews: Array<TextView>
    private var categorys = mutableListOf<Category>()
    private lateinit var categorySpinnerDialogs: Array<MaterialDialog>

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context)
    }

    override fun clear() {
        levelNameViews.forEach { it.text = "" }
    }

    override fun isRequired(): Boolean {
        return true
    }

    override fun checkFieldHasValue(): Boolean {
        return TextUtils.isEmpty(level1NameSpinner.text).not() &&
            TextUtils.isEmpty(level2NameSpinner.text).not() &&
            TextUtils.isEmpty(level3NameSpinner.text).not() &&
            TextUtils.isEmpty(level4NameSpinner.text).not()
    }

    fun setDefaultValues(taskItem: TaskItem) {
        level1NameSpinner.text = taskItem.level1Name
        level2NameSpinner.text = taskItem.level2Name
        level3NameSpinner.text = taskItem.level3Name
        level4NameSpinner.text = taskItem.level4Name
    }

    fun mergeSelectedValuesToTaskItem(taskItem: TaskItem) {
        taskItem.level1Name = level1NameSpinner.text.toString()
        taskItem.level2Name = level2NameSpinner.text.toString()
        taskItem.level3Name = level3NameSpinner.text.toString()
        taskItem.level4Name = level4NameSpinner.text.toString()
    }

    private fun createSpinnerDialogForAllLevelCategory() {

        categorySpinnerDialogs = arrayOf(
            createSpinnerDataDialog(ItemsCallback(1)),
            createSpinnerDataDialog(ItemsCallback(2)),
            createSpinnerDataDialog(ItemsCallback(3)),
            createSpinnerDataDialog(ItemsCallback(4))
        )
    }

    private fun createSpinnerDataDialog(listCallback: MaterialDialog.ListCallback): MaterialDialog {
        return MaterialDialog.Builder(context)
            .items("")
            .itemsCallback(listCallback)
            .build()
    }

    private fun fetchCategorys() {
        doAsync {
            val response = ApiClient.getRetrofit().create(TaskApi::class.java)
                .fetchCategroyData()
                .execute()
            val body = response.body()
            if (body != null) {
                categorys = body!!.data ?: mutableListOf()
                uiThread {
                    for (index in 0..3) {
                        reFillSpinnerItems(index)
                    }
                }
            }
        }
    }

    private fun reFillSpinnerItems(level: Int) {
        if (level == 0) {
            categorySpinnerDialogs[level]
                .setItems(*categorys.filter { it.parentId == 0 && it.level == level + 1 }.map { it.name }.toTypedArray())
        } else {

            val levelText = levelNameViews[level - 1].text.toString()
            if (TextUtils.isEmpty(levelText).not()) {
                val category = categorys.find { it.name == levelText && it.level == level }
                if (category != null) {
                    categorySpinnerDialogs[level]
                        .setItems(*categorys.filter { it.parentId == category.id && it.level == level + 1 }.map { it.name }.toTypedArray())
                }
            }
        }
    }

    private fun setupViews(context: Context) {
        this.labelTextView = LabelView(context)
        this.labelTextView.text = "食品类型"
        LayoutInflater.from(context).inflate(R.layout.category_spinner_layout, this)
        levelNameViews = arrayOf(
            level1NameSpinner,
            level2NameSpinner,
            level3NameSpinner,
            level4NameSpinner
        )
        createSpinnerDialogForAllLevelCategory()
        levelNameViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                categorySpinnerDialogs[index].show()
            }
        }
        fetchCategorys()
    }

    private inner class ItemsCallback(val levelId: Int): MaterialDialog.ListCallback {
        override fun onSelection(dialog: MaterialDialog?, itemView: View?, position: Int, text: CharSequence?) {
            val category = categorys.find { it.name == text && it.level == levelId }
            if (category != null) {
                if (levelId != 4) {
                    val dialog = categorySpinnerDialogs[levelId]
                    dialog.setItems(*categorys.filter { it.parentId == category.id && it.level == levelId + 1 }.map { it.name }.toTypedArray())
                }
                levelNameViews[levelId - 1].text = category.name
            }

        }
    }

}