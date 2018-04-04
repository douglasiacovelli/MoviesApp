package com.iacovelli.moviesapp.common

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseActivity: AppCompatActivity(), BaseContract {
    var presenter: BasePresenter? = null

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun getContext(): Context {
        return this
    }

    override fun showMessage(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
    }
}