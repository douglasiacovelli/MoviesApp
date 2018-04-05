package com.iacovelli.moviesapp.common

import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertNull
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BasePresenterTest {

    private val contract: BaseContract = mock()

    @Test
    fun testGetConfigurationCached() {
        val fakeCache = FakeCache(true)
        val presenter = BasePresenter(contract, GetCachedConfiguration(fakeCache))
        assertEquals(FAKE_URL, presenter.configuration?.baseUrl)
        assertEquals(FAKE_BACKDROP_SIZE, presenter.configuration?.backdropSize)
        assertEquals(FAKE_POSTER_SIZE, presenter.configuration?.posterSize)
    }

    @Test
    fun testGetConfigurationCachedNoCache() {
        val fakeCache = FakeCache(false)
        val presenter = BasePresenter(contract, GetCachedConfiguration(fakeCache))
        assertNull(presenter.configuration)
    }

}