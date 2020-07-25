package com.geekbrains.android.notes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekbrains.android.notes.data.entity.Note
import java.util.*

object NotesRepository {
    private val notesLiveData = MutableLiveData<List<Note>>()

    internal val notes: MutableList<Note> = mutableListOf(
            Note(
                    UUID.randomUUID().toString(),
                    "Первая заметка",
                    "Текст первой заметки. Короткий, но интересный",
                    Note.Color.WHITE
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Вторая заметка",
                    "Текст второй заметки. Короткий, но интересный",
                    Note.Color.YELLOW
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Третья заметка",
                    "Текст третьей заметки. Короткий, но интересный",
                    Note.Color.RED
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Четвертая заметка",
                    "Текст четверой заметки. Короткий, но интересный",
                    Note.Color.BLUE
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Пятая заметка",
                    "Текст пятой заметки. Короткий, но интересный",
                    Note.Color.VIOLET
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Шестая заметка",
                    "Текст шестой заметки. Короткий, но интересный",
                    Note.Color.GREEN
            )
    )

    init {
        notesLiveData.value = notes
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        for (i in 0 until notes.size) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }

        notes.add(note)
    }


    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }
}