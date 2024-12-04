package com.breezepannafoods.features.stockAddCurrentStock.api

import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.location.model.ShopRevisitStatusRequest
import com.breezepannafoods.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.breezepannafoods.features.stockAddCurrentStock.ShopAddCurrentStockRequest
import com.breezepannafoods.features.stockAddCurrentStock.model.CurrentStockGetData
import com.breezepannafoods.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class ShopAddStockRepository (val apiService : ShopAddStockApi){
    fun shopAddStock(shopAddCurrentStockRequest: ShopAddCurrentStockRequest?): Observable<BaseResponse> {
        return apiService.submShopAddStock(shopAddCurrentStockRequest)
    }

    fun getCurrStockList(sessiontoken: String, user_id: String, date: String): Observable<CurrentStockGetData> {
        return apiService.getCurrStockListApi(sessiontoken, user_id, date)
    }

}