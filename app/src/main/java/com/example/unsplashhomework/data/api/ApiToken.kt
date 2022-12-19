package com.example.unsplashhomework.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com/"

interface ApiToken {

    @POST("https://unsplash.com/oauth/token")
    suspend fun getToken(
        @Query("code") code: String,
        @Query("client_id") clientId: String = KEY,
        @Query("client_secret") clientSecret: String = SECRET_KEY,
        @Query("redirect_uri") redirectUri: String = authorization_uri,
        @Query("grant_type") grantType: String = "authorization_code",
    ) :ResponseToken

    companion object {
        val retrofit: ApiToken = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiToken::class.java)
    }
}

class ResponseToken(
    val access_token: String,
 /*   val token_type: String,
    val scope: String,
    val created_at: Long,*/
) {

}