package com.mikeschvedov.inotes.data.database

import com.mikeschvedov.inotes.data.database.daos.NotesDao
import com.mikeschvedov.inotes.data.database.entities.Note
import javax.inject.Inject

class RoomRepositoryIMPL @Inject constructor(private var notesDao: NotesDao) : Repository {


   override fun addNote(note: Note) = notesDao.addNote(note)

    override suspend fun getAllNotes() : List<Note> = notesDao.getAllNotes()

    override fun deleteNote(note: Note) = notesDao.deleteNote(note)

}