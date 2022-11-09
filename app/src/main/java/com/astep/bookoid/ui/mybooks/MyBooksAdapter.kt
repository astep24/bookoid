package com.astep.bookoid.ui.mybooks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.astep.bookoid.R
import com.astep.bookoid.domain.MyBook
import com.astep.bookoid.databinding.ItemOfMyBooksListInGridPresentationBinding
import com.astep.bookoid.databinding.ItemOfMyBooksListInLinearPresentationBinding
import com.bumptech.glide.Glide

// TODO: ListAdapter
class MyBooksAdapter(
    private val onItemClicked: (book: MyBook) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    private val differ = AsyncListDiffer<MyBook>(this, MyBookDiffutilCallback())
    
    var currentPresentationState = PresentationState.LINEAR_PRESENTATION_STATE
    
    fun setPresentationState(newPresentationState: PresentationState) {
        if (currentPresentationState == newPresentationState) return
        currentPresentationState = newPresentationState
        this.notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_LINEAR_PRESENTATION -> {
                MyBookHolderInLinearPresentation(
                    ItemOfMyBooksListInLinearPresentationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClicked
                )
            }
            TYPE_GRID_PRESENTATION -> {
                MyBookHolderInGridPresentation(
                    ItemOfMyBooksListInGridPresentationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClicked
                )
            }
            else -> {
                error("Incorrect view type = $viewType")
            }
        }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyBookHolderInLinearPresentation -> {
                holder.bind(differ.currentList[position])
            }
            is MyBookHolderInGridPresentation -> {
                holder.bind(differ.currentList[position])
            }
            else -> {
                error("Incorrect view holder = $holder")
            }
        }
    }
    
    override fun getItemViewType(position: Int): Int =
        when (currentPresentationState) {
            PresentationState.LINEAR_PRESENTATION_STATE -> TYPE_LINEAR_PRESENTATION
            PresentationState.GRID_PRESENTATION_STATE -> TYPE_GRID_PRESENTATION
        }
    
    override fun getItemCount(): Int = differ.currentList.size
    
    fun updateBooks(newBooks: List<MyBook>) {
        differ.submitList(newBooks)
    }
    
    class MyBookDiffutilCallback : DiffUtil.ItemCallback<MyBook>() {
        override fun areItemsTheSame(oldItem: MyBook, newItem: MyBook): Boolean =
            oldItem.id == newItem.id
        
        override fun areContentsTheSame(oldItem: MyBook, newItem: MyBook): Boolean =
            oldItem == newItem
    }
    
    abstract class BaseMyBookHolder(
        private val binding: ViewBinding,
        private val onItemClicked: (book: MyBook) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private val bookCover: ImageView? = when(binding) {
            is ItemOfMyBooksListInLinearPresentationBinding -> binding.bookCover
            is ItemOfMyBooksListInGridPresentationBinding -> binding.bookCover
            else -> null
        }
        
        private val bookTitle: TextView? = when(binding) {
            is ItemOfMyBooksListInLinearPresentationBinding -> binding.bookTitle
            is ItemOfMyBooksListInGridPresentationBinding -> binding.bookTitle
            else -> null
        }

        protected fun bindMainInfo(book: MyBook) {
            // TODO: мы должны отменять загрузку, если пользователь скролит. Покажу потом.
            bookCover?.let {
                Glide.with(this.itemView)
                    .load(book.imageSource)
                    .placeholder(R.drawable.ic_forest)
                    .error(R.drawable.ic_forest)
                    .into(it)
            }
            
            bookTitle?.text = book.title
            
            binding.root.setOnClickListener {
                onItemClicked(book)
            }
        }
        
    }
    
    class MyBookHolderInGridPresentation(
        binding: ItemOfMyBooksListInGridPresentationBinding,
        onItemClicked: (book: MyBook) -> Unit
    ) : BaseMyBookHolder(binding, onItemClicked) {
        fun bind(book: MyBook) {
            bindMainInfo(book)
        }
    }
    
    class MyBookHolderInLinearPresentation(
        private val binding: ItemOfMyBooksListInLinearPresentationBinding,
        onItemClicked: (book: MyBook) -> Unit
    ) : BaseMyBookHolder(binding, onItemClicked) {
        fun bind(book: MyBook) {
            bindMainInfo(book)
            binding.bookDescription.text = book.description.orEmpty()
        }
    }
    
    enum class PresentationState {
        LINEAR_PRESENTATION_STATE, GRID_PRESENTATION_STATE
    }
    
    companion object {
        private const val TYPE_LINEAR_PRESENTATION = 1
        private const val TYPE_GRID_PRESENTATION = 2
    }
    
}
