package com.skillbox.lesson20myself

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.astep.bookoid.R
import com.astep.bookoid.data_classes.MyBook
import com.astep.bookoid.databinding.ItemOfMyBooksListBinding

class MyBooksAdapter(
    private val onItemClicked: (position: Int) -> Unit
): RecyclerView.Adapter<MyBooksAdapter.Holder>() {

    private var books: List<MyBook> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemOfMyBooksListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<MyBook>) {
        // TODO: apply diffutils
        books = newBooks
        notifyDataSetChanged()
    }

    class Holder(
        val binding: ItemOfMyBooksListBinding,
        onItemClicked: (position: Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClicked(absoluteAdapterPosition)
            }
        }

        fun bind(book: MyBook) {
            Glide.with(this.itemView)
                .load(book.imageSource)
                .placeholder(R.drawable.ic_forest)
                .error(R.drawable.ic_forest)
                .into(binding.bookCover)

            binding.bookTitle.text = book.title
            binding.bookDescription.text = book.description.orEmpty()
        }
    }
}