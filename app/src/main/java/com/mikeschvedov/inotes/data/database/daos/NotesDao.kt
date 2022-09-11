package com.mikeschvedov.inotes.data.database.daos

import androidx.room.*
import com.mikeschvedov.inotes.data.database.entities.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Query("SELECT * FROM note ORDER BY date ASC ")
    fun getAllNotes() : List<Note>

    @Delete
    fun deleteNote(note: Note)
}
