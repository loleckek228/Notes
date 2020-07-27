package com.geekbrains.android.notes.ui.note

import com.geekbrains.android.notes.data.model.NoteResult
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseViewModel

class NoteViewModel() : BaseViewModel<Note?, NoteViewState>() {
    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }


    fun loadNote(noteId: String) {
        NotesRepository.getNoteById(noteId).observeForever { result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = result.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}