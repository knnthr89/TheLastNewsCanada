package com.example.thelastnewscanada.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thelastnewscanada.models.Search

@Dao
abstract class SearchesDao {

    @Query("SELECT * FROM searches")
    abstract fun getAllSearches() : LiveData<List<Search>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSearch(keys: Search)

    @Query("DELETE FROM searches")
    abstract fun deleteAllSearches()

}