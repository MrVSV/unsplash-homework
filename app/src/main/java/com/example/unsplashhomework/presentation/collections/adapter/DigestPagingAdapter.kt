package com.example.unsplashhomework.presentation.collections.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.unsplashhomework.data.model.Digest
import com.example.unsplashhomework.databinding.DigestViewHolderBinding


class DigestPagingAdapter(
    private val onClick: (Digest) -> Unit
) : PagingDataAdapter<Digest, DigestViewHolder>(DigestDiff()) {

    override fun onBindViewHolder(holder: DigestViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item) { Digest ->
                onClick(
                    Digest
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DigestViewHolder(
        DigestViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}