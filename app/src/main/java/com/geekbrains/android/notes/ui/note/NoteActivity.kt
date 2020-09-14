package com.geekbrains.android.notes.ui.note

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.geekbrains.android.notes.R
import com.geekbrains.android.notes.common.getColorInt
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<NoteViewState>() {

    companion object {
        const val EXTRA_NOTE = "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    private var note: Note? = null
    override val layoutRes: Int = R.layout.activity_note
    override val viewModel: NoteViewModel by viewModel()

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let {
            if (savedInstanceState == null) {
                viewModel.loadNote(it)
            }
        } ?: let {
            supportActionBar?.title = getString(R.string.note_new)
            initView()
        }
    }

    override fun renderData(data: NoteViewState) {
        if(data.isDeleted) finish()

        this.note = data.note

        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(it.lastChanged)
        } ?: let {
            getString(R.string.note_new)
        }

        initView()
    }

    private fun initView() {
        note?.let {
            editText_title.removeTextChangedListener(textChangeListener)
            editText_body.removeTextChangedListener(textChangeListener)

            if (editText_title.text.toString() != it.title) editText_title.setTextKeepState(it.title)
            if (editText_body.text.toString() != it.text) editText_body.setTextKeepState(it.text)

            editText_title.addTextChangedListener(textChangeListener)
            editText_body.addTextChangedListener(textChangeListener)

            toolbar.setBackgroundColor(it.color.getColorInt(this))
        }

        onColorPickerClickBehavior()
    }

    private fun onColorPickerClickBehavior() {
        colorPicker.onColorClickListener = {
            note = note?.copy(color = it)
            toolbar.setBackgroundColor(it.getColorInt(this))
            saveNote()
        }
    }

    fun saveNote() {
        if (editText_title.text.isNullOrBlank()) return

        note = note?.copy(
                title = editText_title.text.toString(),
                text = editText_body.text.toString(),
                lastChanged = Date()
        ) ?: Note(UUID.randomUUID().toString(),
                title = editText_title.text.toString(),
                text = editText_body.text.toString())

        note?.let { viewModel.save(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this).inflate(R.menu.note, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePallete().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    fun togglePallete() {
        if(colorPicker.isOpen){
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    fun deleteNote() {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.note_delete_message))
                .setPositiveButton(getString(R.string.positiveButton_ok)) { dialog, which -> viewModel.deleteNote() }
                .setNegativeButton(getString(R.string.negativeButton_cancel)) { dialog, which -> dialog.dismiss() }
                .show()
    }
}