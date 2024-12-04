package com.breezepannafoods.features.performanceAPP

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.breezepannafoods.R
import com.breezepannafoods.base.presentation.BaseFragment
import com.breezepannafoods.features.contacts.TargetAcvhDtls
import com.breezepannafoods.features.contacts.TargetAcvhResponse

class TargetVSAchvDtlsFrag: BaseFragment(), View.OnClickListener {

    private lateinit var rvDtls:RecyclerView
    private lateinit var adapterDtls:AdapterTADtls

    private lateinit var tvTargetType:TextView
    private lateinit var tvTargetLevel:TextView
    private lateinit var tvTargetTimeFrame:TextView

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    companion object{
        var taDtls : ArrayList<TargetAcvhDtls> = ArrayList()
        var targetType :String = ""
        var targetLevel :String = ""
        var targetTime :String = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.frag_target_vs_achv_dtls, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        rvDtls = view.findViewById(R.id.rv_ta_dtls)
        tvTargetType = view.findViewById(R.id.tv_frag_ta_dtls_type)
        tvTargetLevel = view.findViewById(R.id.tv_frag_ta_dtls_level)
        tvTargetTimeFrame = view.findViewById(R.id.tv_frag_ta_dtls_time)

        adapterDtls = AdapterTADtls(mContext,taDtls)
        rvDtls.adapter = adapterDtls

        tvTargetType.text = targetType
        tvTargetLevel.text = targetLevel
        tvTargetTimeFrame.text = targetTime
    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }
}