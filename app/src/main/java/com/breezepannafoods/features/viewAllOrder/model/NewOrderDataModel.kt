package com.breezepannafoods.features.viewAllOrder.model

import com.breezepannafoods.app.domain.NewOrderColorEntity
import com.breezepannafoods.app.domain.NewOrderGenderEntity
import com.breezepannafoods.app.domain.NewOrderProductEntity
import com.breezepannafoods.app.domain.NewOrderSizeEntity
import com.breezepannafoods.features.stockCompetetorStock.model.CompetetorStockGetDataDtls

class NewOrderDataModel {
    var status:String ? = null
    var message:String ? = null
    var Gender_list :ArrayList<NewOrderGenderEntity>? = null
    var Product_list :ArrayList<NewOrderProductEntity>? = null
    var Color_list :ArrayList<NewOrderColorEntity>? = null
    var size_list :ArrayList<NewOrderSizeEntity>? = null
}

