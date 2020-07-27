package com.geekbrains.android.notes.data

import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.provider.FirestoreProvider

object NotesRepository {
    private val dataSource: DataSource = FirestoreProvider()

    fun getNotes() = dataSource.subscribeToAllNotes()
    fun getNoteById(id: String) = dataSource.getNoteById(id)
    fun saveNote(note: Note) = dataSource.saveNote(note)
}