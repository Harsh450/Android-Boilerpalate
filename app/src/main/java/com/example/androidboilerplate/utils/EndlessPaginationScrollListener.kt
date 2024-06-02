package com.example.androidboilerplate.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessPaginationScrollListener : RecyclerView.OnScrollListener() {
    private val LAYOUT_MANAGER_TYPE_LINEAR = 0
    private val LAYOUT_MANAGER_TYPE_STAGGERED_GRID = 1
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mStaggeredGridLayoutManager: StaggeredGridLayoutManager? = null
    private var mLayoutType: Int = 0
    var PAGINATION_PAGE_SIZE = 4

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (null == mLayoutManager) {
            mLayoutManager = recyclerView.layoutManager
            if (mLayoutManager is LinearLayoutManager) {
                mLayoutType = LAYOUT_MANAGER_TYPE_LINEAR
                mLinearLayoutManager = mLayoutManager as LinearLayoutManager
                (mLayoutManager as LinearLayoutManager).isItemPrefetchEnabled = false
            } else if (mLayoutManager is StaggeredGridLayoutManager) {
                mLayoutType = LAYOUT_MANAGER_TYPE_STAGGERED_GRID
                mStaggeredGridLayoutManager = mLayoutManager as StaggeredGridLayoutManager
                mStaggeredGridLayoutManager!!.isItemPrefetchEnabled = false
            }
        }
        onScrolledUpOrDown(dy > 0)
        val visibleItemCount = mLayoutManager!!.childCount
        val totalItemCount = mLayoutManager!!.itemCount
        val firstVisibleItemPosition: Int
        when (mLayoutType) {
            LAYOUT_MANAGER_TYPE_LINEAR -> {
                firstVisibleItemPosition = mLinearLayoutManager!!.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGINATION_PAGE_SIZE) {
                    requestNewPage()
                }
            }

            LAYOUT_MANAGER_TYPE_STAGGERED_GRID -> {
                val firstVisibleItemPositions =
                    mStaggeredGridLayoutManager!!.findFirstVisibleItemPositions(null)
                firstVisibleItemPosition = firstVisibleItemPositions[0]
                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGINATION_PAGE_SIZE) {
                    requestNewPage()
                }
            }
        }
    }

    protected open fun requestNewPage() {}
    protected open fun onScrolledUpOrDown(isScrollingDown: Boolean) {}
}

