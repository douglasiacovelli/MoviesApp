package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.FakeCache
import com.iacovelli.moviesapp.common.io.CONFIG_BACKDROP_SIZE
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_POSTER_SIZE
import com.iacovelli.moviesapp.models.Configuration
import com.iacovelli.moviesapp.models.ImageConfiguration
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import junit.framework.Assert.fail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchConfigurationTest {

    private val configuration: Configuration = mock()

    @Before
    fun setup() {
        val imageConfiguration: ImageConfiguration = mock()
        given(configuration.images).willReturn(imageConfiguration)
        given(imageConfiguration.backdropSizes).willReturn(listOf("w300","w780","w1280","original"))
        given(imageConfiguration.secureBaseUrl).willReturn("https://image.tmdb.org/t/p/")
        given(imageConfiguration.posterSizes).willReturn(listOf("w92","w154","w185","w342","w500","w780","original"))
    }

    @Test
    fun execute_SaveToCacheSuccessfully() {

        val configurationService: ConfigurationService = mock()
        val fakeCache = FakeCache(false)
        given(configurationService.getConfiguration()).willReturn(Single.just(configuration))

        val fetchConfiguration = FetchConfiguration(fakeCache, configurationService)
        fetchConfiguration.execute()
                .subscribe({}, { fail() })

        assertEquals("https://image.tmdb.org/t/p/", fakeCache.getString(CONFIG_BASE_URL))
        assertEquals("w780", fakeCache.getString(CONFIG_BACKDROP_SIZE))
        assertEquals("w500", fakeCache.getString(CONFIG_POSTER_SIZE))
    }
}