package uz.gita.noteappallayar.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.domain.repository.AppRepository
import uz.gita.noteappallayar.presentation.viewmodel.AddViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), AddViewModel {

    override val saveInputsLiveData = MutableLiveData<String>()
    override val openBackDialogLiveData = MutableLiveData<Unit>()

    override fun saveData(model: NoteEntity) {
        repository.insert(model).onEach {
            it.onSuccess {
                saveInputsLiveData.value = "Save data!"
            }
        }.launchIn(viewModelScope)

    }

    override fun closeAddScreen() {
        openBackDialogLiveData.value = Unit
    }

}