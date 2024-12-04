package com.breezepannafoods.features.newcollectionreport

import com.breezepannafoods.features.photoReg.model.UserListResponseModel

interface PendingCollListner {
    fun getUserInfoOnLick(obj: PendingCollData)
}