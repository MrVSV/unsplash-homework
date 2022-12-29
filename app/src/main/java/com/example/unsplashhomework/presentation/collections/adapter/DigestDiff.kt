package com.example.unsplashhomework.presentation.collections.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.unsplashhomework.domain.model.Digest

class DigestDiff : DiffUtil.ItemCallback<Digest>() {

    override fun areItemsTheSame(oldItem: Digest, newItem: Digest) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Digest, newItem: Digest) =
        oldItem == newItem
}