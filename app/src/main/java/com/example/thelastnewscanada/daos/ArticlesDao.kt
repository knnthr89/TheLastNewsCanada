package com.example.thelastnewscanada.daos

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thelastnewscanada.models.Article

@Dao
abstract class ArticlesDao {

    @Query("SELECT * FROM article")
    abstract fun getArticles(): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertArticles(keys: List<Article>)

    @Query("DELETE FROM article")
    abstract fun deleteAllNews()

}