package com.breezepannafoods.features.nearbyuserlist.api

import com.breezepannafoods.app.Pref
import com.breezepannafoods.features.nearbyuserlist.model.NearbyUserResponseModel
import com.breezepannafoods.features.newcollection.model.NewCollectionListResponseModel
import com.breezepannafoods.features.newcollection.newcollectionlistapi.NewCollectionListApi
import io.reactivex.Observable

class NearbyUserRepo(val apiService: NearbyUserApi) {
    fun nearbyUserList(): Observable<NearbyUserResponseModel> {
        return apiService.getNearbyUserList(Pref.session_token!!, Pref.user_id!!)
    }
}