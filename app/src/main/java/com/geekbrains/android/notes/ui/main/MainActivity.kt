package com.geekbrains.android.notes.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.geekbrains.android.notes.R
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseActivity
import com.geekbrains.android.notes.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main
    lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        notes_recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRecyclerViewAdapter() {
            NoteActivity.start(this, it.id)
        }
        notes_recyclerView.adapter = adapter

        floatingButton.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}