package com.mikeschvedov.inotes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mikeschvedov.inotes.data.database.daos.NotesDao
import com.mikeschvedov.inotes.data.database.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao


    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
//            check if there is any existing instance is present for our room database
//            if there exist an existing instance then we'll return that instance
            if(tempInstance!=null){
                return tempInstance
            }
//            If there is no any instance present for our database then we'll create a new instance
//            WHY SYNCHRONIZED ?? --> Because everything inside the synchronized block will be protected
//            by concurrent execution on multiple threads
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}