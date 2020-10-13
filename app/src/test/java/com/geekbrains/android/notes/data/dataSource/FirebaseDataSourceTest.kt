package com.geekbrains.android.notes.data.dataSource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.model.NoteResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.squareup.okhttp.internal.DiskLruCache
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class FirebaseDataSourceTest {

    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()

    private val mockUsersCollection = mockk<CollectionReference>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUserDocument = mockk<DocumentReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val dataSource = FirebaseDataSource(mockDb, mockAuth)

    @Before
    fun setUp() {
        clearAllMocks()

        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]
    }

    @Test
    fun `subscribeToAllNotes`() = runBlocking {
        var result: List<Note>?

        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""

        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        launch {
            slot.captured.onEvent(mockSnapshot, null)
        }

        result = (dataSource.subscribeToAllNotes().receive() as? NoteResult.Success<List<Note>>)?.data

        assertEquals(testNotes, result)
    }

    @Test
    fun `SavedNote`() = runBlocking {
        var result: Note? = null
        val mockSnaphot = mockk<Void>()
        val slot = slot<OnSuccessListener<in Void>>()

        every {
            mockResultCollection.document(testNotes[0].id).set(testNotes[0])
                    .addOnSuccessListener(capture(slot))
                    .addOnFailureListener(any())
        } returns mockk()

        launch {
            delay(1000)
            slot.captured.onSuccess(mockSnaphot)
        }

        result = runBlocking { dataSource.saveNote(testNotes[0]) }
        assertEquals(result, testNotes[0])
    }

    @Test
    fun `deleteNote`() = runBlocking {
        var result: Unit? = null
        val mockSnaphot = mockk<Void>()
        val slot = slot<OnSuccessListener<in Void>>()

        every {
            mockResultCollection.document(testNotes[0].id).delete()
                    .addOnSuccessListener(capture(slot))
                    .addOnFailureListener(any())
        } returns mockk()

        launch {
            delay(1000)
            slot.captured.onSuccess(mockSnaphot)
        }

        result = runBlocking { dataSource.deleteNote(testNotes[0].id) }
        verify(exactly = 1) { mockResultCollection.document(testNotes[0].id).delete() }
    }
}

