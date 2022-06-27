package uz.gita.noteappallayar.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappallayar.data.local.entity.NoteEntity

interface DetailViewModel {
    val loadDataLiveData: LiveData<NoteEntity>
    val onBackPressedLiveData: LiveData<Unit>
    val openEditScreenLiveData: LiveData<Int>


    fun onBack()
    fun editData(pos : Int)
    fun loadData(pos : Int)
}