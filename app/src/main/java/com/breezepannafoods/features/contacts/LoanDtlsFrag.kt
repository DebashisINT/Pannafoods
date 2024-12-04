package com.breezepannafoods.features.contacts

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import com.breezepannafoods.DateProperty
import com.breezepannafoods.DialogLoading
import com.breezepannafoods.LoanDispositionEntity
import com.breezepannafoods.NumberToWords
import com.breezepannafoods.R
import com.breezepannafoods.app.AppDatabase
import com.breezepannafoods.app.NetworkConstant
import com.breezepannafoods.app.Pref
import com.breezepannafoods.app.domain.LoanDetailFetchEntity
import com.breezepannafoods.app.domain.LoanFinalStatusEntity
import com.breezepannafoods.app.domain.LoanRiskTypeEntity
import com.breezepannafoods.app.domain.OrderDetailsListEntity
import com.breezepannafoods.app.utils.AppUtils
import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.base.presentation.BaseActivity
import com.breezepannafoods.base.presentation.BaseFragment
import com.breezepannafoods.features.dashboard.presentation.DashboardActivity
import com.breezepannafoods.features.login.api.opportunity.OpportunityRepoProvider
import com.breezepannafoods.widgets.AppCustomTextView
import com.itextpdf.text.BadElementException
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class LoanDtlsFrag : BaseFragment(), View.OnClickListener {

    private lateinit var tvShopname:TextView

    private lateinit var tvBKT:TextView
    private lateinit var tvTotalOutstanding:TextView
    private lateinit var tvPOS:TextView
    private lateinit var tvEMIAmt:TextView
    private lateinit var tvAllCharges:TextView
    private lateinit var tvTotalCollectable:TextView

    private lateinit var tvRisk:TextView
    private lateinit var ivRisk:ImageView
    private lateinit var tvWorkable:TextView
    private lateinit var ivWorkable:ImageView
    private lateinit var tvDisposition:TextView
    private lateinit var ivDisposition:ImageView
    private lateinit var tvFinalStatus:TextView
    private lateinit var ivFinalStatus:ImageView
    private lateinit var tvPTPDate:TextView
    private lateinit var ivPTPDate:ImageView
    private lateinit var tvPTPAmt:TextView
    private lateinit var ivPTPAmt:ImageView
    private lateinit var tvCollDate:TextView
    private lateinit var ivCollDate:ImageView
    private lateinit var tvCollAmt:TextView
    private lateinit var ivCollAmt:ImageView
    private lateinit var share_icon: AppCompatImageView

    private lateinit var btnSubmit:Button

    private var selected_riskID:String=""
    private var selected_riskName:String=""
    private var selected_Workable:String=""
    private var selected_DispositionID:String=""
    private var selected_DispositionName:String=""
    private var selected_FinalStatusID:String=""
    private var selected_FinalStatusName:String=""
    private var selected_PTPDate:String=""
    private var selected_PTPAmt:String=""
    private var selected_CollectionDate:String=""
    private var selected_CollectionAmt:String=""

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    companion object{
        var shop_id:String=""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.frag_loan_dtls, container, false)
        initView(view)
        return view
    }
    @SuppressLint("RestrictedApi")
    private fun initView(view: View) {
        tvShopname = view.findViewById(R.id.tv_loan_dtls_shop_name)

        btnSubmit = view.findViewById(R.id.btn_submit_loan)

        tvBKT = view.findViewById(R.id.tv_row_contact_bkt)
        tvTotalOutstanding = view.findViewById(R.id.tv_row_contact_total_outstanding)
        tvPOS = view.findViewById(R.id.tv_row_contact_pos)
        tvEMIAmt = view.findViewById(R.id.tv_row_contact_emi_amt)
        tvAllCharges = view.findViewById(R.id.tv_row_contact_all_charges)
        tvTotalCollectable = view.findViewById(R.id.tv_row_contact_total_collectable)

        tvRisk = view.findViewById(R.id.tv_row_contact_risk)
        ivRisk = view.findViewById(R.id.iv_row_contact_risk)
        tvWorkable = view.findViewById(R.id.tv_row_contact_workable)
        ivWorkable = view.findViewById(R.id.iv_row_contact_workable)
        tvDisposition = view.findViewById(R.id.tv_row_contact_disp_code)
        ivDisposition = view.findViewById(R.id.iv_row_contact_disp_code)
        tvFinalStatus = view.findViewById(R.id.tv_row_contact_final_status)
        ivFinalStatus = view.findViewById(R.id.iv_row_contact_final_status)
        tvPTPDate = view.findViewById(R.id.tv_ptp_date)
        ivPTPDate = view.findViewById(R.id.iv_ptp_date)
        tvPTPAmt = view.findViewById(R.id.tv_ptp_amt)
        ivPTPAmt = view.findViewById(R.id.iv_ptp_amt)
        tvCollDate = view.findViewById(R.id.tv_loan_dtls_collection_date)
        ivCollDate = view.findViewById(R.id.iv_loan_dtls_collection_date)
        tvCollAmt = view.findViewById(R.id.tv_loan_dtls_collection_amt)
        ivCollAmt = view.findViewById(R.id.iv_loan_dtls_collection_amt)
        share_icon = view.findViewById(R.id.share_icon)

        ivRisk.setOnClickListener(this)
        ivWorkable.setOnClickListener(this)
        ivDisposition.setOnClickListener(this)
        ivFinalStatus.setOnClickListener(this)
        ivPTPDate.setOnClickListener(this)
        ivPTPAmt.setOnClickListener(this)
        ivCollDate.setOnClickListener(this)
        ivCollAmt.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        share_icon.setOnClickListener(this)

        updateView()

    }

    private fun updateView(){
        try {
            var shopObj = AppDatabase.getDBInstance()!!.addShopEntryDao().getShopByIdN(shop_id)
            tvShopname.text = "${Pref.shopText} name :"+shopObj.shopName
            var objDtls = AppDatabase.getDBInstance()!!.loanDetailFetchDao().getByShopID(shop_id)
            tvBKT.text = objDtls.bkt
            tvTotalOutstanding.text = objDtls.total_outstanding
            tvPOS.text = objDtls.pos
            tvEMIAmt.text = objDtls.emi_amt
            tvAllCharges.text = objDtls.all_charges
            tvTotalCollectable.text = objDtls.total_Collectable

            tvRisk.text = objDtls.risk_name
            tvWorkable.text = objDtls.workable
            tvDisposition.text = objDtls.disposition_code_name
            tvFinalStatus.text = objDtls.final_status_name
            tvPTPDate.text = objDtls.ptp_Date
            tvPTPAmt.text = objDtls.ptp_amt
            tvCollDate.text = objDtls.collection_date
            tvCollAmt.text = objDtls.collection_amount
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            ivRisk.id ->{
                try {
                    var riskL = AppDatabase.getDBInstance()?.loanRiskTypeDao()?.getAll() as ArrayList<LoanRiskTypeEntity>
                    if (riskL.size > 0) {
                        var genericL: ArrayList<CustomData> = ArrayList()
                        for (i in 0..riskL.size - 1) {
                            genericL.add(
                                CustomData(
                                    riskL[i].id.toString(),
                                    riskL[i].name
                                )
                            )
                        }
                        GenericDialog.newInstance(
                            "Risk Type",
                            genericL as ArrayList<CustomData>
                        ) {
                            selected_riskID = it.id
                            selected_riskName = it.name

                            tvRisk.text = selected_riskName

                        }.show((mContext as DashboardActivity).supportFragmentManager, "")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            ivWorkable.id->{
                try {
                    var genericL: ArrayList<CustomData> = ArrayList()
                    genericL.add(CustomData("1", "Yes"))
                    genericL.add(CustomData("2", "No"))
                    GenericDialog.newInstance(
                        "Workable Type",
                        genericL as ArrayList<CustomData>
                    ) {
                        selected_Workable = it.name

                       tvWorkable.text = selected_Workable

                    }.show((mContext as DashboardActivity).supportFragmentManager, "")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            ivDisposition.id->{
                try {
                    var dispL = AppDatabase.getDBInstance()?.loanDispositionDao()
                        ?.getAll() as ArrayList<LoanDispositionEntity>
                    if (dispL.size > 0) {
                        var genericL: ArrayList<CustomData> = ArrayList()
                        for (i in 0..dispL.size - 1) {
                            genericL.add(
                                CustomData(
                                    dispL[i].id.toString(),
                                    dispL[i].name
                                )
                            )
                        }
                        GenericDialog.newInstance("Disposition Type", genericL as ArrayList<CustomData>) {
                            selected_DispositionID = it.id
                            selected_DispositionName = it.name

                            tvDisposition.text = selected_DispositionName

                        }.show(
                            (mContext as DashboardActivity).supportFragmentManager,
                            ""
                        )

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            ivFinalStatus.id->{
                try {
                    var existingID = "0"
                    try {
                        existingID = AppDatabase.getDBInstance()?.loanDetailFetchDao()?.getByShopID(shop_id)!!.final_status_id.toString()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    var finalStatusL = AppDatabase.getDBInstance()?.loanFinalStatusDao()?.getAllFilter(existingID.toInt()) as ArrayList<LoanFinalStatusEntity>
                    if (finalStatusL.size > 0) {
                        var genericL: ArrayList<CustomData> = ArrayList()
                        for (i in 0..finalStatusL.size - 1) {
                            genericL.add(
                                CustomData(
                                    finalStatusL[i].id.toString(),
                                    finalStatusL[i].name
                                )
                            )
                        }
                        GenericDialog.newInstance("Final Status", genericL as ArrayList<CustomData>) {
                            selected_FinalStatusID = it.id
                            selected_FinalStatusName = it.name
                            tvFinalStatus.text = selected_FinalStatusName
                        }.show((mContext as DashboardActivity).supportFragmentManager, "")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            ivPTPDate.id->{
                DateProperty.showDatePickerDialog(requireActivity().supportFragmentManager) { selectedDate ->
                    selected_PTPDate = selectedDate

                    tvPTPDate.text = selected_PTPDate

                }
            }
            ivPTPAmt.id->{
                val simpleDialog = Dialog(mContext)
                simpleDialog.setCancelable(true)
                simpleDialog.getWindow()!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                simpleDialog.setContentView(R.layout.dialog_ptp_amt)
                val tv_header: AppCustomTextView =
                    simpleDialog.findViewById(R.id.dialog_ptp_header_tv) as AppCustomTextView
                val et_value: EditText =
                    simpleDialog.findViewById(R.id.dialog_et_value) as EditText
                val submit =
                    simpleDialog.findViewById(R.id.tv_dialog_submit) as AppCustomTextView
                tv_header.text = "Enter PTP Amount"
                submit.setOnClickListener({ view ->
                    simpleDialog.dismiss()
                    selected_PTPAmt = et_value.text.toString()

                    tvPTPAmt.text = selected_PTPAmt

                })

                simpleDialog.show()
            }
            ivCollDate.id->{
                DateProperty.showDatePickerDialog(requireActivity().supportFragmentManager) { selectedDate ->
                    selected_CollectionDate = selectedDate

                    tvCollDate.text = selected_CollectionDate

                }
            }
            ivCollAmt.id->{
                val simpleDialog = Dialog(mContext)
                simpleDialog.setCancelable(true)
                simpleDialog.getWindow()!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                simpleDialog.setContentView(R.layout.dialog_ptp_amt)
                val tv_header: AppCustomTextView =
                    simpleDialog.findViewById(R.id.dialog_ptp_header_tv) as AppCustomTextView
                val et_value: EditText =
                    simpleDialog.findViewById(R.id.dialog_et_value) as EditText
                val submit =
                    simpleDialog.findViewById(R.id.tv_dialog_submit) as AppCustomTextView
                tv_header.text = "Enter Collection Amount"
                submit.setOnClickListener({ view ->
                    simpleDialog.dismiss()
                    selected_CollectionAmt = et_value.text.toString()
                    tvCollAmt.text = selected_CollectionAmt
                })

                simpleDialog.show()
            }

            btnSubmit.id->{
                updateDB()
            }

            share_icon.id->{
                sharePDF()
            }
        }
    }

    private fun sharePDF() {
        var shopObj = AppDatabase.getDBInstance()!!.addShopEntryDao().getShopByIdN(shop_id)
        //tvShopname.text = "${Pref.shopText} name :"+shopObj.shopName
        var objDtls = AppDatabase.getDBInstance()!!.loanDetailFetchDao().getByShopID(shop_id)

        var document: Document = Document()
            var fileName = "FTS"+ "_" + objDtls.shop_id
            fileName = fileName.replace("/", "_")

            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +"/breezepannafoodsApp/ORDERDETALIS/"

            var pathNew = ""

            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }

            try {
                try {
                    PdfWriter.getInstance(document, FileOutputStream(path + fileName + ".pdf"))
                } catch (ex: Exception) {
                    ex.printStackTrace()

                    pathNew = mContext.filesDir.toString() + "/" + fileName + ".pdf"
                    PdfWriter.getInstance(document, FileOutputStream(pathNew))
                }

                document.open()

                var font: Font = Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD)
                var fontBoldU: Font =
                    Font(Font.FontFamily.HELVETICA, 12f, Font.UNDERLINE or Font.BOLD)
                var font1: Font = Font(Font.FontFamily.HELVETICA, 8f, Font.NORMAL)

                val bm: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.breezelogo)
                val bitmap = Bitmap.createScaledBitmap(bm, 80, 80, true);
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(110f, 110f)
                    img.scalePercent(70f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val Heading = Paragraph("LOAN DETAILS ", fontBoldU)
                Heading.alignment = Element.ALIGN_CENTER
                Heading.spacingAfter = 2f

                val sp = Paragraph("", font)
                sp.spacingAfter = 50f
                document.add(sp)

                val h = Paragraph("LOAN DETAILS ", fontBoldU)
                h.alignment = Element.ALIGN_CENTER

                val pHead = Paragraph()
                pHead.add(Chunk(img, 0f, -30f))
                pHead.add(h)
                document.add(pHead)

                val x = Paragraph("", font)
                x.spacingAfter = 20f
                document.add(x)

                val xz = Paragraph("", font)
                xz.spacingAfter = 10f
                document.add(xz)


                val Parties = Paragraph("Retailer name               :      " + shopObj!!.shopName, font1)
                Parties.alignment = Element.ALIGN_LEFT
                Parties.spacingAfter = 2f
                document.add(Parties)

                val address = Paragraph("BKT                              :      " + objDtls!!.bkt, font1)
                address.alignment = Element.ALIGN_LEFT
                address.spacingAfter = 2f
                document.add(address)

                val totalOutstanding = Paragraph("Total Outstanding         :      " + objDtls!!.total_outstanding, font1)
                totalOutstanding.alignment = Element.ALIGN_LEFT
                address.spacingAfter = 2f
                document.add(totalOutstanding)


                val Contact = Paragraph("POS                              :      " + objDtls!!.pos, font1)
                Contact.alignment = Element.ALIGN_LEFT
                Contact.spacingAfter = 2f
                document.add(Contact)

                val EMIamnt = Paragraph("EMI Amount                  :      " + objDtls!!.emi_amt, font1)
                EMIamnt.alignment = Element.ALIGN_LEFT
                EMIamnt.spacingAfter = 2f
                document.add(EMIamnt)


                val PanNo = Paragraph("All Charge                     :      " + objDtls!!.all_charges, font1)
                PanNo.alignment = Element.ALIGN_LEFT
                PanNo.spacingAfter = 2f
                document.add(PanNo)

                val TotalCollectable = Paragraph("Total Collectable           :      " + objDtls!!.total_Collectable, font1)
                TotalCollectable.alignment = Element.ALIGN_LEFT
                TotalCollectable.spacingAfter = 2f
                document.add(TotalCollectable)

                val GSTNNo = Paragraph("Risk                               :      " + objDtls.risk_name, font1)
                GSTNNo.alignment = Element.ALIGN_LEFT
                GSTNNo.spacingAfter = 2f
                document.add(GSTNNo)


                    val Workable = Paragraph(
                        "Workable                       :      " + objDtls.workable, font1)
                    Workable.alignment = Element.ALIGN_LEFT
                    Workable.spacingAfter = 2f
                    document.add(Workable)


                    val PatientName = Paragraph(
                        "Disposition Code           :      " + objDtls.disposition_code_name,
                        font1)
                    PatientName.alignment = Element.ALIGN_LEFT
                    PatientName.spacingAfter = 2f
                    document.add(PatientName)

                    val FinalStatus = Paragraph(
                        "Final Status                   :      " + objDtls.final_status_name,
                        font1)
                    FinalStatus.alignment = Element.ALIGN_LEFT
                    FinalStatus.spacingAfter = 2f
                    document.add(FinalStatus)

                    val PatientAddr = Paragraph("PTP Date                      :      " + objDtls.ptp_Date, font1)
                    PatientAddr.alignment = Element.ALIGN_LEFT
                    PatientAddr.spacingAfter = 2f
                    document.add(PatientAddr)

                    val PatientPhone = Paragraph("PTP Amount                 :      " + objDtls.ptp_amt, font1)
                    PatientPhone.alignment = Element.ALIGN_LEFT
                    PatientPhone.spacingAfter = 2f
                    document.add(PatientPhone)

                    val Collectiondt =
                        Paragraph("Collection Date             :      " + objDtls.collection_date, font1)
                    Collectiondt.alignment = Element.ALIGN_LEFT
                    Collectiondt.spacingAfter = 2f
                    document.add(Collectiondt)

                    val Collectionamnt =
                        Paragraph("Collection Amount         :      " + objDtls.collection_amount, font1)
                    Collectionamnt.alignment = Element.ALIGN_LEFT
                    Collectionamnt.spacingAfter = 10f
                    document.add(Collectionamnt)

                    val xze = Paragraph("", font)
                    xze.spacingAfter = 10f
                    document.add(xze)

                    document.add(Paragraph())

                    document.close()

                    var sendingPath = path + fileName + ".pdf"
                    if (!pathNew.equals("")) {
                        sendingPath = pathNew
                    }
                    if (!TextUtils.isEmpty(sendingPath)) {
                        try {
                            val shareIntent = Intent(Intent.ACTION_SEND)
                            val fileUrl = Uri.parse(sendingPath)
                            val file = File(fileUrl.path)
                            val uri: Uri = FileProvider.getUriForFile(
                                mContext,
                                mContext.applicationContext.packageName.toString() + ".provider",
                                file
                            )
                            shareIntent.type = "image/png"
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                            startActivity(Intent.createChooser(shareIntent, "Share pdf using"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong1))
                        }
                    }
            }
            catch(ex: Exception) {
                        ex.printStackTrace()
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    }

    }

    private fun updateDB(){
        DialogLoading.show(requireActivity().supportFragmentManager, "")
        doAsync {
            var isPresent = AppDatabase.getDBInstance()!!.loanDetailFetchDao().getByShopID(shop_id)
            if(isPresent == null){
                var ob : LoanDetailFetchEntity = LoanDetailFetchEntity()
                ob.shop_id = shop_id
                ob.isUploaded = true
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.insert(ob)
            }

            if(!selected_riskID.equals("") && !selected_riskName.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updateRiskByShopID(shop_id, selected_riskID, selected_riskName, false)
            }
            if(!selected_Workable.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updateWorkableByShopID(shop_id, selected_Workable, false)
            }
            if(!selected_DispositionID.equals("") && !selected_DispositionName.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updateDispByShopID(shop_id, selected_DispositionID, selected_DispositionName, false)
            }
            if(!selected_FinalStatusID.equals("") && !selected_FinalStatusName.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updateFinalStatusByShopID(shop_id, selected_FinalStatusID, selected_FinalStatusName, false)
            }
            if(!selected_PTPDate.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updatePTPDateShopID(shop_id, selected_PTPDate, false)
            }
            if(!selected_PTPAmt.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updatePTPAmtShopID(shop_id, selected_PTPAmt, false)
            }

            if(!selected_CollectionDate.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updateCollectionDateShopID(shop_id, selected_CollectionDate, false)
            }
            if(!selected_CollectionAmt.equals("")){
                AppDatabase.getDBInstance()?.loanDetailFetchDao()?.updateCollectionAmtShopID(shop_id, selected_CollectionAmt, false)
            }
            uiThread {
                DialogLoading.dismiss()
                loanDetailsUpdate()
            }
        }



    }

    private fun loanDetailsUpdate() {
        try {
            if(AppUtils.isOnline(mContext)){
                var loanDtlsSyncL = AppDatabase.getDBInstance()?.loanDetailFetchDao()
                    ?.getForUpload(false) as ArrayList<LoanDetailFetchEntity>
                if (loanDtlsSyncL.size > 0) {
                    DialogLoading.show(requireActivity().supportFragmentManager, "")
                    var syncObj = loanDtlsSyncL.get(0)
                    var obj: LoanDtlsResponse = LoanDtlsResponse()
                    obj.apply {
                        user_id = Pref.user_id.toString()
                        shop_id = syncObj.shop_id
                        risk_id = syncObj.risk_id
                        risk_name = syncObj.risk_name
                        workable = syncObj.workable
                        disposition_code_id = if(syncObj.disposition_code_id.equals("")) "0" else syncObj.disposition_code_id.toString()
                        disposition_code_name = syncObj.disposition_code_name
                        ptp_Date = syncObj.ptp_Date
                        ptp_amt = if(syncObj.ptp_amt.equals("")) "0" else syncObj.ptp_amt.toString()
                        collection_date = syncObj.collection_date
                        collection_amount = if(syncObj.collection_amount.equals("")) "0" else syncObj.collection_amount.toString()
                        final_status_id =if(syncObj.final_status_id.equals("")) "0" else syncObj.final_status_id.toString()
                        final_status_name = syncObj.final_status_name
                    }

                    val repository = OpportunityRepoProvider.opportunityListRepo()
                    BaseActivity.compositeDisposable.add(
                        repository.syncLoanDtls(obj)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->
                                val response = result as BaseResponse
                                if (response.status == NetworkConstant.SUCCESS) {
                                    doAsync {
                                        AppDatabase.getDBInstance()!!.loanDetailFetchDao().updateIsUploaded(true,obj.shop_id)
                                        uiThread {
                                            DialogLoading.dismiss()
                                            loanDetailsUpdate()
                                        }
                                    }
                                }
                            }, { error ->
                                error.printStackTrace()
                                DialogLoading.dismiss()
                            })
                    )
                }else{
                    showSuccessDialog("Saved ssuccessfully")
                }
            }else{
                showSuccessDialog("Saved ssuccessfully")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showSuccessDialog( msgBody:String){
        val simpleDialog = Dialog(mContext)
        simpleDialog.setCancelable(false)
        simpleDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        simpleDialog.setContentView(R.layout.dialog_message)
        val dialogHeader = simpleDialog.findViewById(R.id.dialog_message_header_TV) as AppCustomTextView
        val dialog_yes_no_headerTV = simpleDialog.findViewById(R.id.dialog_message_headerTV) as AppCustomTextView
        dialog_yes_no_headerTV.text = AppUtils.hiFirstNameText()+"!"
        dialogHeader.text = msgBody
        val dialogYes = simpleDialog.findViewById(R.id.tv_message_ok) as AppCustomTextView
        dialogYes.setOnClickListener({ view ->
            simpleDialog.cancel()
            Handler().postDelayed(Runnable {
                (mContext as DashboardActivity).onBackPressed()
            }, 500)
        })
        simpleDialog.show()
    }

}