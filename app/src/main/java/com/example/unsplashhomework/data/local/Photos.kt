package com.example.unsplashhomework.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**Для каждой фотографии в списке отобразить картинку, лайк от текущего пользователя (или
 * отсутствие лайка), общее количество лайков, создателя фотографии (имя/аватар).
 *
 * Список фотографий кешируется в БД. - uri в БД?
В случае отсутствия соединения с сетью отобразить закешированные фотографии из БД с оповещением пользователя.
**/

@Entity(tableName = "photos")
data class Photos(
    @PrimaryKey
    @ColumnInfo(name = "photo")
    val photo: String,
    @ColumnInfo(name = "date")
    val date: String
)