package com.geekbrains.android.notes.ui.splash

import com.geekbrains.android.notes.ui.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null, error: Throwable? = null):
        BaseViewState<Boolean?>(authenticated, error)