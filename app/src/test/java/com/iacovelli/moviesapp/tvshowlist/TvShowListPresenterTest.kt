package com.iacovelli.moviesapp.tvshowlist

import android.view.View
import com.iacovelli.moviesapp.FakeCache
import com.iacovelli.moviesapp.ReadFixture
import com.iacovelli.moviesapp.RxOverridingRule
import com.iacovelli.moviesapp.common.configuration.Cache
import com.iacovelli.moviesapp.common.configuration.FetchConfiguration
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.iacovelli.moviesapp.common.io.MoviesRetrofit
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowListPresenterTest {

    private val contract: TvShowListContract = mock()
    private val defaultCache = FakeCache(true)

    @get:Rule
    val rxOverridingRule = RxOverridingRule()

    @Test
    fun testGetConfigurationCachedAndPopularTvShowsSuccessfully() {
        val webServer = MockWebServer()
        MoviesRetrofit.BASE_URL = webServer.url("").toString()
        val fixture = ReadFixture().execute("PopularTvShows.json")
        webServer.enqueue(MockResponse().setBody(fixture))

        val presenter = instantiatePresenter(FakeCache(true))

        val request = webServer.takeRequest()
        if (request.requestUrl.queryParameter("api_key").isNullOrBlank()) {
            Assert.fail("missing api_key")
        }
        if (request.path.contains("configuration")) {
            Assert.fail()
        }
        assertEquals("1", request.requestUrl.queryParameter("page"))

        verify(contract).setupList(any())
        verify(contract, never()).showMessage(any())
        webServer.shutdown()
    }

    @Test
    fun testGetConfigurationNoCache() {
        val webServer = MockWebServer()
        MoviesRetrofit.BASE_URL = webServer.url("").toString()
        val configFixture = ReadFixture().execute("Configuration.json")
        webServer.enqueue(MockResponse().setBody(configFixture))

        val tvShowsFixture = ReadFixture().execute("PopularTvShows.json")
        webServer.enqueue(MockResponse().setBody(tvShowsFixture))

        val presenter = instantiatePresenter(FakeCache(false))

        val configurationRequest = webServer.takeRequest()
        if (configurationRequest.path.contains("tv")) {
            Assert.fail()
        }

        val tvRequestPage1 = webServer.takeRequest()

        webServer.enqueue(MockResponse().setBody(tvShowsFixture))
        presenter.fetchNextPage()
                .subscribe({},{ fail()})

        val tvRequestPage2 = webServer.takeRequest()
        if (tvRequestPage2.path.contains("configuration")) {
            Assert.fail()
        }

        verify(contract).setupList(any())
        verify(contract, never()).showMessage(any())
        webServer.shutdown()
    }

    @Test
    fun testFetchNextPagesOnPagination() {
        val webServer = MockWebServer()
        MoviesRetrofit.BASE_URL = webServer.url("").toString()
        val fixturePage1 = ReadFixture().execute("PopularTvShows.json")
        webServer.enqueue(MockResponse().setBody(fixturePage1))

        val fixturePage2 = ReadFixture().execute("PopularTvShows.json")
        webServer.enqueue(MockResponse().setBody(fixturePage2))

        val presenter = instantiatePresenter(FakeCache(true))
        presenter.fetchNextPage()
                .subscribe({}, { fail() })

        val request1 = webServer.takeRequest()
        assertEquals("1", request1.requestUrl.queryParameter("page"))

        val request2 = webServer.takeRequest()
        assertEquals("2", request2.requestUrl.queryParameter("page"))

        verify(contract).setupList(any())
        verify(contract).addResults(any())
        verify(contract, never()).showMessage(any())
        webServer.shutdown()
    }

    @Test
    fun testIssueWhileFetchingConfiguration() {
        val webServer = MockWebServer()
        MoviesRetrofit.BASE_URL = webServer.url("").toString()
        webServer.enqueue(MockResponse().setResponseCode(400))

        val presenter = instantiatePresenter(FakeCache(false))

        assertEquals(View.VISIBLE, presenter.loadingPresenter.tryAgainVisibility)
        verify(contract, never()).setupList(any())
        verify(contract).showMessage(any())
        webServer.shutdown()
    }

    @Test
    fun testIssueWhileFetchingTvShowList() {
        val webServer = MockWebServer()
        MoviesRetrofit.BASE_URL = webServer.url("").toString()
        webServer.enqueue(MockResponse().setResponseCode(400))

        val presenter = instantiatePresenter(FakeCache(true))

        assertEquals(View.VISIBLE, presenter.loadingPresenter.tryAgainVisibility)
        verify(contract, never()).setupList(any())
        verify(contract).showMessage(any())
        webServer.shutdown()
    }

    private fun instantiatePresenter(cache: Cache = defaultCache): TvShowListPresenter {
        return TvShowListPresenter(
                contract = contract,
                fetchConfiguration = FetchConfiguration(cache),
                getCachedConfiguration = GetCachedConfiguration(cache))
    }

}