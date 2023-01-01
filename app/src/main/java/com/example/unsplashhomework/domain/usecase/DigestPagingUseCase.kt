package com.example.unsplashhomework.domain.usecase

import androidx.paging.Pager
import com.example.unsplashhomework.domain.model.Digest

interface DigestPagingUseCase {

    fun getDigest(): Pager<Int, Digest>
}