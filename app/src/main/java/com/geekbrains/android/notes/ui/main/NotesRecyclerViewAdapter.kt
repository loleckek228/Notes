package com.geekbrains.android.notes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.android.notes.R
import com.geekbrains.android.notes.data.entity.Note
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRecyclerViewAdapter : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {
    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.item_note,
                    parent,
                    false
            )
    )

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(notes[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(note) {
            itemView.textView_title.text = title
            itemView.textView_text.text = text
            itemView.setBackgroundColor(color)
        }
    }
}