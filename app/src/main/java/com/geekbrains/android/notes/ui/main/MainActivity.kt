package com.geekbrains.android.notes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import com.geekbrains.android.notes.R
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.ui.base.BaseActivity
import com.geekbrains.android.notes.ui.note.NoteActivity
import com.geekbrains.android.notes.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: MainViewModel by viewModel()
    override val layoutRes = R.layout.activity_main
    lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        notes_recyclerView.layoutManager = GridLayoutManager(this, 1)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this)
            .inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }

    private fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG)
                ?: LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
    }
}