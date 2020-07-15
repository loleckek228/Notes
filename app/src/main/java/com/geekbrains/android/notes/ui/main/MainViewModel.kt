package com.geekbrains.android.notes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.android.notes.data.NotesRepository

class MainViewModel : ViewModel() {
    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        viewStateLiveData.value = MainViewState(NotesRepository.notes)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}