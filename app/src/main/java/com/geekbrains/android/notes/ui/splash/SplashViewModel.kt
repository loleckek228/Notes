package com.geekbrains.android.notes.ui.splash

import androidx.lifecycle.Observer
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.common.observeOnce
import com.geekbrains.android.notes.data.error.NoAuthException
import com.geekbrains.android.notes.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(val repository: NotesRepository) : BaseViewModel<Boolean>() {

    fun requestUser() = launch {
        repository.getCurrentUser()?.let {
            setData(true)
        } ?: let {
            setError(NoAuthException())
        }
    }
}