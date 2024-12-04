package com.breezepannafoods.features.login.api.opportunity

import com.breezepannafoods.app.Pref
import com.breezepannafoods.app.utils.AppUtils
import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.addshop.model.AudioFetchDataCLass
import com.breezepannafoods.features.addshop.model.LoanDetailFetchListsResponse
import com.breezepannafoods.features.addshop.model.LoanDispositionListsResponse
import com.breezepannafoods.features.addshop.model.LoanFinalStatusListsResponse
import com.breezepannafoods.features.addshop.model.LoanRiskTypeListsResponse
import com.breezepannafoods.features.addshop.model.StockAllResponse
import com.breezepannafoods.features.contacts.LoanDtlsResponse
import com.breezepannafoods.features.dashboard.presentation.DashboardActivity
import com.breezepannafoods.features.login.model.opportunitymodel.OpportunityStatusListResponseModel
import com.breezepannafoods.features.login.model.productlistmodel.ProductListResponseModel
import com.breezepannafoods.features.orderITC.SyncDeleteOppt
import com.breezepannafoods.features.orderITC.SyncEditOppt
import com.breezepannafoods.features.orderITC.SyncOppt
import com.breezepannafoods.features.orderList.model.OpportunityListResponseModel
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Saikat on 20-11-2018.
 */
class OpportunityListRepo(val apiService: OpportunityListApi) {
    fun getOpportunityStatus(session_token: String): Observable<OpportunityStatusListResponseModel> {
        return apiService.getOpportunityStatusList(session_token)
    }

    fun saveOpportunity(syncOppt: SyncOppt): Observable<BaseResponse> {
        return apiService.saveOpportunity(syncOppt)
    }

    fun editOpportunity(syncEditOppt: SyncEditOppt): Observable<BaseResponse> {
        return apiService.editOpportunity(syncEditOppt)
    }
    fun deleteOpportunity(syncDeleteOppt: SyncDeleteOppt): Observable<BaseResponse> {
        return apiService.deleteOpportunity(syncDeleteOppt)
    }
    fun getOpportunityL(user_id: String): Observable<OpportunityListResponseModel> {
        return apiService.getOpportunityL(user_id)
    }

    fun getAudioL(user_id: String,data_limit_in_days:String): Observable<AudioFetchDataCLass> {
        return apiService.getAudioLApi(user_id,data_limit_in_days)
    }


    fun getAllStock(user_id: String): Observable<StockAllResponse> {
        return apiService.getAllStockApi(user_id)
    }

    fun getLoanRiskTypeLists(user_id: String): Observable<LoanRiskTypeListsResponse> {
        return apiService.getLoanRiskTypeLists(user_id)
    }

    fun getLoanDispositionLists(user_id: String): Observable<LoanDispositionListsResponse> {
        return apiService.getLoanDispositionLists(user_id)
    }

    fun getLoanFinalStatusLists(user_id: String): Observable<LoanFinalStatusListsResponse> {
        return apiService.getLoanFinalStatusLists(user_id)
    }

    fun getLoanDetailFetch(user_id: String): Observable<LoanDetailFetchListsResponse> {
        return apiService.getLoanDetailFetch(user_id)
    }

    fun syncLoanDtls(obj: LoanDtlsResponse): Observable<BaseResponse> {
        return apiService.syncLoanDtlsApi(obj)
    }
}