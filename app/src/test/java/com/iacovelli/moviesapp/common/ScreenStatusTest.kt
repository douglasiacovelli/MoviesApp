package com.iacovelli.moviesapp.common

import android.view.View
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ScreenStatusTest {

    @Test
    fun loadingStatus_VisibilityIsVISIBLE() {
        val screenStatus = ScreenStatus(ScreenStatus.Status.LOADING, null)
        assertEquals(View.VISIBLE, screenStatus.loadingVisibility)
        assertEquals(View.GONE, screenStatus.tryAgainVisibility)
        assertEquals(View.VISIBLE, screenStatus.pageVisibility)
    }

    @Test
    fun tryAgainStatus_VisibilityIsVISIBLE() {
        val screenStatus = ScreenStatus(ScreenStatus.Status.TRY_AGAIN, null)
        assertEquals(View.VISIBLE, screenStatus.tryAgainVisibility)
        assertEquals(View.GONE, screenStatus.loadingVisibility)
        assertEquals(View.VISIBLE, screenStatus.pageVisibility)
    }

    @Test
    fun okStatus_VisibilityIsVISIBLE() {
        val screenStatus = ScreenStatus(ScreenStatus.Status.OK, null)
        assertEquals(View.GONE, screenStatus.pageVisibility)
        assertEquals(View.GONE, screenStatus.loadingVisibility)
        assertEquals(View.GONE, screenStatus.tryAgainVisibility)
    }
}