package com.example.thelastnewscanada.converters

import com.example.thelastnewscanada.apimodels.ArticlesApiModel
import com.example.thelastnewscanada.models.Article

fun List<ArticlesApiModel>.convertToArticles() = this.map(ArticlesApiModel::convertToArticles)

fun ArticlesApiModel.convertToArticles() = Article (
         this.author ?: "",
         this.title ?: "",
         this.description ?: "",
         this.publishedAt ?: "",
         this.content ?: "",
         this.urlToImage ?: ""
)

