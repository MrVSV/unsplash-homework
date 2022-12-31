package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.api.digestdto.DigestDto
import com.example.unsplashhomework.data.api.digestdto.DigestListDto
import com.example.unsplashhomework.data.api.photodto.PhotoListDto
import retrofit2.http.*

interface ApiDigest {

    @GET("collections")
    suspend fun getDigests(@Query("page") page: Int): DigestListDto

    @GET("collections/{id}/photos")
    suspend fun getDigestPhotos(
        @Path("id") id: String,
        @Query("page") page: Int
    ): PhotoListDto

    @GET("collections/{id}")
    suspend fun getDigestInfo(
        @Path("id") id: String
    ): DigestDto
//
//    @DELETE("photos/{id}/like")
//    suspend fun unlike(@Path("id") id: String): WrapperPhotoDto

}