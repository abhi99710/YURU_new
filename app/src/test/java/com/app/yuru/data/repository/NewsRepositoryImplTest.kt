package com.app.yuru.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.app.yuru.data.datasource.local.NewsLocalDatasource
import com.app.yuru.data.datasource.remote.YuruRemoteDatasource
import com.app.yuru.domain.entity.News
import com.app.yuru.domain.entity.NewsSource
import com.app.yuru.coreandroid.network.NetworkChecker
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class NewsRepositoryImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var newsRepository: YuruRepository

    @MockK
    lateinit var newsRemoteDatasource: YuruRemoteDatasource

//    @MockK
//    lateinit var newsLocalDatasource: NewsLocalDatasource

    @MockK
    lateinit var networkChecker: NetworkChecker

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        newsRepository =
            YuruRepositoryImpl(newsRemoteDatasource, networkChecker)
    }

    @Test
    fun `test getTopHeadlines throw exceptions`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } throws IOException()

        val result = newsRepository.getTopHeadlines(country = "us", category = "technology")

        assertTrue(result.isLeft)
    }

    @Test
    fun `test getTopHeadlines network is connected should return data`() = runBlockingTest {
        // given
        val country = "us"
        val category = "technology"

        // network down
        every { networkChecker.isNetworkConnected() } returns true
//        coEvery { newsLocalDatasource.insertNews(generateFakeData()) } returns Unit

        coEvery {
            newsRemoteDatasource.getTopHeadlines(
                category,
                country
            )
        } returns generateFakeData()

        val result = newsRepository.getTopHeadlines(country, category)

//        coVerify { newsLocalDatasource.insertNews(generateFakeData()) }

        assertTrue(result.isRight)
    }

    @Test
    fun `test getTopHeadlines network is down should return local data`() = runBlockingTest {
        // given
        val country = "us"
        val category = "technology"

        // network down
        every { networkChecker.isNetworkConnected() } returns false

        // local return data
//        coEvery { newsLocalDatasource.getAllNews() } returns generateFakeData()

        // when
        val result = newsRepository.getTopHeadlines(country, category)
        // then
        assertTrue(result.isRight)
    }

    private fun generateFakeData(): List<News> {
        return listOf(
            News(
                source = NewsSource(id = "anu", name = "title"),
                author = "",
                title = "title",
                description = "",
                url = "",
                urlToImage = "",
                publishedAt = ""
            )
        )
    }

    @Test
    fun `test getTopHeadlines network is down and local data null should return failure`() =
        runBlockingTest {
            // given
            val country = "us"
            val category = "technology"

            // network down
            every { networkChecker.isNetworkConnected() } returns false
//            coEvery { newsLocalDatasource.getAllNews() } returns emptyList()

            // when
            val result = newsRepository.getTopHeadlines(country, category)
            // then
            assertTrue(result.isLeft)
        }
}