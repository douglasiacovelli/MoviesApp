package com.iacovelli.moviesapp.details

import android.view.View
import com.iacovelli.moviesapp.ReadFixture
import com.iacovelli.moviesapp.RxOverridingRule
import com.iacovelli.moviesapp.common.io.MoviesRetrofit
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    private val contract: DetailsContract = mock()
    @get:Rule
    val rxOverridingRule = RxOverridingRule()

    @Test
    fun testGetTvShow() {
        val mockWebServer = MockWebServer()
        MoviesRetrofit.BASE_URL = mockWebServer.url("").toString()

        val fixture = ReadFixture().execute("TvShowDetails.json")

        mockWebServer.enqueue(MockResponse().setBody(fixture))
        val presenter = DetailsViewModel(contract, 9)

        verify(contract).setupList(any())
        Assert.assertNotNull(presenter.tvShowPresenter)
        assertEquals(View.GONE, presenter.loadingPresenter.pageVisibility)
        mockWebServer.shutdown()
    }

    @Test
    fun testGetTvShowWith400Response() {
        val mockWebServer = MockWebServer()
        MoviesRetrofit.BASE_URL = mockWebServer.url("").toString()

        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        val presenter = DetailsViewModel(contract, 9)

        verify(contract, never()).setupList(any())
        Assert.assertNull(presenter.tvShowPresenter)
        assertEquals(View.VISIBLE, presenter.loadingPresenter.tryAgainVisibility)
        mockWebServer.shutdown()
    }
}

