package com.astep.bookoid.ui.mybooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astep.bookoid.R
import com.astep.bookoid.data_classes.MyBook
import com.astep.bookoid.repositories.MyBooksRepository
import com.astep.bookoid.sealed_classes.DBOutcome
import com.astep.bookoid.utils.ResourcesUtils
import kotlinx.coroutines.launch

class MyBooksViewModel : ViewModel() {

    private val repository = MyBooksRepository()

    private var booksList: List<MyBook>? = null

    var prevSearchValue: String = ""

    var isGridOn: Boolean = false

    var isFilterOn: Boolean = false

    val gridSpanCount: Int
        get() = ResourcesUtils.getIntResource(R.integer.fragment_my_books__grid_span_count)

    private val _booksListDBResult = MutableLiveData<DBOutcome<List<MyBook>>>()
    val booksListDBResult: LiveData<DBOutcome<List<MyBook>>> = _booksListDBResult


    fun requestForMyBooks(
        searchValue: String?,
        forceRequest: Boolean
    ) = viewModelScope.launch {
        if (forceRequest || booksList == null) {
            repository.getMyBooks(searchValue.orEmpty())
                .collect {
                    booksList = (it as? DBOutcome.Success)?.data
                    _booksListDBResult.postValue(it)
                }
        } else _booksListDBResult.postValue(DBOutcome.Success(booksList.orEmpty()))
    }

}