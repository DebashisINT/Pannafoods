package com.breezepannafoods.features.viewAllOrder.interf

import com.breezepannafoods.app.domain.NewOrderGenderEntity
import com.breezepannafoods.features.viewAllOrder.model.ProductOrder
import java.text.FieldPosition

interface NewOrderSizeQtyDelOnClick {
    fun sizeQtySelListOnClick(product_size_qty: ArrayList<ProductOrder>)
    fun sizeQtyListOnClick(product_size_qty: ProductOrder,position: Int)
}