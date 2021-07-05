package com.gturedi.socialnetworkapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gturedi.socialnetworkapp.databinding.ItemCheckinBinding
import com.gturedi.socialnetworkapp.network.CheckinModel

internal class CheckinsAdapter(
    private val onRowClick: (item: CheckinModel) -> Unit
) : ListAdapter<CheckinModel, CheckinsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CheckinModel>() {
            override fun areItemsTheSame(oldItem: CheckinModel, newItem: CheckinModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CheckinModel, newItem: CheckinModel) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCheckinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ItemCheckinBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onRowClick.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: CheckinModel) {
            binding.apply {
                title.text = item.venue.name
            }
        }
    }
}
