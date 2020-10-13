package com.geekbrains.android.notes.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.android.notes.R
import com.geekbrains.android.notes.data.entity.Note
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        fun start(context: Context, note: Note? = null) =
                Intent(context, NoteActivity::class.java).run {
                    putExtra(EXTRA_NOTE, note)
                    context.startActivity(this)
                }
    }

    private var note: Note? = null

    lateinit var viewModel: NoteViewModel

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = saveNote()
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra(EXTRA_NOTE)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(it.lastChanged)
        } ?: getString(R.string.new_note)

        initView()
    }

    private fun initView() {
        note?.let {
            textInputEditText_title.setText(it.title)
            editText_body.setText(it.text)
            val color = when (it.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
            }

            toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, color, null))
        }

        textInputEditText_title.addTextChangedListener(textChangeListener)
        editText_body.addTextChangedListener(textChangeListener)
    }

    private fun saveNote() {
        if (textInputEditText_title.text.isNullOrBlank()) return

        note = note?.copy(
                title = textInputEditText_title.text.toString(),
                text = editText_body.text.toString(),
                lastChanged = Date()
        ) ?: Note(
                UUID.randomUUID().toString(),
                title = textInputEditText_title.text.toString(),
                text = editText_body.text.toString()
        )

        note?.let { viewModel.save(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}