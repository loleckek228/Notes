package com.geekbrains.android.notes.ui.splash

import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.error.NoAuthException
import com.geekbrains.android.notes.ui.base.BaseViewModel

class SplashViewModel(): BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        NotesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }

}