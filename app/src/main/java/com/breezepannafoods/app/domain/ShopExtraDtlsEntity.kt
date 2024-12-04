package com.breezepannafoods.app.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.breezepannafoods.app.AppConstant

@Entity(tableName = AppConstant.SHOP_EXTRA_DTLS)
data class ShopExtraDtlsEntity(
    @PrimaryKey(autoGenerate = false)
    var shop_id: String="",
    var bkt:String="",
    var total_outstanding:String="",
    var pos:String="",
    var emi_amt:String="",
    var all_charges:String="",
    var total_collectable:String="",
    var risk_id:String="",
    var risk_name:String="",
    var workable:String="",
    var disposition_code_id:String="",
    var disposition_code_name:String="",
    var ptp_Date:String="",
    var ptp_amt:String="",
    var collection_date:String="",
    var collection_amount:String="",
    var final_status_id:String="",
    var final_status_name:String="",
    var isUploaded: Boolean = false
)
