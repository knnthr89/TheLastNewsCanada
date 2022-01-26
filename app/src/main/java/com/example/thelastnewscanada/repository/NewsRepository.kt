package com.example.thelastnewscanada.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.thelastnewscanada.RetrofitClientInstance
import com.example.thelastnewscanada.converters.convertToArticles
import com.example.thelastnewscanada.database.NewsDatabase
import com.example.thelastnewscanada.models.Article
import java.io.IOException

class NewsRepository(
    private val newsDatabase: NewsDatabase
) {
    private val articlesDao = newsDatabase.articlesDao()

    suspend fun getNewsFromApi() : ResultStatus {
        val articlesResult = safeApiRequest {
            apiService.getAllArticles()
        }

        return if(articlesResult.success && articlesResult.result != null){
            var articles = articlesResult.result.articles.convertToArticles()

            articlesDao.insertArticles(articles)

            ResultStatus.Success
        }else{
            articlesResult.status
        }
    }

    fun getArticlesFromRoom() : LiveData<List<Article>> =
        articlesDao.getArticles()

    enum class ResultStatus {
        Unknown,
        Success,
        NetworkException,
        RequestException,
        GeneralException
    }

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