package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.remote.photodetailsmodel.PhotoDetails
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModel
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModelItem
import retrofit2.http.*

interface ApiPhotos {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int
    ): PhotosModel

    @GET("photos/{id}")
    suspend fun getPhotoDetails(
        @Path("id") id: String
    ): PhotoDetails

    @POST("photos/{id}/like")
    suspend fun like(
        @Path("id") id: String
    ): PhotosModelItem

    @DELETE("photos/{id}/like")
    suspend fun unlike(
        @Path("id") id: String
    ): PhotosModelItem

}