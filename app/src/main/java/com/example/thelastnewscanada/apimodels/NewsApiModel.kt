package com.example.thelastnewscanada.apimodels

import com.squareup.moshi.Json

data class NewsApiModel(@field:Json(name = "status") var status : String,
                        @field:Json(name = "totalResults") var totalResults : Int,
                        @field:Json(name = "articles") var articles : List<ArticlesApiModel>)