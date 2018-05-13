package com.iacovelli.moviesapp.details

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.iacovelli.moviesapp.RxOverridingRule
import com.iacovelli.moviesapp.common.ItemTvShowFactory
import com.iacovelli.moviesapp.common.ui.ItemTvShowPresenter
import com.iacovelli.moviesapp.models.TvShow
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    val rule = RuleChain
            .outerRule(InstantTaskExecutorRule())
            .around(RxOverridingRule())

    private val getTvShow: GetTvShow = mock()
    private val tvShow: TvShow = mock()
    private val itemTvShowFactory: ItemTvShowFactory = mock()
    private val itemTvShowPresenter: ItemTvShowPresenter = mock()

    @Before
    fun before() {
        given(itemTvShowFactory.create(any(), anyOrNull())).willReturn(itemTvShowPresenter)
    }

    @Test
    fun init_GetTvShowSuccessfully() {
        given(getTvShow.execute(9)).willReturn(Single.just(tvShow))

        val viewModel = DetailsViewModel(9, getTvShow, itemTvShowFactory)

        assertEquals(itemTvShowPresenter, viewModel.tvShow.value)
    }
}

