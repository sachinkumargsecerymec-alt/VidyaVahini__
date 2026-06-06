package com.vidyavahini.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vidyavahini.app.databinding.ItemTitleSubtitleBinding

data class TitleSubtitle(val title: String, val subtitle: String, val id: String = title)

class TitleSubtitleAdapter(private val onClick: (TitleSubtitle) -> Unit = {}) : RecyclerView.Adapter<TitleSubtitleAdapter.Holder>() {
    private val items = mutableListOf<TitleSubtitle>()

    fun submitList(newItems: List<TitleSubtitle>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemTitleSubtitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(items[position])

    inner class Holder(private val binding: ItemTitleSubtitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TitleSubtitle) {
            binding.titleText.text = item.title
            binding.subtitleText.text = item.subtitle
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}
