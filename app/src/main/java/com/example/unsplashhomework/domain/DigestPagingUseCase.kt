package com.example.unsplashhomework.domain

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.unsplashhomework.domain.model.Digest
import com.example.unsplashhomework.data.repository.DigestPagingSource
import javax.inject.Inject

class DigestPagingUseCase @Inject constructor(
    private val digestPagingSource: DigestPagingSource
) {
    fun getDigest():Pager<Int, Digest> {
        Log.d(TAG, "getDigest: ")
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { digestPagingSource }
        )
    }
}