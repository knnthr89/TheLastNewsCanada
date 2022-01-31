package com.example.thelastnewscanada.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.thelastnewscanada.database.NewsDatabase
import com.example.thelastnewscanada.extensions.getErrorMessage
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.repository.NewsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class NewsViewModel(application: Application) : AndroidViewModel(application){

    private val repo : NewsRepository
    val articles : LiveData<List<Article>>
    val errorMessage = MutableLiveData("")

    init {
        repo = NewsDatabase
            .getDatabase(application, viewModelScope)
            .let { db ->
                NewsRepository.getInstance(db)
            }

        refreshListNews()
        articles = repo.getArticlesFromRoom()

    }

    fun refreshListNews(theme : String = "bitcoin"){
        refreshNews { repo.getNewsFromApi(theme) }
    }

   private fun refreshNews(updateFunction: suspend () -> NewsRepository.ResultStatus){
       viewModelScope.launch(Dispatchers.Main) {
           updateFunction().getErrorMessage(getApplication())
               ?.let { message -> errorMessage.value = message }
       }
    }
}