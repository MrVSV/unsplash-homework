package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.api.dto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.dto.PhotoListDto
import com.example.unsplashhomework.data.api.dto.SearchDto
import com.example.unsplashhomework.data.api.dto.WrapperPhotoDto
import retrofit2.http.*

interface ApiPhotos {

    @GET("photos")
    suspend fun getPhotos(@Query("page") page: Int): PhotoListDto

    @GET("photos/{id}")
    suspend fun getPhotoDetails(@Path("id") id: String): PhotoDetailsDto

    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String): WrapperPhotoDto

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String): WrapperPhotoDto

    @GET("search/photos")
    suspend fun searchPhotos(@Query("query") query: String,@Query("page") page: Int,): SearchDto

}