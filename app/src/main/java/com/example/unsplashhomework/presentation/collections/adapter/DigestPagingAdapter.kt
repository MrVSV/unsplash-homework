package com.example.unsplashhomework.presentation.collections.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.unsplashhomework.domain.model.Digest
import com.example.unsplashhomework.databinding.DigestViewHolderBinding

/**Лишние пробелы между строчками тоже удаляем*/
class DigestPagingAdapter(
    private val onClick: (Digest) -> Unit,
) : PagingDataAdapter<Digest, DigestViewHolder>(DigestDiff()) {

    /** вот тут  я понимаю что нажали автоформатирование но иногда нужно руками прпавить
     * Также красивее?*/

    override fun onBindViewHolder(holder: DigestViewHolder, position: Int) {
        getItem(position)?.let { item ->
            /**Digest это переменная и должна писаться так же со строчной буквы*/
            holder.bind(item) { Digest -> onClick(Digest) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DigestViewHolder(
        DigestViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}