package com.iacovelli.moviesapp.common

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class BasePresenterTest {

    @Test
    fun testHandleErrorForIOException() {
        val presenter = instantiatePresenter()
        val ioException = IOException("lack of internet")
        presenter.setTryAgain(ioException)
    }

    @Test
    fun testHandleErrorForNonIoException() {
        val presenter = instantiatePresenter()
        val unexpectedError = IllegalStateException("unexpected")
        presenter.setTryAgain(unexpectedError)
    }

    private fun instantiatePresenter() = BaseViewModel()
}