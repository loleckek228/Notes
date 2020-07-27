package com.geekbrains.android.notes.ui.note

import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)