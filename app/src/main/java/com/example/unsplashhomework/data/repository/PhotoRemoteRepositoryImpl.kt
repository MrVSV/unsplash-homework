package com.example.unsplashhomework.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.unsplashhomework.data.api.ApiDigest
import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.api.ApiProfile
import com.example.unsplashhomework.data.api.photodto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.photodto.PhotoListDto
import com.example.unsplashhomework.data.api.photodto.WrapperPhotoDto
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.repository.PhotoRemoteRepository
import javax.inject.Inject

class PhotoRemoteRepositoryImpl @Inject constructor(
    private val apiPhotos: ApiPhotos,
    private val apiDigest: ApiDigest,
    private val apiProfile: ApiProfile
) :
    PhotoRemoteRepository {

    override suspend fun getPhotoList(requester: Requester, page: Int): PhotoListDto {
        Log.d(TAG, "getPhotoList: $requester")
        return when (requester) {
            Requester.ALL_LIST -> checkRequester(requester.param, page)
            Requester.COLLECTIONS -> apiDigest.getDigestPhotos(requester.param, page)
            Requester.PROFILE -> apiProfile.getProfileLikes(requester.param, page)
        }
    }

    private suspend fun checkRequester(query: String, page: Int) =
        if (query == "") apiPhotos.getPhotos(page)
        else apiPhotos.searchPhotos(query, page).results

    override suspend fun getPhotoDetails(id: String): PhotoDetailsDto =
        apiPhotos.getPhotoDetails(id)

    override suspend fun likePhoto(id: String): WrapperPhotoDto = apiPhotos.like(id)

    override suspend fun unlikePhoto(id: String): WrapperPhotoDto = apiPhotos.unlike(id)


}