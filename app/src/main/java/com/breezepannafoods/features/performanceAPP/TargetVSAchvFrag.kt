package com.breezepannafoods.features.performanceAPP

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.breezepannafoods.DateProperty
import com.breezepannafoods.DialogLoading
import com.breezepannafoods.R
import com.breezepannafoods.app.AppDatabase
import com.breezepannafoods.app.NetworkConstant
import com.breezepannafoods.app.Pref
import com.breezepannafoods.app.types.FragType
import com.breezepannafoods.app.utils.AppUtils
import com.breezepannafoods.app.utils.Toaster
import com.breezepannafoods.base.presentation.BaseActivity
import com.breezepannafoods.base.presentation.BaseFragment
import com.breezepannafoods.features.contacts.CustomData
import com.breezepannafoods.features.contacts.DateRangeResponse
import com.breezepannafoods.features.contacts.GenericDialog
import com.breezepannafoods.features.contacts.TargetAcvhDtls
import com.breezepannafoods.features.contacts.TargetAcvhParam
import com.breezepannafoods.features.contacts.TargetAcvhResponse
import com.breezepannafoods.features.contacts.TargetLevelResponse
import com.breezepannafoods.features.contacts.TargetTypeDtls
import com.breezepannafoods.features.contacts.TargetTypeResponse
import com.breezepannafoods.features.contacts.TypeMasterRes
import com.breezepannafoods.features.dashboard.presentation.DashboardActivity
import com.breezepannafoods.features.performanceAPP.model.ChartDataModelNew
import com.breezepannafoods.features.shopdetail.presentation.api.EditShopRepoProvider
import com.bumptech.glide.Glide
import com.ekn.gruzer.gaugelibrary.HalfGauge
import com.ekn.gruzer.gaugelibrary.Range
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TargetVSAchvFrag: BaseFragment(), View.OnClickListener  {

    private lateinit var ivTargetPointer : ImageView
    private lateinit var ivAchvPointer : ImageView
    private lateinit var llTargetType : LinearLayout
    private lateinit var llTargetLevel : LinearLayout
    private lateinit var tvTargetType : TextView
    private lateinit var tvTargetLevel : TextView

    private lateinit var rgTimeFrame:RadioGroup
    private lateinit var rbTimeFrame_monthly:RadioButton
    private lateinit var rbTimeFrame_yearly:RadioButton
    private lateinit var rbTimeFrame_custom:RadioButton

    private lateinit var ll_frag_ta_dtls:LinearLayout

    private lateinit var halfGauge:HalfGauge

    private lateinit var targetTypeL :ArrayList<TargetTypeDtls>
    private lateinit var targetLevelL :ArrayList<TargetTypeDtls>
    private lateinit var dateRangeL :ArrayList<TargetTypeDtls>

    private var selectedTargetType :String = ""
    private var selectedTargetLevel :String = ""
    private var selectedTimeFrame :String = ""
    private var selectedStartDate :String = ""
    private var selectedEndDate :String = ""

    private lateinit var tvTarget:TextView
    private lateinit var tvAchv:TextView

    private lateinit var targetAcvhResponse :TargetAcvhResponse
    var taDtls : ArrayList<TargetAcvhDtls> = ArrayList()

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    companion object{
        var userID:String = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.frag_target_vs_achv, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        halfGauge = view.findViewById<HalfGauge>(R.id.half_gauge)

        ll_frag_ta_dtls = view.findViewById(R.id.ll_frag_ta_dtls)

        ivTargetPointer = view.findViewById(R.id.iv_frag_ta_target_pointer)
        ivAchvPointer = view.findViewById(R.id.iv_frag_ta_achv_pointer)
        llTargetType = view.findViewById(R.id.ll_frag_ta_target_type)
        llTargetLevel = view.findViewById(R.id.ll_frag_ta_target_level)
        tvTargetType = view.findViewById(R.id.tv_frag_ta_target_type)
        tvTargetLevel = view.findViewById(R.id.tv_frag_ta_target_level)

        rgTimeFrame = view.findViewById(R.id.rg_frag_ta_time_frame)
        rbTimeFrame_monthly = view.findViewById(R.id.rb_frag_ta_time_frame_monthly)
        rbTimeFrame_yearly = view.findViewById(R.id.rb_frag_ta_time_frame_yearly)
        rbTimeFrame_custom = view.findViewById(R.id.rb_frag_ta_time_frame_custom)

        tvTarget = view.findViewById(R.id.tv_frag_ta_target)
        tvAchv = view.findViewById(R.id.tv_frag_ta_achv)

        rgTimeFrame.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                rbTimeFrame_monthly.id -> {
                    selectedTimeFrame = "M"
                    callDtlsApi()
                }
                rbTimeFrame_yearly.id -> {
                    selectedTimeFrame = "Y"
                    callDtlsApi()
                }
                rbTimeFrame_custom.id -> {
                    selectedTimeFrame = "C"
                    DateProperty.showDateRangePickerDialog((mContext as DashboardActivity).supportFragmentManager){ startDate, endDate ->
                        val date = AppUtils.getFormatedDM(startDate).toString() + " To\n" + AppUtils.getFormatedDM(endDate).toString()
                        rbTimeFrame_custom.text = date
                        selectedStartDate = startDate
                        selectedEndDate = endDate
                        callDtlsApi()
                    }
                }
            }
        }

        llTargetType.setOnClickListener(this)
        llTargetLevel.setOnClickListener(this)
        ll_frag_ta_dtls.setOnClickListener(this)

        Glide.with(mContext)
            .load(R.drawable.icon_pointer_gif)
            .into(ivTargetPointer)

        Glide.with(mContext)
            .load(R.drawable.icon_pointer_gif)
            .into(ivAchvPointer)



        callTargetType()
    }

    private fun callTargetType(){
        DialogLoading.show(requireActivity().supportFragmentManager, "")
        val repository = EditShopRepoProvider.provideEditShopWithoutImageRepository()
        BaseActivity.compositeDisposable.add(
            repository.targetType(Pref.user_id.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val resp = result as TargetTypeResponse
                    if(resp.status == NetworkConstant.SUCCESS){
                        targetTypeL = resp.target_type_list
                        callTargetLevel()
                    }else{
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))
                        DialogLoading.dismiss()
                    }
                }, { error ->
                    error.printStackTrace()
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    DialogLoading.dismiss()
                })
        )
    }

    private fun callTargetLevel(){
        val repository = EditShopRepoProvider.provideEditShopWithoutImageRepository()
        BaseActivity.compositeDisposable.add(
            repository.targetLevel(Pref.user_id.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val resp = result as TargetLevelResponse
                    if(resp.status == NetworkConstant.SUCCESS){
                        targetLevelL = resp.target_level_list
                        callDateRange()
                    }else{
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))
                        DialogLoading.dismiss()
                    }
                }, { error ->
                    error.printStackTrace()
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    DialogLoading.dismiss()
                })
        )
    }

    private fun callDateRange(){
        val repository = EditShopRepoProvider.provideEditShopWithoutImageRepository()
        BaseActivity.compositeDisposable.add(
            repository.dateRange(Pref.user_id.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val resp = result as DateRangeResponse
                    if(resp.status == NetworkConstant.SUCCESS){
                        dateRangeL = resp.target_time_list
                        rbTimeFrame_monthly.text = dateRangeL.get(0).name
                        rbTimeFrame_yearly.text = dateRangeL.get(1).name
                        rbTimeFrame_custom.text = dateRangeL.get(2).name
                        DialogLoading.dismiss()
                    }else{
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))
                        DialogLoading.dismiss()
                    }
                }, { error ->
                    error.printStackTrace()
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    DialogLoading.dismiss()
                })
        )
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ll_frag_ta_dtls->{
                if(taDtls.size>0){
                    try {
                        TargetVSAchvDtlsFrag.taDtls = taDtls
                        TargetVSAchvDtlsFrag.targetType = tvTargetType.text.toString()
                        TargetVSAchvDtlsFrag.targetLevel = tvTargetLevel.text.toString()
                        if(selectedTimeFrame.equals("M")){
                            TargetVSAchvDtlsFrag.targetTime = "Monthly"
                        }else if(selectedTimeFrame.equals("Y")){
                            TargetVSAchvDtlsFrag.targetTime = "Yearly"
                        }else if(selectedTimeFrame.equals("C")){
                            val date = AppUtils.getFormatedDM(selectedStartDate).toString() + " To\n" + AppUtils.getFormatedDM(selectedEndDate).toString()
                            TargetVSAchvDtlsFrag.targetTime = date
                        }

                        (mContext as DashboardActivity).loadFragment(FragType.TargetVSAchvDtlsFrag, true, "")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            llTargetType.id ->{
                if(targetTypeL.size>0){
                    rgTimeFrame.clearCheck()
                    var genericL:ArrayList<CustomData> = ArrayList()
                    for(i in 0..targetTypeL.size-1){
                        genericL.add(CustomData(targetTypeL[i].id.toString(),targetTypeL[i].name))
                    }

                    GenericDialog.newInstance("Target Type",genericL as ArrayList<CustomData>){
                        tvTargetType.text = it.name
                        selectedTargetType = it.id.toString()
                    }.show((mContext as DashboardActivity).supportFragmentManager, "")
                }else{
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))
                }
            }
            llTargetLevel.id ->{
                if(targetLevelL.size>0){
                    rgTimeFrame.clearCheck()
                    var genericL:ArrayList<CustomData> = ArrayList()
                    for(i in 0..targetLevelL.size-1){
                        genericL.add(CustomData(targetLevelL[i].id.toString(),targetLevelL[i].name))
                    }

                    GenericDialog.newInstance("Target Level",genericL as ArrayList<CustomData>){
                        tvTargetLevel.text = it.name
                        selectedTargetLevel = it.id.toString()
                    }.show((mContext as DashboardActivity).supportFragmentManager, "")
                }else{
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))
                }
            }
        }
    }

    private fun callDtlsApi(){
        var obj : TargetAcvhParam = TargetAcvhParam()
        obj.user_id = userID
        obj.target_type_id = selectedTargetType
        obj.target_level_id = selectedTargetLevel
        obj.time_frame = selectedTimeFrame
        if(selectedTimeFrame.equals("C")){
            obj.start_date = selectedStartDate
            obj.end_date = selectedEndDate
        }
        DialogLoading.show(requireActivity().supportFragmentManager, "")
        val repository = EditShopRepoProvider.provideEditShopWithoutImageRepository()
        BaseActivity.compositeDisposable.add(
            repository.targetAchvDtls(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val resp = result as TargetAcvhResponse
                    if(resp.status == NetworkConstant.SUCCESS){
                        DialogLoading.dismiss()
                        targetAcvhResponse = resp
                        tvTarget.text = resp.target.toString()
                        tvAchv.text = resp.achv.toString()
                        taDtls = ArrayList()
                        taDtls = resp.achv_list
                        plotGraph()
                    }else{
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))
                        DialogLoading.dismiss()
                    }
                }, { error ->
                    error.printStackTrace()
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    DialogLoading.dismiss()
                })
        )
    }

    private fun plotGraph(){
        halfGauge.padding = 5f
        val range = Range()
        range.color = Color.parseColor("#10AD65") // green
        range.from =0.0
        range.to = targetAcvhResponse.achv.toDouble()

        val range2 = Range()
        range2.color = Color.parseColor("#E76B1F")
        range2.from = targetAcvhResponse.achv.toDouble()
        range2.to = targetAcvhResponse.target.toDouble()

        halfGauge.addRange(range)
        halfGauge.addRange(range2)

        //set min max and current value
        //set min max and current value
        halfGauge.minValue = 0.0
        halfGauge.maxValue = targetAcvhResponse.target.toDouble()
        halfGauge.value = targetAcvhResponse.achv.toDouble()

        halfGauge.setNeedleColor(Color.parseColor("#5E5E5E"))
        halfGauge.valueColor = Color.DKGRAY
        halfGauge.minValueTextColor = Color.BLACK
        halfGauge.maxValueTextColor = Color.BLACK

        halfGauge.isEnableNeedleShadow = true
    }

}