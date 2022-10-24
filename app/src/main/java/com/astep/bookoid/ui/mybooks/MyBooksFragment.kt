package com.astep.bookoid.ui.mybooks

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.astep.bookoid.R
import com.astep.bookoid.databinding.FragmentMyBooksBinding
import com.astep.bookoid.sealed_classes.DBOutcome
import com.astep.bookoid.utils.*
import com.skillbox.lesson20myself.MyBooksAdapter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyBooksFragment : ViewBindingAndToastFragment<FragmentMyBooksBinding> (
    FragmentMyBooksBinding::inflate
) {

    private val viewModel: MyBooksViewModel by viewModels()

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
        val highlightedMenuItems =
            listOf(
                R.id.menu_toolbar_my_books__grid to viewModel.isGridOn,
                R.id.menu_toolbar_my_books__filter to viewModel.isFilterOn,
            )
                .filter { (_, isOn) -> isOn }
                .map { (res, _) -> binding.toolbar.menu.findItem(res) }

        binding.toolbar.menu
            ?.let { menu -> List(menu.size()) { menu.getItem(it) } }
            ?.forEach {
                if (highlightedMenuItems.contains(it)) it.setColorOn(requireContext()) else
                    it.setColorOff(requireContext())
            }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_toolbar_my_books__search -> { true }
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
        booksAdapter = MyBooksAdapter { toast("$it") }
        with (binding.booksList) {
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
                is DBOutcome.Progress ->
                    setViews(VisibleViewOption.PROGRESS_BAR)
                is DBOutcome.Failure ->
                    ResourcesUtils.getStringResource(
                        R.string.fragment_my_books__db_error,
                        it.e.toString()
                    ).let { setViews(VisibleViewOption.NICE_MESSAGE, it) }
                is DBOutcome.Success ->
                    if (it.data.isEmpty())
                        ResourcesUtils.getStringResource(
                            R.string.fragment_my_books__list_is_empty
                        ).let { setViews(VisibleViewOption.NICE_MESSAGE, it) }
                    else {
                        setViews(VisibleViewOption.BOOKS_LIST)
                        booksAdapter?.updateBooks(it.data)
                    }
                else -> throw Error("Unexpected DB Result")
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
            menuItem.setColorOff(requireContext())
            viewModel.isGridOn = false
        } else {
            menuItem.setColorOn(requireContext())
            viewModel.isGridOn = true
        }
        binding.booksList.layoutManager = selectLayoutManager()
    }


    private fun selectLayoutManager(): LayoutManager =
        if (viewModel.isGridOn)
            GridLayoutManager(this@MyBooksFragment.context, viewModel.gridSpanCount) else
            LinearLayoutManager(this@MyBooksFragment.context)


    private fun setViews(
        visibleView: VisibleViewOption,
        niceMessage: String = ""
    ) {
        binding.progressBarOnDatabaseNotReady.isVisible =
            visibleView == VisibleViewOption.PROGRESS_BAR
        binding.booksList.isVisible =
            visibleView == VisibleViewOption.BOOKS_LIST
        binding.textViewNiceMessage.isVisible =
            visibleView == VisibleViewOption.NICE_MESSAGE
        if (visibleView == VisibleViewOption.NICE_MESSAGE)
            binding.textViewNiceMessage.text = niceMessage
    }


    enum class VisibleViewOption {
        PROGRESS_BAR,
        BOOKS_LIST,
        NICE_MESSAGE
    }

}