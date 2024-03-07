package com.example.greengpt.data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "favorite_table")
data class FavoriteDTO(

    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "message")
    val message : String
)