package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.remote.PhotosModel
import com.example.unsplashhomework.data.remote.photodetailsmodel.PhotoDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiPhotos {
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page:Int //page: Int
    ): PhotosModel

    @GET("photos")
    suspend fun getPhotoDetails(
        @Query("id") id:String
    ): PhotoDetails
}