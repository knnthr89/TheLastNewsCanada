package com.example.thelastnewscanada

import com.example.thelastnewscanada.services.NewsAPIService
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun RetrofitClientInstance(baseUrl : String = "https://newsapi.org/v2/") : NewsAPIService{
    val moshi = Moshi.Builder().build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()

    return retrofit.create(NewsAPIService::class.java)
}