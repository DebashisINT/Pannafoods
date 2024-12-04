package com.breezepannafoods.features.viewAllOrder.interf

import com.breezepannafoods.app.domain.NewOrderGenderEntity
import com.breezepannafoods.app.domain.NewOrderProductEntity

interface ProductListNewOrderOnClick {
    fun productListOnClick(product: NewOrderProductEntity)
}