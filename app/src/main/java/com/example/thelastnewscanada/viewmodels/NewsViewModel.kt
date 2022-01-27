package com.example.thelastnewscanada.viewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
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
import okhttp3.Dispatcher

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

    fun refreshListNews(){
        refreshNews { repo.getNewsFromApi() }
    }
    fun refreshNews(updateFunction: suspend () -> NewsRepository.ResultStatus){
       //viewModelScope.launch {}

        viewModelScope.launch(IO){
            updateFunction().getErrorMessage(getApplication())
                ?.let { message -> errorMessage.value = message }
        }
    }
}