package com.zhengdianfang.samplingpad.common


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.github.barteksc.pdfviewer.util.FileUtils
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.normal_product.fragments.TableFragmentViewModel
import kotlinx.android.synthetic.main.fragment_video.*
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File


class VideoFragment : BaseFragment() {

    companion object {
        fun newInstance(id: Int): VideoFragment {
            val fragment = VideoFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var localFile: File? = null

    private val attachmentId  by lazy { arguments?.getInt("id")!! }
    private val tableFragmentViewModel by lazy { ViewModelProviders.of(this).get(TableFragmentViewModel::class.java) }
    private val configDialog by lazy {
        MaterialDialog.Builder(context!!)
            .content("确定删除此视频吗？")
            .positiveText("确定")
            .onPositive { _, _ ->
                tableFragmentViewModel.deleteAttachments("$attachmentId")
            }
            .negativeText("取消")
            .build()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        deleteButton.setOnClickListener {
            configDialog.show()
        }

        tableFragmentViewModel.deleteAttachmentLiveData.observe(this, Observer {
            if (it == true) {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
                pop()
            }
        })
        downloadVideo()
    }

    private fun downloadVideo() {
        val localPdfDir = File("${Environment.getExternalStorageDirectory().absolutePath}/sampling/video/")
        if (localPdfDir.exists().not()) {
            localPdfDir.mkdirs()
        }
        localFile = File(localPdfDir, "$attachmentId.mp4")
        if (localFile!!.exists().not()) {
            startLoading()
            doAsync {
                val request = Request.Builder().url("${ApiClient.HOST}${TaskApi.ATTACHMENT_URL}$attachmentId").build()
                val response = ApiClient.getHttpClient().newCall(request).execute()
                val byteStream = response.body()?.byteStream()
                FileUtils.copy(byteStream, localFile)
                uiThread {
                    stopLoading()
                    if (localFile!!.exists()) {
                        play()
                    }
                }
            }
        } else {
            play()
        }
    }

    private fun play() {
        val mediaController = MediaController(context)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.parse("file://${localFile!!.absolutePath}"))
        videoView.start()
        videoView.requestFocus()
    }

}
