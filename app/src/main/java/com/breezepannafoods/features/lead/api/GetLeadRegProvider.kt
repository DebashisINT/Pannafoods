package com.breezepannafoods.features.lead.api

import com.breezepannafoods.features.NewQuotation.api.GetQuotListRegRepository
import com.breezepannafoods.features.NewQuotation.api.GetQutoListApi


object GetLeadRegProvider {
    fun provideList(): GetLeadListRegRepository {
        return GetLeadListRegRepository(GetLeadListApi.create())
    }
}