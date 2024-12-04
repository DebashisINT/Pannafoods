package com.breezepannafoods.features.damageProduct.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.breezepannafoods.app.FileUtils
import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.NewQuotation.model.*
import com.breezepannafoods.features.addshop.model.AddShopRequestData
import com.breezepannafoods.features.addshop.model.AddShopResponse
import com.breezepannafoods.features.damageProduct.model.DamageProductResponseModel
import com.breezepannafoods.features.damageProduct.model.delBreakageReq
import com.breezepannafoods.features.damageProduct.model.viewAllBreakageReq
import com.breezepannafoods.features.login.model.userconfig.UserConfigResponseModel
import com.breezepannafoods.features.myjobs.model.WIPImageSubmit
import com.breezepannafoods.features.photoReg.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GetDamageProductListRegRepository(val apiService : GetDamageProductListApi) {

    fun viewBreakage(req: viewAllBreakageReq): Observable<DamageProductResponseModel> {
        return apiService.viewBreakage(req)
    }

    fun delBreakage(req: delBreakageReq): Observable<BaseResponse>{
        return apiService.BreakageDel(req.user_id!!,req.breakage_number!!,req.session_token!!)
    }

}