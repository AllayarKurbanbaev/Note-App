package uz.gita.noteappallayar.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.noteappallayar.data.local.Database
import uz.gita.noteappallayar.data.local.dao.NoteDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @[Provides Singleton]
    fun getDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, "NoteDatabase").build()

    @[Provides Singleton]
    fun getNoteDao(database: Database): NoteDao = database.getNoteDao()

}