package uz.gita.noteappallayar.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappallayar.data.local.entity.NoteEntity

interface AddViewModel {
    val saveInputsLiveData : LiveData<String>
    val openBackDialogLiveData : LiveData<Unit>

    fun saveData(model : NoteEntity)
    fun closeAddScreen()
}