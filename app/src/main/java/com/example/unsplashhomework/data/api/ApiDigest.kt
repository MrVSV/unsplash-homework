package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.api.digestdto.DigestListDto
import retrofit2.http.*

interface ApiDigest {

    @GET("collections")
    suspend fun getDigests(@Query("page") page: Int): DigestListDto

//    @GET("photos/{id}")
//    suspend fun getPhotoDetails(@Path("id") id: String): PhotoDetailsDto
//
//    @POST("photos/{id}/like")
//    suspend fun like(@Path("id") id: String): WrapperPhotoDto
//
//    @DELETE("photos/{id}/like")
//    suspend fun unlike(@Path("id") id: String): WrapperPhotoDto

}