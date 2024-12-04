package com.breezepannafoods.features.viewAllOrder.orderOptimized

import com.breezepannafoods.app.domain.ProductOnlineRateTempEntity
import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.login.model.productlistmodel.ProductRateDataModel
import java.io.Serializable

class ProductRateOnlineListResponseModel: BaseResponse(), Serializable {
    var product_rate_list: ArrayList<ProductOnlineRateTempEntity>? = null
}