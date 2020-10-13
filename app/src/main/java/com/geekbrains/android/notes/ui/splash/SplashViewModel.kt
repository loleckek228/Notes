package com.geekbrains.android.notes.ui.splash

import androidx.lifecycle.Observer
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.common.observeOnce
import com.geekbrains.android.notes.data.error.NoAuthException
import com.geekbrains.android.notes.ui.base.BaseViewModel

class SplashViewModel(val repository: NotesRepository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        repository.getCurrentUser().observeOnce(Observer { user ->
            viewStateLiveData.value = user?.let {
                SplashViewState(authenticated = true)
            } ?: SplashViewState(error = NoAuthException())
        })
    }
}