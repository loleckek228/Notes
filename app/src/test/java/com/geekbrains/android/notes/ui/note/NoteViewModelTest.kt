package com.geekbrains.android.notes.ui.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.model.NoteResult
import com.geekbrains.android.notes.ui.main.MainViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class NoteViewModelTest {

//    @get: Rule
//    val taskExecutorRule = InstantTaskExecutorRule()
//
//    private val mockRepository = mockk<NotesRepository>(relaxed = true)
//    private val noteLiveData = MutableLiveData<NoteResult>()
//
//    private val testNote = Note("1", "2", "3")
//    private lateinit var viewModel: NoteViewModel
//
//    @Before
//    fun setUp() {
//        clearAllMocks()
//
//        every { mockRepository.getNoteById(testNote.id) } returns noteLiveData
//        every { mockRepository.deleteNote(testNote.id) } returns noteLiveData
//        every { mockRepository.saveNote(any()) } returns noteLiveData
//
//        viewModel = NoteViewModel(mockRepository)
//    }
//
//    @Test
//    fun `loadNote should return NoteData`() {
//        var result: NoteViewState.Data? = null
//        val testData = NoteViewState.Data(false, testNote)
//
//        viewModel.getViewStateLiveData().observeForever {
//            result = it?.data
//        }
//
//        viewModel.loadNote(testNote.id)
//
//        noteLiveData.value = NoteResult.Success(testNote)
//
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `should returns error`() {
//        var result: Throwable? = null
//        val testData = Throwable("error")
//
//        viewModel.getViewStateLiveData().observeForever {
//            result = it?.error
//        }
//
//        viewModel.loadNote(testNote.id)
//
//        noteLiveData.value = NoteResult.Error(error = testData)
//
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `deleteNote should return NoteData with isDeleted`() {
//        var result: NoteViewState.Data? = null
//        val testData = NoteViewState.Data(true, null)
//
//        viewModel.getViewStateLiveData().observeForever {
//            result = it?.data
//        }
//
//        viewModel.save(testNote)
//        viewModel.deleteNote()
//
//        noteLiveData.value = NoteResult.Success(null)
//
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `deleteNote should returns error`() {
//        var result: Throwable? = null
//        val testData = Throwable("error")
//
//        viewModel.getViewStateLiveData().observeForever {
//            result = it?.error
//        }
//
//        viewModel.save(testNote)
//        viewModel.deleteNote()
//
//        noteLiveData.value = NoteResult.Error(testData)
//
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `should save changes`() {
//        viewModel.save(testNote)
//        viewModel.onCleared()
//
//        verify(exactly = 1) { mockRepository.saveNote(testNote) }
//    }
}