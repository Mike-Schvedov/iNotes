package com.mikeschvedov.inotes.data.database

import com.mikeschvedov.inotes.data.database.daos.NotesDao
import com.mikeschvedov.inotes.data.database.entities.Note

class Repository(private val notesDao: NotesDao) {

    fun addNote(note: Note) = notesDao.addNote(note)

    fun getAllNotes() : List<Note> = notesDao.getAllNotes()

    fun deleteNote(note: Note) = notesDao.deleteNote(note)

}