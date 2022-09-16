package com.mikeschvedov.inotes.ui.add_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikeschvedov.inotes.data.database.Repository
import com.mikeschvedov.inotes.data.database.entities.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
     private val repository: Repository
) : ViewModel() {

    fun addNoteToDB(content: String){
        val unixTime = System.currentTimeMillis() / 1000L
        val note = Note(content = content, date =  unixTime)

        viewModelScope.launch (Dispatchers.IO){
            repository.addNote(note)
        }
    }
}