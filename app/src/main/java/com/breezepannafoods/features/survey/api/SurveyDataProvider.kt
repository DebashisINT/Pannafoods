package com.breezepannafoods.features.survey.api

import com.breezepannafoods.features.photoReg.api.GetUserListPhotoRegApi
import com.breezepannafoods.features.photoReg.api.GetUserListPhotoRegRepository

object SurveyDataProvider{

    fun provideSurveyQ(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.create())
    }

    fun provideSurveyQMultiP(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.createImage())
    }
}