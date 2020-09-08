package com.geekbrains.android.notes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.android.notes.R
import com.geekbrains.android.notes.common.getColorInt
import com.geekbrains.android.notes.data.entity.Note
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRecyclerViewAdapter(val onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {
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

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView ), LayoutContainer {
        fun bind(note: Note) = with(itemView) {
            textView_title.text = note.title
            textView_text.text = note.text

            setBackgroundColor(note.color.getColorInt(context))

            setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }
}