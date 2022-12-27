package com.example.unsplashhomework.tools

import android.widget.ImageView
import androidx.appcompat.widget.SearchView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unsplashhomework.R
import com.example.unsplashhomework.data.api.dto.PhotoDto
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.data.model.Photo

fun List<PhotoDto>.toListPhoto(): List<Photo> {
    val newList = mutableListOf<Photo>()

    this.forEach{ item->
        newList.add(item.toPhoto())
    }
    return newList
}

fun List<PhotoDto>.toListPhotoEntity(): List<PhotoEntity> {
    return this.map {
        it.toPhotoEntity()
    }
}

fun ImageView.loadImage(urls:String){
    Glide.with(this)
        .load(urls)
        .error(R.drawable.error_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.placeholder)
        .into(this)
}

fun ImageView.loadImage(imageId:Int){
    Glide.with(this)
        .load(imageId)
        .error(R.drawable.error_image)
        .placeholder(R.drawable.placeholder)
        .into(this)
}

fun SearchView.setChangeTextListener(block:(query:String)->Unit){

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String): Boolean {
            block(newText)
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }
    })
}
