package com.geekbrains.android.notes.data

import androidx.lifecycle.LiveData
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.entity.User
import com.geekbrains.android.notes.data.model.NoteResult

interface DataSource {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User>
}