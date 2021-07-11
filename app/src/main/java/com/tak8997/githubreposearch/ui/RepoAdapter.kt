package com.tak8997.githubreposearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.githubreposearch.data.model.Repo
import com.tak8997.githubreposearch.databinding.ItemRepoBinding

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private val items = mutableListOf<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Repo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Repo>) {
        val pos = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(pos, itemCount)
    }

    class ViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.item = repo
            binding.executePendingBindings()
        }
    }
}