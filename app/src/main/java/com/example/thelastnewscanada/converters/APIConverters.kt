package com.example.thelastnewscanada.converters

import android.util.Log
import com.example.thelastnewscanada.ApiModels.ArticlesApiModel
import com.example.thelastnewscanada.models.Article

fun List<ArticlesApiModel>.convertToArticles() = this.map(ArticlesApiModel::convertToArticles)

fun ArticlesApiModel.convertToArticles() = Article (
        if(!this.author.isNullOrBlank()) this.author else "",
        this.title,
        this.description,
        this.publishedAt,
        this.content,
        this.urlToImage
)

