package com.zhengdianfang.samplingpad.main.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_verify_result.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class VerifyResultFragment : BaseFragment() {

    private val isSuccessFul by lazy { arguments?.getBoolean("isSuccessFul")!! }
    private val message by lazy { arguments?.getString("message")!! }

    companion object {
        fun newInstance(isSuccessFul: Boolean, message: String): VerifyResultFragment {
            val fragment = VerifyResultFragment()
            val bundle = Bundle()
            bundle.putString("message", message)
            bundle.putBoolean("isSuccessFul", isSuccessFul)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_result, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        toolBarTitleView.text = "检验结果"
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        if (isSuccessFul) {
            resultTitleTextView.text = "校验通过"
            resultMarkImageView.setImageResource(R.drawable.ic_ok)
        } else {
            resultTitleTextView.text = "校验不通过"
            resultMarkImageView.setImageResource(R.drawable.ic_error)
        }

        resultMessageTextView.text = message
    }

}
