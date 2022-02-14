package com.example.thelastnewscanada.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.CoroutinesRoom
import com.example.thelastnewscanada.apimodels.NewsApiModel
import com.example.thelastnewscanada.database.NewsDatabase
import com.example.thelastnewscanada.enums.ResultStatus
import com.example.thelastnewscanada.extensions.getErrorMessage
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.models.Search
import com.example.thelastnewscanada.repository.NewsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class NewsViewModel(application: Application) : AndroidViewModel(application){

    private val newsRepo : NewsRepository
    val articles : LiveData<List<Article>>
    val errorMessage = MutableLiveData("")
    val searches : LiveData<List<Search>>

    init {
        newsRepo = NewsDatabase
            .getDatabase(application, viewModelScope)
            .let { db ->
                NewsRepository.getInstance(db)
            }

        searches = newsRepo.getSearchesFromRoom()

        refreshListNews()
        articles = newsRepo.getArticlesFromRoom()

    }

    fun refreshListNews(theme : String = "bitcoin"){
        //Dispatchers.Main -> Calling suspend function, UI functions or updating LiveData
        viewModelScope.launch(Dispatchers.Main) {
                refreshNews { newsRepo.getNewsFromApi(theme) }
        }
    }

    fun deleteAllSearches(){
        //Dispatchers.IO -> Database, reading/writing files or networking
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.deleteAllSearches()
        }
    }

    fun insertSearchToRoom(theme : String){
        //Dispatchers.IO -> Database, reading/writing files or networking
       viewModelScope.launch(Dispatchers.IO){
            val search = Search(name = theme)
            newsRepo.insertSearchFromView(search)
        }
    }

   private fun refreshNews(updateFunction: suspend () -> ResultStatus){
       //Dispatchers.IO -> Database, reading/writing files or networking
       viewModelScope.launch(Dispatchers.IO) {
           updateFunction().getErrorMessage(getApplication())
               .let {
                       message -> errorMessage.postValue(message)
                    }
       }
    }
}