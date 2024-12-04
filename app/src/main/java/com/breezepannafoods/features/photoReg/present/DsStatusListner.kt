package com.breezepannafoods.features.photoReg.present

import com.breezepannafoods.app.domain.ProspectEntity
import com.breezepannafoods.features.photoReg.model.UserListResponseModel

interface DsStatusListner {
    fun getDSInfoOnLick(obj: ProspectEntity)
}