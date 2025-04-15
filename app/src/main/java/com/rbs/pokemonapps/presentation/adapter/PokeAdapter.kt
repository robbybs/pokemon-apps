package com.rbs.pokemonapps.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rbs.pokemonapps.databinding.ItemPokemonBinding
import com.rbs.pokemonapps.domain.model.PokeItemDomain

class PokeAdapter : PagingDataAdapter<PokeItemDomain, PokeAdapter.PokeViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class PokeViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokeItemDomain) {
            with(binding) {
                item.apply {
                    tvName.text = name
                    tvUrl.text = url
                }
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<PokeItemDomain>() {
            override fun areItemsTheSame(oldItem: PokeItemDomain, newItem: PokeItemDomain): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokeItemDomain, newItem: PokeItemDomain): Boolean =
                oldItem == newItem
        }
    }
}