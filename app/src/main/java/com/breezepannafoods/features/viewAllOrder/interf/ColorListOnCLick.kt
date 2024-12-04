package com.breezepannafoods.features.viewAllOrder.interf

import com.breezepannafoods.app.domain.NewOrderGenderEntity
import com.breezepannafoods.features.viewAllOrder.model.ProductOrder

interface ColorListOnCLick {
    fun colorListOnCLick(size_qty_list: ArrayList<ProductOrder>, adpPosition:Int)
}