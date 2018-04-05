package com.iacovelli.moviesapp.common

import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.configuration.Cache
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import junit.framework.Assert.assertNull
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class BasePresenterTest {

    private val contract: BaseContract = mock()
    private val defaultFakeCache = FakeCache(true)

    @Test
    fun testGetConfigurationCached() {
        val presenter = instantiatePresenter(FakeCache(true))
        assertEquals(FAKE_URL, presenter.configuration?.baseUrl)
        assertEquals(FAKE_BACKDROP_SIZE, presenter.configuration?.backdropSize)
        assertEquals(FAKE_POSTER_SIZE, presenter.configuration?.posterSize)
    }

    @Test
    fun testGetConfigurationCachedNoCache() {
        val presenter = instantiatePresenter(FakeCache(false))
        assertNull(presenter.configuration)
    }

    @Test
    fun testHandleErrorForIOException() {
        val presenter = instantiatePresenter(defaultFakeCache)
        val ioException = IOException("lack of internet")
        presenter.handleError(ioException)
        verify(contract).showMessage(R.string.connection_error)
    }

    @Test
    fun testHandleErrorForNonIoException() {
        val presenter = instantiatePresenter(defaultFakeCache)
        val unexpectedError = IllegalStateException("unexpected")
        presenter.handleError(unexpectedError)
        verify(contract).showMessage(R.string.unexpected_error)
    }

    @Test
    fun testClearDisposable() {
        val presenter = instantiatePresenter(defaultFakeCache)
        val disposable = Single.just("").subscribe({}, {})
        presenter.compositeDisposable.add(disposable)
        presenter.onDestroy()
        assertEquals(0, presenter.compositeDisposable.size())
    }

    private fun instantiatePresenter(cache: Cache) =
            BasePresenter(contract, GetCachedConfiguration(cache))
}