package com.mikeschvedov.inotes.data.database

import com.mikeschvedov.inotes.data.database.entities.Note

interface Repository  {

    fun addNote(note: Note)

    suspend fun getAllNotes() : List<Note>

    fun deleteNote(note: Note)

}