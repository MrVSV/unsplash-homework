package com.example.unsplashhomework.data.repository

import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.api.photodto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.photodto.PhotoListDto
import com.example.unsplashhomework.data.api.photodto.WrapperPhotoDto
import com.example.unsplashhomework.domain.PhotoRemoteRepository
import com.example.unsplashhomework.data.api.photodto.SearchDto
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.tools.toListPhotoEntity
import javax.inject.Inject

class PhotoRemoteRepositoryImpl @Inject constructor(private val apiPhotos: ApiPhotos) :
    PhotoRemoteRepository {

    override suspend fun test(requester: Requester, page: Int) =
        when (requester) {
            Requester.ALL_LIST -> checkRequester(requester.query, page)
            Requester.COLLECTIONS -> {
                /**  а вот тут вызов колеекции*/
                emptyList<PhotoEntity>()
            }
        }


    /** вот тут у вас два экстеншена которые делают одно и тоже , написаны чуть по разному выберете
     * тот который нравится ьольше другой удалите
     * ааа .... надо просто думать что возвращаете в одном месте аррей лист в другом просто лист
     * поэтому вам так просто не выбрать, что бы не удалили что то отвалится*/

    suspend fun checkRequester(query: String, page: Int) =
        if (query == "") apiPhotos.getPhotos(page).toListEntity()
        else apiPhotos.searchPhotos(query, page).results.toListPhotoEntity()


 /*   override suspend fun getPhotos(page: Int): PhotoListDto = apiPhotos.getPhotos(page)

    *//** пусть так останется пока*//*

    override suspend fun searchPhotos(query: String, page: Int): SearchDto =
        apiPhotos.searchPhotos(query, page)*/

    //две функции сверхк становятся приватными так как они нам негде больше не нужны, соответсвенно
    // их можно удалить из интерфейса

    override suspend fun getPhotoDetails(id: String): PhotoDetailsDto =
        apiPhotos.getPhotoDetails(id)

    override suspend fun likePhoto(id: String): WrapperPhotoDto = apiPhotos.like(id)

    override suspend fun unlikePhoto(id: String): WrapperPhotoDto = apiPhotos.unlike(id)


}