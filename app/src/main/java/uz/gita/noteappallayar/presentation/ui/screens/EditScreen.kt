package uz.gita.noteappallayar.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.aztec.Aztec
import org.wordpress.aztec.ITextFormat
import org.wordpress.aztec.toolbar.IAztecToolbarClickListener
import uz.gita.noteappallayar.R
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.databinding.ScreenEditBinding
import uz.gita.noteappallayar.presentation.viewmodel.EditViewModel
import uz.gita.noteappallayar.presentation.viewmodel.impl.EditViewModelImpl


@AndroidEntryPoint
class EditScreen : Fragment(R.layout.screen_edit) {

    private val binding by viewBinding(ScreenEditBinding::bind)
    private val viewModel: EditViewModel by viewModels<EditViewModelImpl>()
    private val args: EditScreenArgs by navArgs()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        viewModel.loadData(args.pos)

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
        saveButton.setOnClickListener {
            val data = NoteEntity(
                args.pos,
                editTitle.text.toString(),
                editDescription.text.toString()
            )
            viewModel.saveData(data)
        }

        backButton.setOnClickListener {
            viewModel.back(args.pos)
        }

        viewModel.loadDataLiveData.observe(viewLifecycleOwner, loadDataObserver)
        viewModel.openDetailScreenLiveData.observe(this@EditScreen, openDetailScreenObserver)
        viewModel.saveLiveData.observe(viewLifecycleOwner, saveObserver)
    }

    private val saveObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        viewModel.back(args.pos)
    }

    private val loadDataObserver = Observer<NoteEntity> {
        binding.editTitle.setText(it.title.toString())
        binding.editDescription.setText(it.description.toString())
    }

    private val openDetailScreenObserver = Observer<Int> {
        findNavController().navigate(EditScreenDirections.actionEditScreenToDetailScreen(it))
    }
}