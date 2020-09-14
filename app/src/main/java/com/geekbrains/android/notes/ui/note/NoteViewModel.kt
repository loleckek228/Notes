package com.geekbrains.android.notes.ui.note

import androidx.annotation.VisibleForTesting
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NoteViewModel(val repository: NotesRepository) : BaseViewModel<NoteViewState>() {
    private val pendingNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteViewState(note = note))
    }

    fun loadNote(noteId: String) = launch {
        try {
            repository.getNoteById(noteId).let {
                setData(NoteViewState(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let {
                repository.deleteNote(it.id)
                setData(NoteViewState(isDeleted = true))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingNote?.let {
                repository.saveNote(it)
            }
        }
    }
}