package com.example.thelastnewscanada.ApiModels

import com.squareup.moshi.Json

data class ArticlesApiModel(
    @field:Json(name = "author") var author : String,
    @field:Json(name = "title") var title : String,
    @field:Json(name = "description") var description : String,
    @field:Json(name = "publishedAt") var publishedAt : String,
    @field:Json(name = "content") var content : String,
    @field:Json(name = "urlToImage") var urlToImage : String
)