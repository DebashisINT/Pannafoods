package com.breezepannafoods.features.orderList.model

import com.breezepannafoods.base.BaseResponse


class ReturnListResponseModel: BaseResponse() {
    var return_list: ArrayList<ReturnDataModel>? = null
}