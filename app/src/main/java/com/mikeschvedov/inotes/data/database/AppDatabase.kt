package com.mikeschvedov.inotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikeschvedov.inotes.data.database.daos.NotesDao
import com.mikeschvedov.inotes.data.database.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao

}