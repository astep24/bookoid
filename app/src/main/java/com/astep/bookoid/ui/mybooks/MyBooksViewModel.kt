package com.astep.bookoid.ui.mybooks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astep.bookoid.R
import com.astep.bookoid.domain.MyBook
import com.astep.bookoid.domain.DataOutcomeState
import com.astep.bookoid.domain.MyBooksRepository
import kotlinx.coroutines.launch

// Если нужны ресурсы - можно поменять на AndroidViewModel, тогда будет доступен application.
// Потому что во view model мы можем использовать только context приложения (без утечек).
class MyBooksViewModel(
    app: Application,
    // Зависимости нужно всегда стараться передать через конструктор.
    // Таким образом их можно подменить позднее в целях тестирования.
    // Почитай про SOLID принципы.
    private val repository: MyBooksRepository
) : AndroidViewModel(app) {

    private var booksList: List<MyBook>? = null

    var prevSearchValue: String = ""

    var isGridOn: Boolean = false

    var isFilterOn: Boolean = false

    val gridSpanCount: Int = app.resources.getInteger(R.integer.fragment_my_books__grid_span_count)

    private val _booksListDBResult = MutableLiveData<DataOutcomeState<List<MyBook>>>()
    val booksListDBResult: LiveData<DataOutcomeState<List<MyBook>>> = _booksListDBResult

    fun requestForMyBooks(
        searchValue: String?,
        forceRequest: Boolean
    ) = viewModelScope.launch {
        if (forceRequest || booksList == null) {
            repository.getMyBooks(searchValue.orEmpty())
                .collect {
                    booksList = (it as? DataOutcomeState.Success)?.data
                    _booksListDBResult.postValue(it)
                }
        } else {
            _booksListDBResult.postValue(DataOutcomeState.Success(booksList.orEmpty()))
        }
    }

    fun onItemClicked(book: MyBook) {
        // TODO: open another screen
    }
}
