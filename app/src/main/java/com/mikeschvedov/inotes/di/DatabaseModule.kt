package com.mikeschvedov.inotes.di

import android.content.Context
import androidx.room.Room
import com.mikeschvedov.inotes.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DBName = "NotesDB"

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
         Room.databaseBuilder(appContext, AppDatabase::class.java, DBName)
            .build()


    @Provides
    fun provideNotesDao(appDatabase: AppDatabase) =
         appDatabase.notesDao()



}