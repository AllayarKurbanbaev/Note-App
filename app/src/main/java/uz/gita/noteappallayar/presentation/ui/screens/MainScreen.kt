package uz.gita.noteappallayar.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.noteappallayar.R
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.databinding.ScreenMainBinding
import uz.gita.noteappallayar.presentation.ui.adapter.NoteAdapter
import uz.gita.noteappallayar.presentation.viewmodel.MainViewModel
import uz.gita.noteappallayar.presentation.viewmodel.impl.MainViewModelImpl


@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private var listItems: ArrayList<NoteEntity> = arrayListOf()
    private var listViews: ArrayList<View> = arrayListOf()
    private var size = 0
    private var listBackground: MutableMap<View, Int> = mutableMapOf()

    private val adapter: NoteAdapter = NoteAdapter()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {

        size = 0

        viewModel.loadNotes()
        recyclerView.adapter = adapter

        addButton.setOnClickListener {
            viewModel.addNote()
        }

        adapter.setOnDeleteButtonClickListener { model, view ->
            if (size == 0) {
                listBackground[view] = R.drawable.shape_item_onclick
                view.setBackgroundResource(R.drawable.shape_item_onclick)
                listItems.add(model)
                listViews.add(view)
                binding.deletes.visibility = View.VISIBLE
                size++
                count.visibility = View.VISIBLE
                count.text = size.toString()
            }
        }


        binding.deletes.setOnClickListener {
            if (it.isVisible && listItems.isNotEmpty() && listViews.isNotEmpty()) {
                listItems.forEach { model ->
                    viewModel.deleteNote(model)
                }
                listViews.forEach { view ->
                    view.setBackgroundResource(R.drawable.shape_item)
                }
                size = 0
                count.visibility = View.GONE
                count.text = size.toString()
                deletes.visibility = View.GONE
            }
        }

        adapter.setOnItemClickListener { model, view ->
            if (listBackground.isNotEmpty() && listBackground[view] == R.drawable.shape_item_onclick) {
                view.setBackgroundResource(R.drawable.shape_item)
                listBackground[view] = R.drawable.shape_item
                size--
                count.text = size.toString()
                listItems.remove(model)
                listViews.remove(view)
                if (size == 0) {
                    count.visibility = View.GONE
                    deletes.visibility = View.GONE
                }
            } else if (size >= 1) {
                view.setBackgroundResource(R.drawable.shape_item_onclick)
                listBackground[view] = R.drawable.shape_item_onclick
                listItems.add(model)
                listViews.add(view)
                size++
                count.text = size.toString()
            } else {
                viewModel.openDetailsScreen(model.id)
            }
        }

        infoButton.setOnClickListener {
            viewModel.openInfoDialog()
        }
        //todo Delete swipe to Right or Left

        viewModel.deleteNoteLiveData.observe(viewLifecycleOwner, deleteNoteObserver)
        viewModel.messageLiveData.observe(viewLifecycleOwner, messageObserver)
        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)
        viewModel.openAddScreenLiveData.observe(this@MainScreen, openAddScreenObserver)
        viewModel.openDetailScreenLiveData.observe(this@MainScreen, openDetailScreenObserver)
        viewModel.openInfoDialogLiveData.observe(this@MainScreen, openInfoDialogObserver)
    }


    private val deleteNoteObserver = Observer<Unit> {
        viewModel.loadNotes()
    }
    private val messageObserver = Observer<String> {

    }

    private val notesObserver = Observer<List<NoteEntity>> {
        if (it.isNotEmpty()) {
            binding.nullImage.visibility = View.GONE
        } else {
            binding.nullImage.visibility = View.VISIBLE
        }
        adapter.submitList(it)
    }
    private val openAddScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_mainScreen_to_addScreen)
    }

    private val openDetailScreenObserver = Observer<Int> {
        findNavController().navigate(MainScreenDirections.actionMainScreenToDetailScreen(it))

    }

    private val openInfoDialogObserver = Observer<Unit> {
        ///todo Dialog open
    }
}