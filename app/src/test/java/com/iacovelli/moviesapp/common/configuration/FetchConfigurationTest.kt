package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.FakeCache
import com.iacovelli.moviesapp.ReadFixture
import com.iacovelli.moviesapp.common.io.CONFIG_BACKDROP_SIZE
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_POSTER_SIZE
import com.iacovelli.moviesapp.common.io.MoviesRetrofit
import junit.framework.Assert.assertFalse
import junit.framework.Assert.fail
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test

class FetchConfigurationTest {

    @Test
    fun testProcessingConfiguration() {
        val mockWebServer = MockWebServer()
        MoviesRetrofit.BASE_URL = mockWebServer.url("").toString()

        val fixture = ReadFixture().execute("Configuration.json")

        mockWebServer.enqueue(MockResponse().setBody(fixture))
        val fakeCache = FakeCache(false)
        assertFalse(fakeCache.contains(CONFIG_BASE_URL))
        val fetchConfiguration = FetchConfiguration(fakeCache)
        fetchConfiguration.execute()
                .subscribe({}, { fail() })

        assertEquals("https://image.tmdb.org/t/p/", fakeCache.getString(CONFIG_BASE_URL))
        assertEquals("w780", fakeCache.getString(CONFIG_BACKDROP_SIZE))
        assertEquals("w500", fakeCache.getString(CONFIG_POSTER_SIZE))
        mockWebServer.shutdown()
    }
}