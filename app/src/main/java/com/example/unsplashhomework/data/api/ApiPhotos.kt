package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.remote.PhotosModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiPhotos {
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page:Int //page: Int
    ): PhotosModel
}