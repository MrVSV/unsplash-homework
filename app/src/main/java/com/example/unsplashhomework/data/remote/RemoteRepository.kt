package com.example.unsplashhomework.data.remote

import android.util.Log
import com.example.unsplashhomework.data.api.ApiPhotos
import javax.inject.Inject

class RemoteRepository@Inject constructor(private val apiPhotos: ApiPhotos) {

    suspend fun getData(page: Int) = apiPhotos.getPhotos(page)

}