package com.example.thelastnewscanada.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    val author : String,
    val title : String,
    val description : String,
    val publishedAt : String,
    val content : String,
    val urlToImage : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}