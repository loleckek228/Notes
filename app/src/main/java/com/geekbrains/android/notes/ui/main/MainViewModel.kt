package com.geekbrains.android.notes.ui.main

import androidx.annotation.VisibleForTesting
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.model.NoteResult
import com.geekbrains.android.notes.ui.base.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(val repository: NotesRepository) : BaseViewModel<List<Note>?>() {

    private val notesChannel = repository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when (it) {
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()

        notesChannel.cancel()
    }
}