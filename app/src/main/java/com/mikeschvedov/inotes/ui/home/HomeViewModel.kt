package com.mikeschvedov.inotes.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mikeschvedov.inotes.data.database.AppDatabase
import com.mikeschvedov.inotes.data.database.Repository
import com.mikeschvedov.inotes.data.database.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application){

    private val repository: Repository

    init {

        val userDao = AppDatabase.getDatabase(application).notesDao()
        repository = Repository(userDao)

    }

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