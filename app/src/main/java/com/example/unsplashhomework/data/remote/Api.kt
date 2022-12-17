package com.example.unsplashhomework.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com/"

interface Api {
    @Headers(
        "Accept-Version: v1"
    )

    //TODO: change for authorized user after authorization is ready

    @GET("photos/?client_id=mj_2vu5NRd4iwh6pYzpqOymkmK79_WqclNutm5-O2UQ")
    suspend fun getPhotos(
        @Query("page") page:Int=1 //page: Int

    ): PhotosModel


    companion object {
        val retrofit: Api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}