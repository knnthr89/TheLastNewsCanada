package com.example.thelastnewscanada.services

import com.example.thelastnewscanada.ApiModels.NewsApiModel
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.models.New
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPIService {

    @GET("everything?q=bitcoin&pageSize=100&apiKey=309858f664d141108a30962182bbeff0")
    suspend fun getAllArticles(): NewsApiModel

}