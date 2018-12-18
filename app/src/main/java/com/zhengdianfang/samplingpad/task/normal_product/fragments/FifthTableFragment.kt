package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA
import com.vincent.filepicker.activity.VideoPickActivity
import com.vincent.filepicker.filter.entity.ImageFile
import com.vincent.filepicker.filter.entity.VideoFile
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.ItemDecoration
import com.zhengdianfang.samplingpad.common.PhotoPreviewFragment
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.common.VideoFragment
import com.zhengdianfang.samplingpad.common.pdf.PdfPreviewActivity
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.AttachmentItem
import com.zhengdianfang.samplingpad.task.entities.Purpose_Type
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.UploadItem
import kotlinx.android.synthetic.main.fragment_fifth_normal_table_layout.*
import timber.log.Timber
import java.io.File

open class FifthTableFragment: TableFragment() {

    private val MIN_UPLOAD_PHOTO_COUNT = 7

    private val uploadDialog by lazy {
        MaterialDialog.Builder(context!!)
            .progress(false, 100)
            .build()
    }

    private val imageAttachments = mutableListOf<MultiItemEntity>()
    private val videoAttachments = mutableListOf<MultiItemEntity>()
    private val pdfAttachments = mutableListOf<MultiItemEntity>()

    private var sampleImageId = 0
    private var notifyReportImageId = 0

    companion object {

        const val SELECT_PHOTO_REQUEST = 0x00001
        const val SELECT_VIDEO_REQUEST = 0x00002
        const val SELECT_REPORT_REQUEST = 0x00003
        const val SELECT_SAMPLE_REQUEST = 0x00004

        fun newInstance(taskItem: TaskItem): FifthTableFragment {
            val fragment = FifthTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fifth_normal_table_layout, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                SELECT_PHOTO_REQUEST -> {
                    val photoPaths = data.getParcelableArrayListExtra<ImageFile>(Constant.RESULT_PICK_IMAGE)
                    Timber.d("select photos: $photoPaths")
                    uploadDialog.show()
                    tableFragmentViewModel.uploadFile(
                        taskItem, "sample", "现场照片", "1", photoPaths.map { File(it.path) }.toTypedArray()) {
                        Timber.d("upload progress $it")
                        uploadDialog.setProgress(it)
                        uploadDialog.setCancelable(it == 0 || it == 100)
                    }
                }
                SELECT_VIDEO_REQUEST -> {
                    val videoPaths = data.getParcelableArrayListExtra<VideoFile>(Constant.RESULT_PICK_VIDEO)
                    Timber.d("select videos: $videoPaths")
                    uploadDialog.show()
                    tableFragmentViewModel.uploadFile(
                        taskItem, "sample", "现场视频", "4", videoPaths.map { File(it.path) }.toTypedArray()) {
                        uploadDialog.setProgress(it)
                    }
                }
                SELECT_REPORT_REQUEST -> {
                    uploadDialog.show()
                    val photoPaths = data.getParcelableArrayListExtra<ImageFile>(Constant.RESULT_PICK_IMAGE)
                    tableFragmentViewModel.uploadFile(
                        taskItem, "sample", "告知书", "2", photoPaths.map { File(it.path) }.toTypedArray()) {
                        Timber.d("upload progress $it")
                        uploadDialog.setProgress(it)
                        uploadDialog.setCancelable(it == 0 || it == 100)
                    }
                }
                SELECT_SAMPLE_REQUEST -> {
                    uploadDialog.show()
                    val photoPaths = data.getParcelableArrayListExtra<ImageFile>(Constant.RESULT_PICK_IMAGE)
                    tableFragmentViewModel.uploadFile(
                        taskItem, "sample", "抽样单", "3", photoPaths.map { File(it.path) }.toTypedArray()) {
                        Timber.d("upload progress $it")
                        uploadDialog.setProgress(it)
                        uploadDialog.setCancelable(it == 0 || it == 100)
                    }
                }
            }
        }
    }

    private fun fetchAttachment() {
        tableFragmentViewModel.fetchAttachmentIdsBySampleId(this.taskItem.attachmentUnitId ?: this.taskItem.id)
        tableFragmentViewModel.fetchReportBySampleId(this.taskItem.attachmentUnitId ?: this.taskItem.id)
        tableFragmentViewModel.fetchSampleImageBySampleId(this.taskItem.attachmentUnitId ?: this.taskItem.id)
        if (taskItem.sampleReportAttachmentId != null) {
            tableFragmentViewModel.fetchPdfById(taskItem.sampleReportAttachmentId!!)
        }
    }


    override fun setupViews() {

        saveOnlyButton.setOnClickListener {
            tableFragmentViewModel.saveSample(taskItem)
        }

        submitButton.setOnClickListener {
            if (imageAttachments.size < MIN_UPLOAD_PHOTO_COUNT && taskItem.inspectionPurposeId != Purpose_Type.EVALUATE.value) {
                Toast.makeText(context, "最少上传7张图片", Toast.LENGTH_SHORT).show()
            } else {
                tableFragmentViewModel.submitSample(taskItem)
            }
        }

        generateButton.setOnClickListener {
            tableFragmentViewModel.generatePdf(taskItem)
        }
        uploadNotifyReportImage.setOnClickListener {
            val intent = Intent(context, ImagePickActivity::class.java)
            intent.putExtra(IS_NEED_CAMERA, true)
            intent.putExtra(Constant.MAX_NUMBER, 1)
            startActivityForResult(intent, SELECT_REPORT_REQUEST)
        }

        uploadSampleImage.setOnClickListener {
            val intent = Intent(context, ImagePickActivity::class.java)
            intent.putExtra(IS_NEED_CAMERA, true)
            intent.putExtra(Constant.MAX_NUMBER, 1)
            startActivityForResult(intent, SELECT_SAMPLE_REQUEST)
        }
        notifyReportImage.setOnClickListener {
            if (this.notifyReportImageId != 0) {
                startForResult(PhotoPreviewFragment.newInstance(this.notifyReportImageId), 0x0001)
            }
        }

        sampleImage.setOnClickListener {
            if (this.sampleImageId != 0) {
                startForResult(PhotoPreviewFragment.newInstance(this.sampleImageId), 0x0001)
            }
        }
        renderImageFrame()
        renderVideoFrame()
        renderPdfFrame()
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        fetchAttachment()
    }

    override fun bindViewModel() {
        tableFragmentViewModel.attachmentIdsLiveData.observe(this, Observer { attachments ->
            if (null != attachments) {
                imageAttachments.clear()
                imageAttachments.addAll(attachments.filter { it.documentType == ".jpg" || it.documentType == ".png" || it.documentType == ".jpeg" }.toTypedArray())
                imageAttachments.add(UploadItem())
                imageFrame.adapter.notifyDataSetChanged()

                videoAttachments.clear()
                videoAttachments.addAll(attachments.filter { it.documentType == ".mp4" }.toTypedArray())
                videoAttachments.add(UploadItem())
                videoFrame.adapter.notifyDataSetChanged()
            }
        })
        tableFragmentViewModel.reportLiveData.observe(this, Observer { attachments ->
            if (attachments != null && attachments.isNotEmpty()) {
                this.notifyReportImageId = attachments.last().id
                Glide.with(this)
                    .load("${ApiClient.getHost()}${TaskApi.ATTACHMENT_URL}${this.notifyReportImageId}")
                    .apply(RequestOptions().error(R.drawable.verify_code_default_pic))
                    .into(notifyReportImage)
            }
        })

        tableFragmentViewModel.sampleImageLiveData.observe(this, Observer { attachments ->
            if (attachments != null && attachments.isNotEmpty()) {
                this.sampleImageId = attachments.last().id
                Glide.with(this)
                    .load("${ApiClient.getHost()}${TaskApi.ATTACHMENT_URL}${this.sampleImageId}")
                    .apply(RequestOptions().error(R.drawable.verify_code_default_pic))
                    .into(sampleImage)
            }
        })
        tableFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                startLoading()
            } else {
                stopLoading()
            }
        })
        tableFragmentViewModel.responseLiveData.observe(this, Observer { response ->
            Timber.d("upload file result : ${response!!.msg}")
            Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show()
            if (response.code == 200) {
                activity?.finish()
            }
        })

        tableFragmentViewModel.sumbitResponseLiveData.observe(this, Observer { response ->
            Timber.d("upload file result : ${response!!.msg}")
            Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show()
            if (response.code == 200) {
                activity?.finish()
            }
        })

        tableFragmentViewModel.uploadImageResponseLiveData.observe(this, Observer {
            uploadDialog.cancel()
            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show()
            fetchAttachment()
        })
        tableFragmentViewModel.uploadVideoResponseLiveData.observe(this, Observer {
            uploadDialog.cancel()
            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show()
            fetchAttachment()
        })
        tableFragmentViewModel.generatePdfLiveData.observe(this, Observer { pdfObject ->
            taskItem.sampleReportAttachmentId = pdfObject?.get("sampleReportAttachmentId")?.toIntOrNull()
            val url = pdfObject?.get("url")
            if (TextUtils.isEmpty(url).not()) {
                pdfAttachments.clear()
                pdfAttachments.add(AttachmentItem(pdfObject?.get("id")?.toInt() ?: 0, ".pdf", url))
                pdfFrame.adapter.notifyDataSetChanged()
                startActivity(
                    Intent(context, PdfPreviewActivity::class.java)
                        .putExtra("url", "http://$url")
                )
            }
        })
        tableFragmentViewModel.pdfHistoryLiveData.observe(this, Observer { pdfObject ->
            val url = pdfObject?.get("url")
            if (TextUtils.isEmpty(url).not()) {
                pdfAttachments.clear()
                pdfAttachments.add(AttachmentItem(pdfObject?.get("id")?.toInt() ?: 0, ".pdf", url))
                pdfFrame.adapter.notifyDataSetChanged()
            }
        })
        tableFragmentViewModel.uploadErrorResponseLiveData.observe(this, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            uploadDialog.cancel()
        })
        fetchAttachment()

    }

    private fun renderImageFrame() {
        val imageAdapter = AttachmentAdapter(imageAttachments, 0)
        imageAdapter.setOnItemClickListener { adapter, _, position ->
           if (adapter.getItemViewType(position) == 1) {
               val intent = Intent(context, ImagePickActivity::class.java)
               intent.putExtra(IS_NEED_CAMERA, true)
               intent.putExtra(Constant.MAX_NUMBER, 7)
               startActivityForResult(intent, SELECT_PHOTO_REQUEST)
           } else {
               val item = adapter.data[position] as AttachmentItem
               startForResult(PhotoPreviewFragment.newInstance(item.id), 0x0001)
           }
        }
        imageFrame.layoutManager = GridLayoutManager(context, 8)
        imageFrame.addItemDecoration(ItemDecoration())
        imageFrame.layoutManager.isAutoMeasureEnabled = true
        imageFrame.adapter = imageAdapter
    }

    private fun renderVideoFrame() {
        val videoAdapter = AttachmentAdapter(videoAttachments, 1)
        videoAdapter.setOnItemClickListener { adapter, _, position ->
            if (adapter.getItemViewType(position) == 1) {
                val intent = Intent(context, VideoPickActivity::class.java)
                intent.putExtra(Constant.MAX_NUMBER, 1)
                startActivityForResult(intent, SELECT_VIDEO_REQUEST)
            } else {
                val item = adapter.data[position] as AttachmentItem
                startForResult(VideoFragment.newInstance(item.id), 0x0001)
            }
        }
        videoFrame.layoutManager = GridLayoutManager(context, 8)
        videoFrame.addItemDecoration(ItemDecoration())
        videoFrame.layoutManager.isAutoMeasureEnabled = true
        videoFrame.adapter = videoAdapter
    }

    private fun renderPdfFrame() {
        val pdfAdapter = AttachmentAdapter(pdfAttachments, 1)
        pdfAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.data[position] as AttachmentItem
            startActivity(
                Intent(context, PdfPreviewActivity::class.java)
                    .putExtra("url",item.url)
            )
        }
        pdfFrame.layoutManager = GridLayoutManager(context, 8)
        pdfFrame.addItemDecoration(ItemDecoration())
        pdfFrame.layoutManager.isAutoMeasureEnabled = true
        pdfFrame.adapter = pdfAdapter
    }

    override fun submitSuccessful() {
        activity?.finish()
    }

    override fun assembleSubmitTaskData() {
    }


}