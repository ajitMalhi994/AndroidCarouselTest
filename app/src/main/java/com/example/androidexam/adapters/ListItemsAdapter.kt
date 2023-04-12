package com.example.androidexam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexam.R
import com.example.androidexam.data.ListItem
import com.example.androidexam.databinding.ListItemBinding

class ListItemsAdapter: ListAdapter<ListItem, ListItemsAdapter.LabelItemViewHolder>(DefCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelItemViewHolder {
        val binderObj = DataBindingUtil.inflate<ListItemBinding>(
            LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
        return LabelItemViewHolder(binderObj)
    }

    override fun onBindViewHolder(holder: LabelItemViewHolder, position: Int) {
        holder.bindingObj.labelData = getItem(position)
    }

    inner class LabelItemViewHolder(var bindingObj: ListItemBinding): RecyclerView.ViewHolder(bindingObj.root){
    }
}

class DefCallBack : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.description == newItem.description
    }
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}