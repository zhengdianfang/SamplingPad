package com.zhengdianfang.samplingpad.common


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.normal_product.fragments.FourthTableFragment
import kotlinx.android.synthetic.main.fragment_qrcode.*

class QRCodeFragment : BaseFragment() {

    companion object {
        fun newInstance(): QRCodeFragment {
            val fragment = QRCodeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qrcode, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        zxingView.setDelegate(object : QRCodeView.Delegate {
            override fun onScanQRCodeSuccess(result: String?) {
                if (TextUtils.isEmpty(result).not()) {
                    val bundle = Bundle()
                    bundle.putString("result", result)
                    setFragmentResult(FourthTableFragment.QR_SCAN_REQUEST, bundle)
                }
            }

            override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
            }

            override fun onScanQRCodeOpenCameraError() {
            }
        })
        cancelButton.setOnClickListener {
            pop()
        }

    }
}
