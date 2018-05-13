package com.iacovelli.moviesapp.common

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.RxOverridingRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class BaseViewModelTest {

    @get:Rule
    val rule = RuleChain
            .outerRule(InstantTaskExecutorRule())
            .around(RxOverridingRule())

    @Test
    fun handlError_ForTryAgainWithIOException() {
        val viewModel = withViewModel()
        val ioException = IOException("lack of internet")

        viewModel.setTryAgain(ioException)
        assertEquals(ScreenStatus.Status.TRY_AGAIN, viewModel.screenStatus.value?.status)
        assertEquals(R.string.connection_error, viewModel.screenStatus.value?.message)
    }

    @Test
    fun handleError_ForTryAgainWithNonIOException() {
        val viewModel = withViewModel()
        val unexpectedException = IllegalStateException("unexpected")

        viewModel.setTryAgain(unexpectedException)
        assertEquals(ScreenStatus.Status.TRY_AGAIN, viewModel.screenStatus.value?.status)
        assertEquals(R.string.unexpected_error, viewModel.screenStatus.value?.message)
    }

    @Test
    fun setLoading() {
        val viewModel = withViewModel()

        viewModel.setLoading()
        assertEquals(ScreenStatus.Status.LOADING, viewModel.screenStatus.value?.status)
    }

    @Test
    fun setOK() {
        val viewModel = withViewModel()

        viewModel.setOk()
        assertEquals(ScreenStatus.Status.OK, viewModel.screenStatus.value?.status)
    }

    private fun withViewModel() = BaseViewModel()
}