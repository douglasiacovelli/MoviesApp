package com.iacovelli.moviesapp.tvshowlist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.iacovelli.moviesapp.RxOverridingRule
import com.iacovelli.moviesapp.common.ScreenStatus
import com.iacovelli.moviesapp.common.configuration.FetchConfiguration
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.iacovelli.moviesapp.models.SimpleConfiguration
import com.iacovelli.moviesapp.models.TvShowResponse
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class TvShowListViewModelTest {

    private val getTvShowList: GetTvShowList = mock()
    private val fetchConfiguration: FetchConfiguration = mock()
    private val getCachedConfiguration: GetCachedConfiguration = mock()
    private val tvShowResponse: TvShowResponse = mock()
    private val simpleConfiguration: SimpleConfiguration = mock()

    @get:Rule
    val rule = RuleChain
            .outerRule(InstantTaskExecutorRule())
            .around(RxOverridingRule())

    @Before
    fun before() {
        given(getCachedConfiguration.execute()).willReturn(simpleConfiguration)
        given(getTvShowList.execute(1)).willReturn(Single.just(tvShowResponse))
        given(tvShowResponse.page).willReturn(1)
    }

    @Test
    fun init_GetConfigurationFromCacheAndTvShowListSuccessfully() {
        given(getCachedConfiguration.execute()).willReturn(simpleConfiguration)

        val viewModel = withViewModel()
        assertEquals(tvShowResponse, viewModel.tvShowResponse.value)

        verify(getCachedConfiguration).execute()
        verify(fetchConfiguration, never()).execute()
        verify(getTvShowList, only()).execute(1)
    }

    @Test
    fun init_GetConfigurationNoCacheAndTvShowListSuccessfully() {
        given(getCachedConfiguration.execute()).willReturn(null)
        given(fetchConfiguration.execute()).willReturn(Single.just(simpleConfiguration))

        val viewModel = withViewModel()

        assertEquals(tvShowResponse, viewModel.tvShowResponse.value)
        verify(getCachedConfiguration).execute()
        verify(fetchConfiguration, only()).execute()
        verify(getTvShowList, only()).execute(1)
    }

    @Test
    fun fetchNextPage_BringsResultSuccessfully() {
        given(getCachedConfiguration.execute()).willReturn(null)
        given(fetchConfiguration.execute()).willReturn(Single.just(simpleConfiguration))

        val viewModel = withViewModel()

        val secondPageResults: TvShowResponse = mock()
        given(getTvShowList.execute(2)).willReturn(Single.just(secondPageResults))

        viewModel.fetchNextPage().subscribe({}, { fail() })

        assertEquals(secondPageResults, viewModel.tvShowResponse.value)

        verify(getCachedConfiguration).execute()
        verify(fetchConfiguration, only()).execute()
        verify(getTvShowList, times(1)).execute(1)
        verify(getTvShowList, times(1)).execute(2)
    }

    @Test
    fun init_ErrorWhileFetchingConfiguration() {
        given(getCachedConfiguration.execute()).willReturn(null)
        given(fetchConfiguration.execute()).willReturn(Single.error(IOException()))

        val viewModel = withViewModel()
        assertEquals(ScreenStatus.Status.TRY_AGAIN, viewModel.screenStatus.value?.status)
        assertEquals(null, viewModel.tvShowResponse.value)

        verify(fetchConfiguration, only()).execute()
        verify(getTvShowList, never()).execute(any())
    }

    @Test
    fun init_ErrorWhileFetchingTvShowList() {
        given(getTvShowList.execute(1)).willReturn(Single.error(IOException()))
        val viewModel = withViewModel()

        assertEquals(ScreenStatus.Status.TRY_AGAIN, viewModel.screenStatus.value?.status)
        assertEquals(null, viewModel.tvShowResponse.value)
    }

    private fun withViewModel() = TvShowListViewModel(getTvShowList, fetchConfiguration, getCachedConfiguration)
}