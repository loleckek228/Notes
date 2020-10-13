package com.geekbrains.android.notes.data

import com.geekbrains.android.notes.data.dataSource.IDataSource
import com.geekbrains.android.notes.data.entity.Note

class NotesRepository(val provider: IDataSource) {
    fun getNotes() = provider.subscribeToAllNotes()
    suspend fun getNoteById(id: String) = provider.getNoteById(id)
    suspend fun saveNote(note: Note) = provider.saveNote(note)
    suspend fun getCurrentUser() = provider.getCurrentUser()
    suspend fun deleteNote(noteId: String) = provider.deleteNote(noteId)
}