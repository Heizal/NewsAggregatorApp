package com.example.newsaggregatorapp

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import com.example.newsaggregatorapp.api.NewsApiService
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.models.NewsResponse
import com.example.newsaggregatorapp.repository.RecentSearchRepository
import com.example.newsaggregatorapp.service.RetrofitInstance
import com.example.newsaggregatorapp.viewmodel.SearchViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private lateinit var repository: RecentSearchRepository
    private lateinit var apiService: NewsApiService

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        repository = mockk(relaxed = true)
        apiService = mockk()
        val mockApp = mockk<Application>(relaxed = true)

        viewModel = SearchViewModel(
            application = mockApp,
            savedStateHandle = SavedStateHandle(),
            recentSearchRepo = repository,
            apiService = apiService,
            apiKey = "test-api-key"
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchNews fetches articles and saves query`() = runTest {
        val query = "climate"
        val expectedArticles = listOf(
            ArticleEntity(
                title = "Climate News",
                author = "Reporter",
                publishedAt = "2024-12-01",
                url = "https://climate.com",
                urlToImage = null,
                category = "science"
            )
        )

        // Mock API response
        coEvery { apiService.searchArticles(query = query, apiKey = any()) } returns
                NewsResponse(articles = expectedArticles)

        // Act
        viewModel.searchNews(query)
        advanceUntilIdle()

        // ✅ Then: searchResults should contain the expected articles
        assertEquals(expectedArticles, viewModel.searchResults.value)

        // ✅ And: recent search should be saved
        coVerify { repository.addSearch(query) }
    }
}