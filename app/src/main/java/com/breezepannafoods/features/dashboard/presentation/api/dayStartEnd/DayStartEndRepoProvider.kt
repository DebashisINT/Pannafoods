package com.breezepannafoods.features.dashboard.presentation.api.dayStartEnd

import com.breezepannafoods.features.stockCompetetorStock.api.AddCompStockApi
import com.breezepannafoods.features.stockCompetetorStock.api.AddCompStockRepository

object DayStartEndRepoProvider {
    fun dayStartRepositiry(): DayStartEndRepository {
        return DayStartEndRepository(DayStartEndApi.create())
    }

}