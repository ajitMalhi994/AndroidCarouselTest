package com.example.androidexam.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.androidexam.data.ListItem
import com.example.androidexam.ui.views.ViewStatus
import com.example.androidexam.ui.views.ViewStatusLayout

@BindingAdapter("loadImageTo")
fun ImageView.loadImageTo(url: String?) {
    if (url.isNullOrEmpty()) return
    loadImage(url)
}

fun View.makeGoneVisible(list: ArrayList<ListItem>){
    if (list.isEmpty()) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("app:status")
fun ViewStatusLayout.setData(status: ViewStatus?) = setStatus(status)