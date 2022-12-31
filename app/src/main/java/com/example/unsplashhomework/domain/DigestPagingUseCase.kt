package com.example.unsplashhomework.domain

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.unsplashhomework.data.repository.DigestPagingSource
import com.example.unsplashhomework.domain.model.Digest
import javax.inject.Inject

class DigestPagingUseCase @Inject constructor(
    private val repository: DigestRemoteRepository
) {
    /**если прикручивать рефрешер к пейджинг сурсу, то при обновлени приложение вылетает сошибкой
     * вычитал, что в пейджере должен каждый раз создаваться новый экземпляр сурса
     * так что вот**/
    fun getDigest():Pager<Int, Digest> {
        Log.d(TAG, "getDigest: ")
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { DigestPagingSource(repository) }
        )
    }
}