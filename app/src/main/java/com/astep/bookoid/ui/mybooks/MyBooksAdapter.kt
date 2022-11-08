// Всегда смотри, чтобы пакет был правильный, согласно пути, где лежит файл.
package com.astep.bookoid.ui.mybooks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astep.bookoid.R
import com.astep.bookoid.domain.MyBook
import com.astep.bookoid.databinding.ItemOfMyBooksListBinding
import com.bumptech.glide.Glide

// TODO: ListAdapter
class MyBooksAdapter(
    private val onItemClicked: (book: MyBook) -> Unit
) : RecyclerView.Adapter<MyBooksAdapter.Holder>() {

    private var books: List<MyBook> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(
            ItemOfMyBooksListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<MyBook>) {
        // TODO: apply diffutils
        books = newBooks
        notifyDataSetChanged()
    }

    class Holder(
        // Всегда нужно давать минимальный модификатор видимости private -> protected -> public
        // и открывать проперти только по мере необходимости.
        private val binding: ItemOfMyBooksListBinding,
        private val onItemClicked: (book: MyBook) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: MyBook) {
            // TODO: мы должны отменять загрузку, если пользователь скролит. Покажу потом.
            Glide.with(this.itemView)
                .load(book.imageSource)
                .placeholder(R.drawable.ic_forest)
                .error(R.drawable.ic_forest)
                .into(binding.bookCover)

            binding.bookTitle.text = book.title
            binding.bookDescription.text = book.description.orEmpty()

            // Обработчики событий лучше всегда навешивать там же, где и байндинг.
            // В противном случае (если навесить в конструкторе init) при ребайндинге для другого итема
            // у тебя останется старый обработчик. Это может привести к ошибкам, которые сложно поймать.
            binding.root.setOnClickListener {
                onItemClicked(book)
            }
        }
    }
}
