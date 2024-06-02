package com.example.androidboilerplate.screens.home.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.androidboilerplate.core.BaseRecyclerViewAdapter
import com.example.androidboilerplate.databinding.ItemMusicAlbumsBinding
import com.example.androidboilerplate.screens.home.model.network.Result
import com.example.androidboilerplate.utils.setOnSafeClickListener

class HomeAdapter(val context: Context, val onItemClickListener: (Result) -> Unit) :
    BaseRecyclerViewAdapter<Result, ItemMusicAlbumsBinding, HomeAdapter.ViewHolder>(
        ItemMusicAlbumsBinding::inflate
    ) {

    inner class ViewHolder(private val itemBinding: ItemMusicAlbumsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(result: Result) {
            Glide.with(context).load(result.artworkUrl100).skipMemoryCache(false).diskCacheStrategy(
                DiskCacheStrategy.ALL
            ).into(itemBinding.imageView)
            itemBinding.textView.text = result.artistName
            itemView.setOnSafeClickListener {
                onItemClickListener(result)
            }
        }
    }

    override fun getViewHolder(binding: ItemMusicAlbumsBinding): ViewHolder {
        return ViewHolder(binding)
    }

    override fun bindData(holder: ViewHolder, item: Result?, position: Int) {
        if (item != null) {
            holder.bindData(item)
        }
    }
}