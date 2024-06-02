package com.example.androidboilerplate.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseRecyclerViewAdapter<T, VB : ViewBinding, VH : RecyclerView.ViewHolder>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
) : RecyclerView.Adapter<VH>() {
    private var mList: ArrayList<T> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return getViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder.adapterPosition >= mList.size) {
            bindData(holder, null, position)
            return
        }
        val item = mList[position]
        bindData(holder, item, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    abstract fun getViewHolder(binding: VB): VH
    abstract fun bindData(holder: VH, item: T?, position: Int)
    fun clear() {
        val oldSize = mList.size
        mList.clear()
        notifyItemRangeRemoved(0, oldSize)
    }

    fun setData(list: List<T>) {
        val oldSize = mList.size
        this.mList.addAll(list)
        notifyItemRangeRemoved(0, oldSize);
        notifyItemRangeInserted(0, mList.size)
    }

    fun addData(list: List<T>) {
        val oldSize = mList.size
        mList.addAll(list)
        notifyItemRangeInserted(oldSize, mList.size)
    }

    fun updateItem(position: Int, item: T) {
        mList[position] = item
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: T) {
        val position = mList.size
        mList.add(item)
        notifyItemInserted(position)
    }
}