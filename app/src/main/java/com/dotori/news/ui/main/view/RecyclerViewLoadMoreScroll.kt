package com.dotori.news.ui.main.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewLoadMoreScroll : RecyclerView.OnScrollListener {

    private var visibleThreshold = 100
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private lateinit var topToScrollListener: OnTopToScrollListener
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var firstVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var mLayoutManager: RecyclerView.LayoutManager

    fun setLoaded() {
        isLoading = false
    }

    fun getLoaded(): Boolean {
        return isLoading
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setOnVisibleTopToScrollButtonListener(topToScrollListener: OnTopToScrollListener) {
        this.topToScrollListener = topToScrollListener
    }

    constructor(layoutManager: LinearLayoutManager) {
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        this.mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                // get maximum element within the list
                lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                firstVisibleItem =
                    (mLayoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                lastVisibleItem =
                    (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                firstVisibleItem =
                    (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                lastVisibleItem =
                    (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }

        if (dy <= 0) {
            if (firstVisibleItem == 0) {
                topToScrollListener.onInvisibleTopToScrollButton()
            }
        } else {
            topToScrollListener.onVisibleTopToScrollButton()
            totalItemCount = mLayoutManager.itemCount

            if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                mOnLoadMoreListener.onLoadMore()
                isLoading = true
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}

interface OnLoadMoreListener {
    fun onLoadMore()
}

interface OnTopToScrollListener {
    fun onVisibleTopToScrollButton()
    fun onInvisibleTopToScrollButton()
}