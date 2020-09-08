package com.geekbrains.android.notes.data.dataSource

import androidx.lifecycle.LiveData
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.entity.User
import com.geekbrains.android.notes.data.model.NoteResult

interface IDataSource {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User>
    fun deleteNote(noteId: String): LiveData<NoteResult>
}