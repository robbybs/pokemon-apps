package com.rbs.pokemonapps.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbs.pokemonapps.R
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.databinding.ActivityDetailBinding
import com.rbs.pokemonapps.domain.model.PokeDetailDomain
import com.rbs.pokemonapps.presentation.adapter.AbilityAdapter
import com.rbs.pokemonapps.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetailViewModel>()
    private val abilityAdapter by lazy { AbilityAdapter() }

    companion object {
        private const val POKE_ID = "ID"

        @JvmStatic
        fun launch(context: Context, id: Int) {
            Intent(context, DetailActivity::class.java).apply {
                putExtra(POKE_ID, id)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getDetailData()
        subscribeUI()
        subscribeObserver()
    }

    private fun getDetailData() {
        val id = intent.getIntExtra(POKE_ID, 0)
        viewModel.getDetail(id)
    }

    private fun subscribeUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvAbility.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = abilityAdapter
        }
    }

    private fun subscribeObserver() {
        lifecycleScope.launch {
            viewModel.viewState.collectLatest {
                when (it) {
                    ResultState.Loading -> setLoading(true)
                    is ResultState.Success -> setData(it.data)
                    is ResultState.Error -> setLoading(false)
                }
            }
        }
    }

    private fun setLoading(isVisible: Boolean) {
        binding.apply {
            progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvAbility.visibility = if (isVisible) View.GONE else View.VISIBLE
        }
    }

    private fun setData(data: PokeDetailDomain) {
        setLoading(false)
        with(binding) {
            data.apply {
                tvName.text = name.uppercase()
                abilityAdapter.submitList(listAbility)
            }
        }
    }
}