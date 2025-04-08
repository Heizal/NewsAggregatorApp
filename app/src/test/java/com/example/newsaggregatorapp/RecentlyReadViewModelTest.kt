package com.example.newsaggregatorapp

import app.cash.turbine.test
import com.example.newsaggregatorapp.models.RecentlyReadArticleEntity
import com.example.newsaggregatorapp.repository.RecentlyReadRepository
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecentlyReadViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: RecentlyReadRepository
    private lateinit var viewModel: RecentlyReadViewModel

    private val testArticle = RecentlyReadArticleEntity(
        title = "Test Article",
        author = "Author Name",
        url = "https://example.com",
        urlToImage = "https://example.com/image.jpg",
        publishedAt = "2024-01-01",
        category = "Tech"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)

        // Mock flow of articles to simulate database updates
        every { repository.allRecentlyRead } returns MutableStateFlow(listOf(testArticle))

        viewModel = RecentlyReadViewModel(mockk(relaxed = true)).apply {
            // Overwrite repository with mock
            val field = this::class.java.getDeclaredField("repository")
            field.isAccessible = true
            field.set(this, repository)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `recentlyRead emits articles from repository`() = runTest {
        viewModel.recentlyRead.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val emission = awaitItem()
            assert(emission.contains(testArticle))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addToRecentlyRead calls repository`() = runTest {
        viewModel.addToRecentlyRead(testArticle)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.addToRecentlyRead(testArticle) }
    }
}