package com.mikeschvedov.inotes.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikeschvedov.inotes.data.database.Repository
import com.mikeschvedov.inotes.data.database.entities.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
     private val repository: Repository
) : ViewModel(){

    private val _listOfNotes = MutableLiveData<List<Note>>()
    val listOfNotes: LiveData<List<Note>> get() = _listOfNotes

    fun populateList() {
        viewModelScope.launch (Dispatchers.IO){
            val allNotes = repository.getAllNotes()
            _listOfNotes.postValue(allNotes)
        }
    }

    fun deleteNoteFromDB(note: Note) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteNote(note)
        }
        // Also refreshList
        populateList()
    }
}