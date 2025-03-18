package com.example.newsaggregatorapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsaggregatorapp.models.ArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarked_articles")
    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(article: ArticleEntity)

    @Delete
    suspend fun removeBookmark(article: ArticleEntity)
}