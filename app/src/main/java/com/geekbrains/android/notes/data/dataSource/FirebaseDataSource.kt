package com.geekbrains.android.notes.data.dataSource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekbrains.android.notes.data.model.NoteResult
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.entity.User
import com.geekbrains.android.notes.data.error.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseDataSource(val store: FirebaseFirestore, val auth: FirebaseAuth) : IDataSource {
    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = auth.currentUser

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun subscribeToAllNotes(): ReceiveChannel<NoteResult> = Channel<NoteResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null
        try {
            registration = getUserNotesCollection().addSnapshotListener { snapshot, e ->
                val value = e?.let {
                    NoteResult.Error(e)
                } ?: snapshot?.let {
                    val notes = snapshot.documents.map { doc -> doc.toObject(Note::class.java) }
                    NoteResult.Success(notes)
                }

                value?.let { offer(it) }
            }
        } catch (e: Throwable) {
            offer(NoteResult.Error(e))
        }

        invokeOnClose { registration?.remove() }
    }

    override suspend fun getNoteById(id: String): Note = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(id).get()
                    .addOnSuccessListener { snapshot ->
                        continuation.resume(snapshot.toObject(Note::class.java)!!)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun saveNote(note: Note): Note = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(note.id).set(note)
                    .addOnSuccessListener {
                        continuation.resume(note)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        continuation.resume(currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        })
    }


    override suspend fun deleteNote(noteId: String): Unit = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(noteId).delete()
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }
}