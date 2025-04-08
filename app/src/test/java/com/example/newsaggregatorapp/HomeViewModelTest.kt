package com.example.newsaggregatorapp

import com.example.newsaggregatorapp.api.NewsApiService
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.models.NewsResponse
import com.example.newsaggregatorapp.service.RetrofitInstance
import com.example.newsaggregatorapp.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeApiService: NewsApiService

    private val testCategory = "technology"

    private val testArticle = ArticleEntity(
        title = "Test Title",
        author = "Test Author",
        publishedAt = "2024-01-01",
        url = "https://test.com",
        urlToImage = "https://test.com/img.jpg",
        category = testCategory
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeApiService = mockk()
        coEvery {
            fakeApiService.getTopHeadlines(
                category = testCategory,
                apiKey = any()
            )
        } returns NewsResponse(listOf(testArticle))

        mockkObject(RetrofitInstance)
        every { RetrofitInstance.getApiService(any()) } returns fakeApiService
        every { RetrofitInstance.getApiKey() } returns "fake_api_key"

        viewModel = HomeViewModel(mockk(relaxed = true))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `fetchNews loads articles into news StateFlow`() = runTest {
        viewModel.fetchNews(testCategory)
        testDispatcher.scheduler.advanceUntilIdle()

        val articles = viewModel.news.value
        assertEquals(1, articles.size)
        assertEquals(testArticle.title, articles.first().title)
    }

    @Test
    fun `setCategory updates selectedCategory and triggers fetchNews`() = runTest {
        viewModel.setCategory(testCategory)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(testCategory, viewModel.selectedCategory.value)
        assertEquals(1, viewModel.news.value.size)
    }






}