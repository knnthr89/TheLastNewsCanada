package com.example.thelastnewscanada.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searches")
data class Search(
   var name : String,
   ) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}