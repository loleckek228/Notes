package com.geekbrains.android.notes.ui.main

import androidx.lifecycle.Observer
import com.geekbrains.android.notes.data.model.NoteResult
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer { result: NoteResult ->
        when(result){
            is NoteResult.Success<*> -> viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }


    override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }
}