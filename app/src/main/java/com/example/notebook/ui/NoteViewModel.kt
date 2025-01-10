package com.example.notebook.ui

import androidx.lifecycle.*
import com.example.notebook.data.Note
import com.example.notebook.data.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes = repository.allNotes.asLiveData()
    
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
    
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
    
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
}
