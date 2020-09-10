package com.geekbrains.android.notes.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.geekbrains.android.notes.data.NotesRepository
import com.geekbrains.android.notes.data.entity.Note
import com.geekbrains.android.notes.data.model.NoteResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<NoteResult>()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        clearAllMocks()

        every { mockRepository.getNotes() } returns notesLiveData

        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun `should call getNotes once`() {
        verify(exactly = 1) { mockRepository.getNotes() }
    }

    @Test
    fun `should returns Notes`() {
        var result: List<Note>? = null
        val testData = listOf(Note("1"), Note("2"))

        viewModel.getViewStateLiveData().observeForever {
            result = it?.data
        }

        notesLiveData.value = NoteResult.Success(testData)

        assertEquals(testData, result)
    }

    @Test
    fun `should returns error`() {
        var result: Throwable? = null
        val testData = Throwable("error")

        viewModel.getViewStateLiveData().observeForever {
            result = it?.error
        }

        notesLiveData.value = NoteResult.Error( error = testData)

        assertEquals(testData, result)
    }

    @Test
    fun `should remove observer`() {
        viewModel.onCleared()

        assertFalse(notesLiveData.hasObservers())
    }
}
