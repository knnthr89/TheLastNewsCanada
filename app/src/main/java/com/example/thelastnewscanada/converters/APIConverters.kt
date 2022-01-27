package com.example.thelastnewscanada.converters

import com.example.thelastnewscanada.apimodels.ArticlesApiModel
import com.example.thelastnewscanada.models.Article

fun List<ArticlesApiModel>.convertToArticles() = this.map(ArticlesApiModel::convertToArticles)

fun ArticlesApiModel.convertToArticles() = Article (
        if(!this.author.isNullOrBlank()) this.author else "",
        if(!this.title.isNullOrBlank()) this.title else "",
        if(!this.description.isNullOrBlank()) this.description else "",
        if(!this.publishedAt.isNullOrBlank()) this.publishedAt else "",
        if(!this.content.isNullOrBlank()) this.content else "",
        if(!this.urlToImage.isNullOrBlank()) this.urlToImage else ""
)

