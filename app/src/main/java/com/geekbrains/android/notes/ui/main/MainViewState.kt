package com.geekbrains.android.notes.ui.main

import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
        BaseViewState<List<Note>?>(notes, error)
