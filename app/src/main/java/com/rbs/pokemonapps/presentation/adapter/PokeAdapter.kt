package com.rbs.pokemonapps.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rbs.pokemonapps.databinding.ItemPokemonBinding
import com.rbs.pokemonapps.domain.model.PokeItemDomain

class PokeAdapter : PagingDataAdapter<PokeItemDomain, PokeAdapter.PokeViewHolder>(DiffCallback) {
    private var onClickListener: ((Int) -> Unit)? = null

    fun onItemClickListener(listener: (Int) -> Unit) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item, onClickListener)
        }
    }

    class PokeViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokeItemDomain, onClick: ((Int) -> Unit)?) {
            with(binding) {
                item.apply {
                    tvName.text = name
                    tvUrl.text = url
                    root.setOnClickListener { onClick?.invoke(id) }
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