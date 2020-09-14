package com.geekbrains.android.notes.data.dataSource

import androidx.lifecycle.LiveData
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.entity.User
import com.geekbrains.android.notes.data.model.NoteResult
import kotlinx.coroutines.channels.ReceiveChannel

interface IDataSource {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)
}