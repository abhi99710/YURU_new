package com.app.yuru.data.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.yuru.data.datasource.remote.model.NewsResponse
import com.app.yuru.data.datasource.remote.model.SourceResponse
import com.app.yuru.data.datasource.remote.model.TopHeadlineResponse
import com.app.yuru.data.datasource.remote.service.YuruApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsRemoteDatasourceImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var newsRemoteDatasourceImpl: YuruRemoteDatasourceImpl

    @MockK
    lateinit var service: YuruApiService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        newsRemoteDatasourceImpl = YuruRemoteDatasourceImpl(service)
    }

    @Test
    fun `test getTopHeadlines should return list news`() = runBlockingTest {
        // given
        val category = "technology"
        val country = "us"

        coEvery { service.getTopHeadlines(country, category) } returns generateFakeNews()

        // when
        val result = newsRemoteDatasourceImpl.getTopHeadlines(category, country)

        coVerify { service.getTopHeadlines(category = category, country = country) }

        assertEquals(1, result.size)
    }

    private fun generateFakeNews(): TopHeadlineResponse {
        return TopHeadlineResponse(
            status = "success",
            totalResults = 1,
            articles = listOf(
                NewsResponse(
                    source = SourceResponse(id = "1", name = "ok")
                )
            )
        )
    }
}