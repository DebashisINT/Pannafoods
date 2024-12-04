package com.breezepannafoods.features.mylearning.apiCall

import com.breezepannafoods.app.NetworkConstant
import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.addshop.presentation.Crash_Report_Save
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LMSApi {

    @POST("LMSInfoDetails/UserWiseAPPCrashDetails")
    fun saveCrashReportToServer(@Body mCrash_Report_Save: Crash_Report_Save): Observable<BaseResponse>

    companion object Factory {
        fun create(): LMSApi {
            val retrofit = Retrofit.Builder()
                .client(NetworkConstant.setTimeOut())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NetworkConstant.BASE_URL)
                .build()

            return retrofit.create(LMSApi::class.java)
        }
    }
}