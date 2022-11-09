package com.astep.bookoid.ui.mybooks

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.astep.bookoid.R
import com.astep.bookoid.databinding.FragmentMyBooksBinding
import com.astep.bookoid.data.MyBooksRepositoryImpl
import com.astep.bookoid.domain.DataOutcomeState
import com.astep.bookoid.utils.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyBooksFragment : ViewBindingFragment<FragmentMyBooksBinding>(
    FragmentMyBooksBinding::inflate
) {
    
    // TODO: Пока используем кастомную фабрику, чтобы потом подключить HILT.
    @Suppress("UNCHECKED_CAST")
    private val viewModel: MyBooksViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MyBooksViewModel(
                    requireActivity().application,
                    MyBooksRepositoryImpl()
                ) as T
            }
        }
    }
    
    private var booksAdapter: MyBooksAdapter? = null
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initToolbar()
        initList()
        initFab()
        initSearchViewFlow()
        initLiveDataObserves()
        viewModel.requestForMyBooks("", forceRequest = false)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        booksAdapter = null
    }
    
    private fun initToolbar() {
        val highlightedMenuItems = listOf(
            R.id.menu_toolbar_my_books__grid to viewModel.isGridOn,
            R.id.menu_toolbar_my_books__filter to viewModel.isFilterOn,
        )
            .filter { (_, isOn) ->
                isOn
            }
            .map { (res, _) ->
                binding.toolbar.menu.findItem(res)
            }
        
        binding.toolbar.menu
            ?.let { menu ->
                List(menu.size()) { menu.getItem(it) }
            }
            ?.forEach {
                if (highlightedMenuItems.contains(it)) {
                    it.setColorOn(requireContext())
                } else {
                    it.setColorOff(requireContext())
                }
            }
        
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_toolbar_my_books__search -> {
                    true
                }
                R.id.menu_toolbar_my_books__filter -> {
                    onFilterIconClicked(menuItem)
                    true
                }
                R.id.menu_toolbar_my_books__grid -> {
                    onGridIconClicked(menuItem)
                    true
                }
                else -> false
            }
        }
    }
    
    private fun initList() {
        booksAdapter = MyBooksAdapter(viewModel::onItemClicked)
        booksAdapter?.setPresentationState(selectPresentationState())
        with(binding.booksList) {
            adapter = booksAdapter
            layoutManager = selectLayoutManager()
            setHasFixedSize(true)
        }
    }
    
    private fun initFab() {
        binding.fabAddMyBook.setOnClickListener {
            toast("Add book tapped")
        }
    }
    
    @OptIn(FlowPreview::class)
    private fun initSearchViewFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            val searchMenuItem = binding.toolbar.menu.findItem(R.id.menu_toolbar_my_books__search)
            
            (searchMenuItem.actionView as? SearchView)
                ?.textChangedFlow()
                ?.onEach {
                    DebugLogger.d(msg = "flow goes: [$it]")
                }
                ?.drop(1) // don't need initial emit - always the empty string
                ?.dropWhile { it == viewModel.prevSearchValue } // specially for starting flow
                // after orientation been changed: a current value in SearchView goes
                // emitted, and we don't need it, since books are already filtered
                // according to that value
                ?.debounce(500)
                ?.distinctUntilChanged()
                ?.collect {
                    DebugLogger.d(msg = "collected: [$it]")
                    viewModel.prevSearchValue = it
                    viewModel.requestForMyBooks(it, forceRequest = true)
                }
        }
    }
    
    private fun initLiveDataObserves() {
        viewModel.booksListDBResult.observe(viewLifecycleOwner) {
            when (it) {
                is DataOutcomeState.Progress -> {
                    setViews(VisibleViewOption.PROGRESS_BAR)
                }
                is DataOutcomeState.Failure -> {
                    setViews(
                        VisibleViewOption.NICE_MESSAGE,
                        getString(R.string.fragment_my_books__db_error, it.e.toString())
                    )
                }
                is DataOutcomeState.Success -> {
                    if (it.data.isEmpty()) {
                        setViews(
                            VisibleViewOption.NICE_MESSAGE,
                            getString(R.string.fragment_my_books__list_is_empty)
                        )
                    } else {
                        setViews(VisibleViewOption.BOOKS_LIST)
                        booksAdapter?.updateBooks(it.data)
                    }
                }
                else -> {
                    throw Error("Unexpected DB Result")
                }
            }
        }
    }
    
    private fun onFilterIconClicked(menuItem: MenuItem) {
        // TODO: filtering by genres in bottom sheet
        if (viewModel.isFilterOn) {
            menuItem.setColorOff(requireContext())
            viewModel.isFilterOn = false
        } else {
            menuItem.setColorOn(requireContext())
            viewModel.isFilterOn = true
        }
    }
    
    private fun onGridIconClicked(menuItem: MenuItem) {
        if (viewModel.isGridOn) {
            viewModel.isGridOn = false
            menuItem.setColorOff(requireContext())
        } else {
            viewModel.isGridOn = true
            menuItem.setColorOn(requireContext())
        }
        booksAdapter?.setPresentationState(selectPresentationState())
        binding.booksList.layoutManager = selectLayoutManager()
    }
    
    private fun selectPresentationState(): MyBooksAdapter.PresentationState =
        if (viewModel.isGridOn) {
            MyBooksAdapter.PresentationState.GRID_PRESENTATION_STATE
        } else {
            MyBooksAdapter.PresentationState.LINEAR_PRESENTATION_STATE
        }
    
    private fun selectLayoutManager(): LayoutManager =
        if (viewModel.isGridOn) {
            GridLayoutManager(context, viewModel.gridSpanCount)
        } else {
            LinearLayoutManager(context)
        }
    
    private fun setViews(
        visibleView: VisibleViewOption, niceMessage: String = ""
    ) {
        binding.progressBarOnDatabaseNotReady.isVisible =
            visibleView == VisibleViewOption.PROGRESS_BAR
        binding.booksList.isVisible = visibleView == VisibleViewOption.BOOKS_LIST
        binding.textViewNiceMessage.isVisible = visibleView == VisibleViewOption.NICE_MESSAGE
        if (visibleView == VisibleViewOption.NICE_MESSAGE) {
            binding.textViewNiceMessage.text = niceMessage
        }
    }
    
    enum class VisibleViewOption {
        PROGRESS_BAR, BOOKS_LIST, NICE_MESSAGE
    }
    
}
