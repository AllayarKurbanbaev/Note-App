package uz.gita.noteappallayar.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.noteappallayar.R
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.databinding.ItemNoteBinding


class NoteAdapter : ListAdapter<NoteEntity, NoteAdapter.ViewHolder>(NoteDiffUtil) {

    private var onItemClickListener: ((NoteEntity, View) -> Unit)? = null
    private var onDeleteButtonClickListener: ((NoteEntity, View) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(getItem(absoluteAdapterPosition), it)
            }
            binding.root.setOnLongClickListener {
                onDeleteButtonClickListener?.invoke(getItem(absoluteAdapterPosition), it)
                return@setOnLongClickListener true
            }
        }

        fun populateModel(model: NoteEntity) = with(binding) {
            tvTitle.text = model.title
            tvDescription.text = model.description
        }
    }

    private object NoteDiffUtil : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(ItemNoteBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(getItem(position))
    }

    fun setOnItemClickListener(block: (NoteEntity, View) -> Unit) {
        onItemClickListener = block
    }


    fun setOnDeleteButtonClickListener(block: (NoteEntity, View) -> Unit) {
        onDeleteButtonClickListener = block
    }
}