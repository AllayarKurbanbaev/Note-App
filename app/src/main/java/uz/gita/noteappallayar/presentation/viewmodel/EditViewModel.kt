package uz.gita.noteappallayar.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappallayar.data.local.entity.NoteEntity

interface EditViewModel {
    val saveLiveData : LiveData<String>
    val openDetailScreenLiveData : LiveData<Int>
    val loadDataLiveData : LiveData<NoteEntity>

    fun back(pos : Int)
    fun saveData(data : NoteEntity)
    fun loadData(pos : Int)
}