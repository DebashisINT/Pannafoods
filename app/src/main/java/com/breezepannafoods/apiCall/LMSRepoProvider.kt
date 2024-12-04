package com.breezepannafoods.features.mylearning.apiCall

import com.breezepannafoods.features.login.api.opportunity.OpportunityListApi
import com.breezepannafoods.features.login.api.opportunity.OpportunityListRepo

object LMSRepoProvider {
    fun getTopicList(): LMSRepo {
        return LMSRepo(LMSApi.create())
    }
}