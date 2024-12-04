package com.breezepannafoods.features.location.shopRevisitStatus

import com.breezepannafoods.features.location.shopdurationapi.ShopDurationApi
import com.breezepannafoods.features.location.shopdurationapi.ShopDurationRepository

object ShopRevisitStatusRepositoryProvider {
    fun provideShopRevisitStatusRepository(): ShopRevisitStatusRepository {
        return ShopRevisitStatusRepository(ShopRevisitStatusApi.create())
    }
}