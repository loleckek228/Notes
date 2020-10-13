package com.geekbrains.android.notes.data

import com.geekbrains.android.notes.data.dataSource.IDataSource
import com.geekbrains.android.notes.data.entity.Note

class NotesRepository(val provider: IDataSource) {
    fun getNotes() = provider.subscribeToAllNotes()
    fun getNoteById(id: String) = provider.getNoteById(id)
    fun saveNote(note: Note) = provider.saveNote(note)
    fun getCurrentUser() = provider.getCurrentUser()
    fun deleteNote(noteId: String) = provider.deleteNote(noteId)
}