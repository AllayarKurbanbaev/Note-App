package uz.gita.noteappallayar.presentation.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.aztec.Aztec
import org.wordpress.aztec.ITextFormat
import org.wordpress.aztec.toolbar.IAztecToolbarClickListener
import uz.gita.noteappallayar.R
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.databinding.ScreenAddBinding
import uz.gita.noteappallayar.presentation.viewmodel.AddViewModel
import uz.gita.noteappallayar.presentation.viewmodel.impl.AddViewModelImpl

@AndroidEntryPoint
class AddScreen : Fragment(R.layout.screen_add) {

    private val binding by viewBinding(ScreenAddBinding::bind)
    private val viewModel: AddViewModel by viewModels<AddViewModelImpl>()
    private var descriptionBool: Boolean = false
    private var titleBool: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {

        saveButton.isEnabled = descriptionBool && titleBool

        editTitle.addTextChangedListener {
            titleBool = it.toString().isNotEmpty()
            saveButton.isEnabled = titleBool && descriptionBool
        }

        Aztec.with(
            binding.editDescription,
            binding.tools,
            object : IAztecToolbarClickListener {
                override fun onToolbarCollapseButtonClicked() {

                }

                override fun onToolbarExpandButtonClicked() {

                }

                override fun onToolbarFormatButtonClicked(
                    format: ITextFormat,
                    isKeyboardShortcut: Boolean
                ) {

                }

                override fun onToolbarHeadingButtonClicked() {

                }

                override fun onToolbarHtmlButtonClicked() {

                }

                override fun onToolbarListButtonClicked() {

                }

                override fun onToolbarMediaButtonClicked(): Boolean {
                    return false
                }

            }
        )


        editDescription.addTextChangedListener {
            descriptionBool = it.toString().isNotEmpty()
            saveButton.isEnabled = titleBool && descriptionBool
        }


        saveButton.setOnClickListener {
            viewModel.saveData(
                NoteEntity(
                    id = 0,
                    title = editTitle.text.toString(),
                    description = editDescription.text.toString()
                )
            )
        }

        backButton.setOnClickListener {
            viewModel.closeAddScreen()
        }



        viewModel.saveInputsLiveData.observe(viewLifecycleOwner, saveInputsObserver)
        viewModel.openBackDialogLiveData.observe(viewLifecycleOwner, openBackDialogObserver)
    }

    private val saveInputsObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private val openBackDialogObserver = Observer<Unit> {
        //todo Back button onclick open dialog
        findNavController().popBackStack()
    }

}