package com.breezepannafoods.features.viewAllOrder.interf

import com.breezepannafoods.app.domain.NewOrderProductEntity
import com.breezepannafoods.app.domain.NewOrderSizeEntity

interface SizeListNewOrderOnClick {
    fun sizeListOnClick(size: NewOrderSizeEntity)
}