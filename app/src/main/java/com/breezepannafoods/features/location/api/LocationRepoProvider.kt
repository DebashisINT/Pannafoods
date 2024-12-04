package com.breezepannafoods.features.location.api

import com.breezepannafoods.features.location.shopdurationapi.ShopDurationApi
import com.breezepannafoods.features.location.shopdurationapi.ShopDurationRepository


object LocationRepoProvider {
    fun provideLocationRepository(): LocationRepo {
        return LocationRepo(LocationApi.create())
    }
}