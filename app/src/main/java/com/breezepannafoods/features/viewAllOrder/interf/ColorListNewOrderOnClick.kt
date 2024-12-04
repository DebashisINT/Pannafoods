package com.breezepannafoods.features.viewAllOrder.interf

import com.breezepannafoods.app.domain.NewOrderColorEntity
import com.breezepannafoods.app.domain.NewOrderProductEntity

interface ColorListNewOrderOnClick {
    fun productListOnClick(color: NewOrderColorEntity)
}