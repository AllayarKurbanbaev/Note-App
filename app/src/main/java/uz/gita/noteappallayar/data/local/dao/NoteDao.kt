package uz.gita.noteappallayar.data.local.dao

import androidx.room.*
import uz.gita.noteappallayar.data.local.entity.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE NoteEntity.id = :id")
    suspend fun getById(id: Int): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: NoteEntity)

    @Delete
    suspend fun delete(data: NoteEntity)
}