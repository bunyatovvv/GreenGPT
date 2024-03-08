package com.example.greengpt.data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteDTO(

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "message")
    val message : String
)