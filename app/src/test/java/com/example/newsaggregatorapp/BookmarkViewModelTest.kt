package com.example.newsaggregatorapp

import app.cash.turbine.test
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.repository.BookmarkRepository
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
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
class BookmarkViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: BookmarkRepository
    private lateinit var viewModel: BookmarkViewModel

    private val testArticle = ArticleEntity(
        title = "Bookmark Test Article",
        author = "Test Author",
        url = "https://test.com",
        urlToImage = "https://test.com/image.jpg",
        publishedAt = "2024-01-01",
        category = "Tech"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)

        every { repository.allBookmarks } returns MutableStateFlow(listOf(testArticle))

        viewModel = BookmarkViewModel(mockk(relaxed = true)).apply {
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
    fun `bookmarks emits articles from repository`() = runTest {
        viewModel.bookmarks.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val emission = awaitItem()
            assert(emission.contains(testArticle))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addBookmark calls repository`() = runTest {
        viewModel.addBookmark(testArticle)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.addBookmark(testArticle) }
    }

    @Test
    fun `removeBookmark calls repository`() = runTest {
        viewModel.removeBookmark(testArticle)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.removeBookmark(testArticle) }
    }

    @Test
    fun `isBookmarked returns true if article is in bookmarks`() {
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.isBookmarked(testArticle)
        assert(result)
    }


}