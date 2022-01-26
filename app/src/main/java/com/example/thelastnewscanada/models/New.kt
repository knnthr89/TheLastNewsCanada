package com.example.thelastnewscanada.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class New(
   var status : String,
   var totalResults : Int,
   var articles : List<Article>
            ) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}