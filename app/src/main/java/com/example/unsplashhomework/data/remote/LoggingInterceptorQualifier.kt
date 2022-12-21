package com.example.unsplashhomework.data.remote

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthTokenInterceptorQualifier