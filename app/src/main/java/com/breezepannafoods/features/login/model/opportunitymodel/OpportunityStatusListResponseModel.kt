package com.breezepannafoods.features.login.model.opportunitymodel

import com.breezepannafoods.app.domain.OpportunityStatusEntity
import com.breezepannafoods.app.domain.ProductListEntity
import com.breezepannafoods.base.BaseResponse

/**
 * Created by Puja on 30.05.2024
 */
class OpportunityStatusListResponseModel : BaseResponse() {
    var status_list: ArrayList<OpportunityStatusEntity>? = null
}