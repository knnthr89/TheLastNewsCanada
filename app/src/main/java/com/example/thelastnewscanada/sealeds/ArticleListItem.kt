package com.example.thelastnewscanada.sealeds

import com.example.thelastnewscanada.models.Article

sealed class ArticleListItem(val name : String) {
    data class Item(val article : Article) : ArticleListItem(article.title)
    data class Separator(private val letter : Char) : ArticleListItem(letter.toUpperCase().toString())
}