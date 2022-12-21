package com.example.unsplashhomework.di

import com.example.unsplashhomework.data.api.*
import com.example.unsplashhomework.data.remote.AuthTokenInterceptorQualifier
import com.example.unsplashhomework.data.remote.LoggingInterceptorQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    @AuthTokenInterceptorQualifier
    fun provideAuthTokenInterceptor(): Interceptor = AuthTokenInterceptor()

    @Provides
    @LoggingInterceptorQualifier
    fun provideLoginInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideUnsplashClient(
        @LoggingInterceptorQualifier loggingInterceptor: Interceptor,
        @AuthTokenInterceptorQualifier authTokenInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(authTokenInterceptor)
        .followRedirects(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okhttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiToken(retrofit: Retrofit): ApiToken = retrofit.create(ApiToken::class.java)

    @Provides
    @Singleton
    fun provideApiPhotos(retrofit: Retrofit): ApiPhotos = retrofit.create(ApiPhotos::class.java)

    @Provides
    @Singleton
    fun provideApiDetailInfo(retrofit: Retrofit): ApiDetailInfo = retrofit.create(ApiDetailInfo::class.java)

    @Provides
    @Singleton
    fun provideApiLikes(retrofit: Retrofit): ApiLikes = retrofit.create(ApiLikes::class.java)
}