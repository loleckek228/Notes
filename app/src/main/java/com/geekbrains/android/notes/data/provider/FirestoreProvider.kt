package com.geekbrains.android.notes.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekbrains.android.notes.data.DataSource
import com.geekbrains.android.notes.data.model.NoteResult
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.entity.User
import com.geekbrains.android.notes.data.error.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreProvider : DataSource {
    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val store = FirebaseFirestore.getInstance()

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun subscribeToAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().addSnapshotListener { snapshot, e ->
                e?.let {
                    value = NoteResult.Error(e)
                } ?: snapshot?.let {
                    val notes = snapshot.documents.map { doc -> doc.toObject(Note::class.java) }
                    value = NoteResult.Success(notes)
                }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getNoteById(id: String): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(id).get()
                    .addOnSuccessListener { snapshot ->
                        value = NoteResult.Success(snapshot.toObject(Note::class.java))
                    }.addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(note.id).set(note)
                    .addOnSuccessListener { snapshot ->
                        value = NoteResult.Success(note)
                    }.addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getCurrentUser(): LiveData<User> = MutableLiveData<User>().apply {
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }

    }
}