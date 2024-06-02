package com.example.androidboilerplate.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
/**
 * BaseListAdapter is an abstract class that extends ListAdapter and provides a flexible
 * foundation for implementing RecyclerView adapters with ViewBinding.
 *
 * @param T The type of data model to be displayed in the list.
 * @param VB The ViewBinding type associated with the layout for each list item.
 * @param VH The RecyclerView.ViewHolder type associated with the ViewBinding.
 * @property bindingInflater A lambda function to create the ViewBinding for each list item.
 * @property diffCallback The DiffUtil.ItemCallback to calculate the difference between old and new items.
 */
abstract class BaseListAdapter<T, VB : ViewBinding, VH : RecyclerView.ViewHolder>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {

    /**
     * Creates a new ViewHolder by inflating the ViewBinding associated with the layout.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given ViewBinding type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return getViewHolder(binding)
    }

    /**
     * Binds data to the ViewHolder at the specified position.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        bindData(holder, item, position)
    }

    /**
     * Abstract method to be implemented by subclasses to create a ViewHolder.
     *
     * @param binding The ViewBinding associated with the layout of each list item.
     * @return A new instance of the RecyclerView.ViewHolder.
     */
    abstract fun getViewHolder(binding: VB): VH

    /**
     * Abstract method to be implemented by subclasses to bind data to the ViewHolder.
     *
     * @param holder The ViewHolder to bind data to.
     * @param item The data item to be displayed.
     * @param position The position of the item within the adapter's data set.
     */
    abstract fun bindData(holder: VH, item: T, position: Int)

    /**
     * Adds a single item to the list and updates the adapter.
     *
     * @param item The item to be added.
     */
    fun addItem(item: T) {
        submitList(currentList + listOf(item))
    }

    /**
     * Adds a list of items to the list and updates the adapter.
     *
     * @param newItems The list of items to be added.
     */
    fun addItems(newItems: List<T>) {
        submitList(currentList + newItems)
    }

    /**
     * Updates the entire list with a new list of items.
     *
     * @param newList The new list of items to replace the existing data set.
     */
    fun updateList(newList: List<T>) {
        submitList(newList)
    }

    /**
     * Removes a single item from the list and updates the adapter.
     *
     * @param item The item to be removed.
     */
    fun removeItem(item: T) {
        submitList(currentList - item)
    }

    /**
     * Removes an item at the specified position from the list and updates the adapter.
     *
     * @param position The position of the item to be removed.
     */
    fun removeItem(position: Int) {
        val updatedList = currentList.toMutableList()
        updatedList.removeAt(position)
        submitList(updatedList)
    }
}