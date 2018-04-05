package com.iacovelli.moviesapp.common

import com.iacovelli.moviesapp.R
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class BasePresenterTest {

    private val contract: BaseContract = mock()

    @Test
    fun testHandleErrorForIOException() {
        val presenter = instantiatePresenter()
        val ioException = IOException("lack of internet")
        presenter.handleError(ioException)
        verify(contract).showMessage(R.string.connection_error)
    }

    @Test
    fun testHandleErrorForNonIoException() {
        val presenter = instantiatePresenter()
        val unexpectedError = IllegalStateException("unexpected")
        presenter.handleError(unexpectedError)
        verify(contract).showMessage(R.string.unexpected_error)
    }

    @Test
    fun testClearDisposable() {
        val presenter = instantiatePresenter()
        val disposable = Single.just("").subscribe({}, {})
        presenter.compositeDisposable.add(disposable)
        presenter.onDestroy()
        assertEquals(0, presenter.compositeDisposable.size())
    }

    private fun instantiatePresenter() = BasePresenter(contract)
}