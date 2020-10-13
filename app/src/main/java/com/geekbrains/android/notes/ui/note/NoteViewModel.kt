package com.geekbrains.android.notes.ui.note

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.geekbrains.android.notes.data.model.NoteResult
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.common.observeOnce
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseViewModel

class NoteViewModel(val repository: NotesRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeOnce(Observer { result ->
            viewStateLiveData.value = when (result) {
                is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(note = result.data as? Note))
                is NoteResult.Error -> NoteViewState(error = result.error)
            }
        })
    }

    fun deleteNote() {
        pendingNote?.let {
            repository.deleteNote(it.id).observeOnce(Observer { result ->
                result ?: return@Observer
                viewStateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }
            })
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }
    }
}