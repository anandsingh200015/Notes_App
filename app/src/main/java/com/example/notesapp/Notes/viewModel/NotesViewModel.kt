package com.example.notesapp.Notes.viewModel

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Notes.Repo.NotesRepo
import com.example.notesapp.Notes.api.NotesAPI
import com.example.notesapp.Notes.model.NotesRequest
import com.example.notesapp.Notes.model.NotesResponseItem
import com.example.notesapp.Notes.model.NotesResult
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(val notesRepo: NotesRepo) : ViewModel() {

    private val _notesState = MutableSharedFlow<List<NotesResponseItem>>()
    val notesState = _notesState.asSharedFlow()

    private var _isLoading = mutableStateOf(false)
    val isLoading : Boolean
        get() = _isLoading.value

    private val _notesRequestState = MutableSharedFlow<NotesResult>()
     val notesRequestState = _notesRequestState.asSharedFlow()


    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            _isLoading.value = true
           val notesResult = notesRepo.getNotes()
            _isLoading.value = false
            if (notesResult is NotesResult.Success){
                val notes = notesResult.notes
                _notesState.emit(notes)
            }
        }
    }

    fun sendNotesRequest(noteTitle: String, noteDescription: String) {
        viewModelScope.launch {
            val notesRequestData = NotesRequest(noteDescription, noteTitle)
            _notesRequestState.emit(notesRepo.saveNotes(notesRequestData))
            getNotes()
        }
    }


    fun updateNote(noteId: String, noteTitle: String, noteDescription: String) {
        viewModelScope.launch {
            val updatedNote = NotesRequest(noteDescription, noteTitle)
            _notesRequestState.emit(notesRepo.updateNote(noteId, updatedNote))
            getNotes()
        }

    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            _notesRequestState.emit(notesRepo.deleteNote(noteId))
            getNotes()
        }
    }
}







