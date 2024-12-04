package com.breezepannafoods.features.login.model.productlistmodel

import com.breezepannafoods.app.domain.ProductListEntity
import com.breezepannafoods.base.BaseResponse

/**
 * Created by Saikat on 20-11-2018.
 */
class ProductListResponseModel : BaseResponse() {
    //var product_list: ArrayList<ProductListDataModel>? = null
    var product_list: ArrayList<ProductListEntity>? = null
}