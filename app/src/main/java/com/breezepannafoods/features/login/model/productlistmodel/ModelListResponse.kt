package com.breezepannafoods.features.login.model.productlistmodel

import com.breezepannafoods.app.domain.ModelEntity
import com.breezepannafoods.app.domain.ProductListEntity
import com.breezepannafoods.base.BaseResponse

class ModelListResponse: BaseResponse() {
    var model_list: ArrayList<ModelEntity>? = null
}