package com.iacovelli.moviesapp.common.ui

import com.iacovelli.moviesapp.models.TvShow
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemTvShowPresenterTest {

    private val tvShowMock: TvShow = mock()

    @Test
    fun rating_From7_5To3_75() {
        given(tvShowMock.voteAverage).willReturn(7.5f)
        val presenter = withPresenter(tvShowMock)
        assertEquals(3.75f, presenter.rating)
    }

    @Test
    fun onClick_InvokesCallback() {
        val callback: (tvShow: TvShow) -> Unit = mock()
        val presenter = withPresenter(tvShowMock, callback)
        presenter.onClick()

        verify(callback).invoke(tvShowMock)
    }

    private fun withPresenter(tvShow: TvShow, callback: ((tvShow: TvShow) -> Unit)? = null)
            = ItemTvShowPresenter(tvShow, callback)

}