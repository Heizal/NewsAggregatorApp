package com.example.newsaggregatorapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.models.RecentSearchEntity
import com.example.newsaggregatorapp.models.RecentlyReadArticleEntity

@Database(
    entities = [ArticleEntity::class, RecentlyReadArticleEntity::class, RecentSearchEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun recentlyReadDao(): RecentlyReadDao
    abstract fun recentSearchDao(): RecentSearchDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "news_app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }

        }
    }
}