package com.example.unsplashhomework.data.api

import com.example.unsplashhomework.data.remote.PhotosModel
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiLikes {

    @POST("photos/{id}/like")
    suspend fun like(
        @Path("id") id: String
    ):PhotosModel.PhotosModelItem

    @DELETE("photos/{id}/like")
    suspend fun unlike(
        @Path("id") id: String
    ):PhotosModel.PhotosModelItem
}