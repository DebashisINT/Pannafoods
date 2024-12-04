package com.breezepannafoods.features.stockAddCurrentStock.api

import com.breezepannafoods.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.breezepannafoods.features.location.shopRevisitStatus.ShopRevisitStatusRepository

object ShopAddStockProvider {
    fun provideShopAddStockRepository(): ShopAddStockRepository {
        return ShopAddStockRepository(ShopAddStockApi.create())
    }
}