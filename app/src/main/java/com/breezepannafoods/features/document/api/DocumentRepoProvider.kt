package com.breezepannafoods.features.document.api

import com.breezepannafoods.features.dymanicSection.api.DynamicApi
import com.breezepannafoods.features.dymanicSection.api.DynamicRepo

object DocumentRepoProvider {
    fun documentRepoProvider(): DocumentRepo {
        return DocumentRepo(DocumentApi.create())
    }

    fun documentRepoProviderMultipart(): DocumentRepo {
        return DocumentRepo(DocumentApi.createImage())
    }
}