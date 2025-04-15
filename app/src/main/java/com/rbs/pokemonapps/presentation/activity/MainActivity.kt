package com.rbs.pokemonapps.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.databinding.ActivityMainBinding
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.presentation.adapter.LoadingStateAdapter
import com.rbs.pokemonapps.presentation.adapter.PokeAdapter
import com.rbs.pokemonapps.presentation.viewmodel.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<PokeViewModel>()
    private val pokeAdapter by lazy { PokeAdapter() }

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeUI()
        subscribeObserver()
    }

    private fun subscribeUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvPokemon.adapter = pokeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { pokeAdapter.retry() }
        )
    }

    private fun subscribeObserver() {
        lifecycleScope.launch {
            viewModel.viewState.collect {
                when (it) {
                    ResultState.Loading -> setLoading(true)
                    is ResultState.Success -> setData(it.data)
                    is ResultState.Error -> setLoading(false)
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
}