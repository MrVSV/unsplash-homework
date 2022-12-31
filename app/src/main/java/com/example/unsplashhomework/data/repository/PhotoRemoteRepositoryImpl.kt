package com.example.unsplashhomework.data.repository

import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.api.photodto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.photodto.PhotoListDto
import com.example.unsplashhomework.data.api.photodto.WrapperPhotoDto
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.PhotoRemoteRepository
import javax.inject.Inject

class PhotoRemoteRepositoryImpl @Inject constructor(private val apiPhotos: ApiPhotos) :
    PhotoRemoteRepository {

    override suspend fun test(requester: Requester, page: Int) =
        when (requester) {
            Requester.ALL_LIST -> checkRequester(requester.query, page)
            Requester.COLLECTIONS -> {
                /**  а вот тут вызов колеекции*/
                PhotoListDto()
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