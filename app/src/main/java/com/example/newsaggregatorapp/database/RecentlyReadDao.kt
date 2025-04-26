package com.example.newsaggregatorapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsaggregatorapp.models.RecentlyReadArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecentlyReadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: RecentlyReadArticleEntity)

    @Query("SELECT * FROM recently_read_articles ORDER BY publishedAt DESC LIMIT 20")
    fun getRecentlyRead(): Flow<List<RecentlyReadArticleEntity>>
}