package com.geekbrains.android.notes.ui.note

import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseViewState

class NoteViewState(val isDeleted: Boolean = false, val note: Note? = null)