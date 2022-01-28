package com.example.thelastnewscanada.services

import com.example.thelastnewscanada.apimodels.NewsApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsAPIService {

    @GET("everything")
    suspend fun getAllArticles(
        @Query("q") theme: String,
        @Query("apiKey") apiKey: String
    ): NewsApiModel

}