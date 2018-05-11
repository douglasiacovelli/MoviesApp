package com.iacovelli.moviesapp.common.ui

import com.iacovelli.moviesapp.common.OpenTvShowContract
import com.iacovelli.moviesapp.models.TvShow
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemTvShowPresenterTest {

    private val contract: OpenTvShowContract = mock()

    @Test
    fun ratingFromSevenToThreeAndAHalf() {
        val presenter = instantiatePresenter(TvShow(1, "", 10f))
        assertEquals(5f, presenter.rating)
    }

    private fun instantiatePresenter(tvShow: TvShow) = ItemTvShowPresenter(contract, tvShow)
}