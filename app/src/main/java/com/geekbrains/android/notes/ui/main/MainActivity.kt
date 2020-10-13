package com.geekbrains.android.notes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.geekbrains.android.notes.R
import com.geekbrains.android.notes.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        adapter = NotesRecyclerViewAdapter {
            NoteActivity.start(this, it)
        }

        notes_recyclerView.layoutManager = GridLayoutManager(this, 2)
        notes_recyclerView.adapter = adapter

        viewModel.viewState().observe(this, Observer { state ->
            state?.let { adapter.notes = it.notes }
        })

        floatingButton.setOnClickListener{
            NoteActivity.start(this, null)
        }
    }
}