package com.example.thelastnewscanada.services

import com.example.thelastnewscanada.apimodels.NewsApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsAPIService {

    @GET("everything")
    suspend fun getAllArticles(
        @Query("q") theme: String = "bitcoin",
        @Query("apiKey") apiKey: String = "309858f664d141108a30962182bbeff0"
    ): NewsApiModel

}