package com.geekbrains.android.notes.di

import com.geekbrains.android.notes.data.dataSource.IDataSource
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.dataSource.FirebaseDataSource
import com.geekbrains.android.notes.ui.main.MainViewModel
import com.geekbrains.android.notes.ui.note.NoteViewModel
import com.geekbrains.android.notes.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { FirebaseDataSource(get(), get()) } bind IDataSource::class
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { NotesRepository(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}