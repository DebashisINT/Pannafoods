package com.breezepannafoods.features.leaderboard.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.breezepannafoods.app.FileUtils
import com.breezepannafoods.app.Pref
import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.addshop.model.AddLogReqData
import com.breezepannafoods.features.addshop.model.AddShopRequestData
import com.breezepannafoods.features.addshop.model.AddShopResponse
import com.breezepannafoods.features.addshop.model.LogFileResponse
import com.breezepannafoods.features.addshop.model.UpdateAddrReq
import com.breezepannafoods.features.contacts.CallHisDtls
import com.breezepannafoods.features.contacts.CompanyReqData
import com.breezepannafoods.features.contacts.ContactMasterRes
import com.breezepannafoods.features.contacts.SourceMasterRes
import com.breezepannafoods.features.contacts.StageMasterRes
import com.breezepannafoods.features.contacts.StatusMasterRes
import com.breezepannafoods.features.contacts.TypeMasterRes
import com.breezepannafoods.features.dashboard.presentation.DashboardActivity
import com.breezepannafoods.features.login.model.WhatsappApiData
import com.breezepannafoods.features.login.model.WhatsappApiFetchData
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Puja on 10-10-2024.
 */
class LeaderboardRepo(val apiService: LeaderboardApi) {

    fun branchlist(session_token: String): Observable<LeaderboardBranchData> {
        return apiService.branchList(session_token)
    }
    fun ownDatalist(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOwnData> {
        return apiService.ownDatalist(user_id,activitybased,branchwise,flag)
    }
    fun overAllAPI(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOverAllData> {
        return apiService.overAllDatalist(user_id,activitybased,branchwise,flag)
    }
}