package com.example.thelastnewscanada.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.thelastnewscanada.daos.ArticlesDao
import com.example.thelastnewscanada.models.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Article::class], exportSchema = false, version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articlesDao() : ArticlesDao

    companion object {
        @Volatile
        private var Instance: NewsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope):
                NewsDatabase= Instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                NewsDatabase::class.java,
                "NewsDatabase"
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    scope.launch {
                        Instance
                            ?.articlesDao()
                            //?.getCurrentArticles()
                    }
                }
            }).build()

            Instance = instance

            instance
        }
    }
}