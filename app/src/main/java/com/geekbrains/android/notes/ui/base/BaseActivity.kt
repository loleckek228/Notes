package com.geekbrains.android.notes.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity<T,S: BaseViewState<T>> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        setSupportActionBar(toolbar)

        viewModel.getViewStateLiveData().observe(this, Observer { state ->
            state.error?.let {
                renderError(it)
            } ?: renderData(state.data)
        })
    }

    protected fun renderError(error: Throwable?) {
        error?.message?.let {
            showError(it)
        }
    }

    fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    abstract fun renderData(data: T)
}