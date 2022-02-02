package com.example.thelastnewscanada.repository

import androidx.lifecycle.LiveData
import com.example.thelastnewscanada.BuildConfig

import com.example.thelastnewscanada.RetrofitClientInstance
import com.example.thelastnewscanada.converters.convertToArticles
import com.example.thelastnewscanada.database.NewsDatabase
import com.example.thelastnewscanada.enums.ResultStatus
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.models.Search
import java.io.IOException

class NewsRepository(
    private val newsDatabase: NewsDatabase
) {
    private val articlesDao = newsDatabase.articlesDao()
    private val searchesDao = newsDatabase.searchesDao()

    private val apikey : String = BuildConfig.NEWS_API_KEY
    suspend fun getNewsFromApi(theme : String) : ResultStatus {
        val articlesResult = safeApiRequest {
            apiService.getAllArticles(
                theme = theme,
                apiKey = apikey
            )
        }

        return if(articlesResult.success && articlesResult.result != null){
            var articles = articlesResult.result.articles.convertToArticles()

            if(articles.isNotEmpty()){
                articlesDao.deleteAllNews()
                articlesDao.insertArticles(articles)
            }

            ResultStatus.Success
        }else{
            articlesResult.status
        }
    }

    fun insertSearchFromView(search: Search) =
        searchesDao.insertSearch(search)

    fun getSearchesFromRoom() : LiveData<List<Search>> =
        searchesDao.getAllSearches()

    fun getArticlesFromRoom() : LiveData<List<Article>> =
        articlesDao.getArticles()

    fun deleteAllSearches() =
        searchesDao.deleteAllSearches()

    inner class ApiResult<T>(
        val result: T? = null,
        val status: ResultStatus = ResultStatus.Unknown
    ) {
        val success = status == ResultStatus.Success
    }

    private suspend fun <T> safeApiRequest(
        apiFunction: suspend () -> T
    ): ApiResult<T> =
        try {
            val result = apiFunction()
            ApiResult(result, ResultStatus.Success)
        } catch (ex: retrofit2.HttpException) {
            ApiResult(status = ResultStatus.RequestException)
        } catch (ex: IOException) {
            ApiResult(status = ResultStatus.NetworkException)
        } catch (ex: Exception) {
            ApiResult(status = ResultStatus.GeneralException)
        }

    companion object {
        private val apiService =  RetrofitClientInstance()

        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(newsDatabase: NewsDatabase) =
            this.instance ?: synchronized(this) {
                instance ?: NewsRepository(newsDatabase).also {
                    instance = it
                }
            }
    }
}