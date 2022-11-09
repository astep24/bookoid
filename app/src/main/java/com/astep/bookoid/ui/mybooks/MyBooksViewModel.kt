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

class MyBooksViewModel(
    private val app: Application,
    private val repository: MyBooksRepository
) : AndroidViewModel(app) {
    
    private var booksList: List<MyBook>? = null
    
    var prevSearchValue: String = ""
    
    var isGridOn: Boolean = false
    
    var isFilterOn: Boolean = false
    
    val gridSpanCount: Int
        get() = app.resources.getInteger(R.integer.fragment_my_books__grid_span_count)
    
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
