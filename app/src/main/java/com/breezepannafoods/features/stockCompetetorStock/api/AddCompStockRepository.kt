package com.breezepannafoods.features.stockCompetetorStock.api

import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.orderList.model.NewOrderListResponseModel
import com.breezepannafoods.features.stockCompetetorStock.ShopAddCompetetorStockRequest
import com.breezepannafoods.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class AddCompStockRepository(val apiService:AddCompStockApi){

    fun addCompStock(shopAddCompetetorStockRequest: ShopAddCompetetorStockRequest): Observable<BaseResponse> {
        return apiService.submShopCompStock(shopAddCompetetorStockRequest)
    }

    fun getCompStockList(sessiontoken: String, user_id: String, date: String): Observable<CompetetorStockGetData> {
        return apiService.getCompStockList(sessiontoken, user_id, date)
    }
}