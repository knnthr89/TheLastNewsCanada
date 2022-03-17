package com.example.thelastnewscanada.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import androidx.room.CoroutinesRoom
import com.example.thelastnewscanada.apimodels.NewsApiModel
import com.example.thelastnewscanada.daos.ArticlesDao
import com.example.thelastnewscanada.database.NewsDatabase
import com.example.thelastnewscanada.enums.ResultStatus
import com.example.thelastnewscanada.extensions.getErrorMessage
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.models.Search
import com.example.thelastnewscanada.repository.NewsRepository
import com.example.thelastnewscanada.sealeds.ArticleListItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsViewModel(context : Context) : ViewModel(){

    private val newsRepo : NewsRepository
    //val articles : LiveData<List<Article>>
    val errorMessage = MutableLiveData("")
    val searches : LiveData<List<Search>>
    private var context : Context = context

    init {
        newsRepo = NewsDatabase
            .getDatabase(context, viewModelScope)
            .let { db ->
                NewsRepository.getInstance(db)
            }

        searches = newsRepo.getSearchesFromRoom()

        refreshListNews()
        //articles = newsRepo.getArticlesFromRoom()

    }

    val allArticles : Flow<PagingData<ArticleListItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 50
        )

    ){
        newsRepo.articlesDao.getArticles()
    }.flow
        .map { pagingData ->
            pagingData
                // Map cheeses to common UI model.
                .map { article -> ArticleListItem.Item(article) }
                .insertSeparators { before: ArticleListItem?, after: ArticleListItem? ->
                    if (before == null && after == null) {
                        // List is empty after fully loaded; return null to skip adding separator.
                        null
                    } else if (after == null) {
                        // Footer; return null here to skip adding a footer.
                        null
                    } else if (before == null) {
                        // Header
                        ArticleListItem.Separator(after.name.first())
                    } else if (!before.name.first().equals(after.name.first(), ignoreCase = true)){
                        // Between two items that start with different letters.
                        ArticleListItem.Separator(after.name.first())
                    } else {
                        // Between two items that start with the same letter.
                        null
                    }
                }
        }
        .cachedIn(viewModelScope)

    val articles : Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = 500,
            enablePlaceholders = true,
            maxSize = 2000
        )
    ){
      newsRepo.articlesDao.getArticles()
    }.flow
        .map { pagingData ->
            pagingData
                .map { article ->
                    article
                }
        }
        .cachedIn(viewModelScope)

    fun refreshListNews(theme : String = "Vancouver"){
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
           updateFunction().getErrorMessage(context.applicationContext)
               .let {
                       message -> errorMessage.postValue(message)
                    }
       }
    }
}