package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.remote.PhotosModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDetailInfo {

    @GET("photos/{id}")
    suspend fun getDetailInfo(
        @Path("id") id: String
    ): PhotosModel.PhotosModelItem
}