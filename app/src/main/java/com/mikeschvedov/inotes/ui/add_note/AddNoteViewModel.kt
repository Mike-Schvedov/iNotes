package com.mikeschvedov.inotes.ui.add_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mikeschvedov.inotes.data.database.AppDatabase
import com.mikeschvedov.inotes.data.database.Repository
import com.mikeschvedov.inotes.data.database.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    init {
        val userDao = AppDatabase.getDatabase(application).notesDao()
        repository = Repository(userDao)
    }

    fun addNoteToDB(content: String){
        val unixTime = System.currentTimeMillis() / 1000L
        val note = Note(content = content, date =  unixTime)

        viewModelScope.launch (Dispatchers.IO){
            repository.addNote(note)
        }
    }
}