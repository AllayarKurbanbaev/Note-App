package uz.gita.noteappallayar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.noteappallayar.data.local.dao.NoteDao
import uz.gita.noteappallayar.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}