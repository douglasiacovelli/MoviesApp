package com.iacovelli.moviesapp.common

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

open class BaseActivity<T: BaseViewModel>: AppCompatActivity() {

    lateinit var viewModel: T

    fun observeScreenStatus() {
        viewModel.screenStatus.observe(this, Observer {
            it?.message?.let {
                showToast(it)
            }
        })
    }

    private fun showToast(message: Int, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, message, length).show()
    }
}