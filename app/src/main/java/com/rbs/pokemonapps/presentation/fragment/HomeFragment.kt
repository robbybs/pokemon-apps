package com.rbs.pokemonapps.presentation.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.databinding.FragmentHomeBinding
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.presentation.activity.DetailActivity
import com.rbs.pokemonapps.presentation.adapter.LoadingStateAdapter
import com.rbs.pokemonapps.presentation.adapter.PokeAdapter
import com.rbs.pokemonapps.presentation.viewmodel.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<PokeViewModel>()
    private val pokeAdapter by lazy { PokeAdapter() }
    private var searchJob: Job? = null
    private var isLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI(view)
        subscribeObserver()
    }

    private fun subscribeUI(view: View) {
        resetSearchState()
        initRecyclerView()
        setHideKeyboard(view)
        searchData()
    }

    private fun resetSearchState() {
        viewLifecycleOwner.lifecycleScope.launch {
            pokeAdapter.submitData(PagingData.empty())
            viewModel.fetchData()
            isLoaded = true
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rvPokemon.adapter = pokeAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { pokeAdapter.retry() }
            )

            pokeAdapter.apply {
                onItemClickListener {
                    DetailActivity.launch(requireContext(), it)
                }
            }
        }
    }

    private fun setHideKeyboard(view: View) {
        view.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.performClick()
                hideKeyboardOutsideEditText(event)
            }
            false
        }
    }

    private fun hideKeyboardOutsideEditText(event: MotionEvent) {
        activity?.currentFocus?.let { focusedView ->
            if (focusedView is EditText) {
                val outRect = Rect()
                focusedView.getGlobalVisibleRect(outRect)

                val screenLocation = IntArray(2)
                focusedView.getLocationOnScreen(screenLocation)
                outRect.offset(0, -screenLocation[1])

                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    hideKeyboard(focusedView)
                    focusedView.clearFocus()
                }
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun searchData() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            searchJob?.cancel()
            val queryText = text?.trim().toString()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(300)
                if (isLoaded) {
                    isLoaded = false
                    return@launch
                }

                if (queryText.isNotEmpty()) {
                    pokeAdapter.submitData(PagingData.empty())
                    viewModel.getAllQuery(queryText)
                } else {
                    viewModel.fetchData()
                }
            }
        }
    }

    private fun subscribeObserver() {
        loadingObserver()
        dataObserver()
        searchObserver()
    }

    private fun loadingObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            pokeAdapter.loadStateFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest { loadStates ->
                    val isLoading = loadStates.refresh is LoadState.Loading
                    setLoading(isLoading)
                }
        }
    }

    private fun dataObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    ResultState.Loading -> Unit
                    is ResultState.Success -> setData(it.data)
                    is ResultState.Error -> showError(it.message)
                }
            }
        }
    }

    private fun searchObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collectLatest { state ->
                when (state) {
                    is ResultState.Loading -> setLoading(true)
                    is ResultState.Success -> setData(PagingData.from(state.data))
                    is ResultState.Error -> {
                        setLoading(false)
                        showError(state.message)
                    }
                }
            }
        }
    }

    private fun setLoading(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private suspend fun setData(data: PagingData<PokeItemDomain>) {
        setLoading(false)
        pokeAdapter.submitData(data)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        binding.etSearch.text?.clear()
    }
}