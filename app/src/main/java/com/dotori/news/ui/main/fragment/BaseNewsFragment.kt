package com.dotori.news.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotori.news.R
import com.dotori.news.api.data.Status
import com.dotori.news.databinding.FragmentNewsBinding
import com.dotori.news.ui.adapter.NewsListAdapter
import com.dotori.news.ui.main.WebActivity
import com.dotori.news.ui.main.data.News
import com.dotori.news.ui.main.data.NewsUrl
import com.dotori.news.ui.main.view.OnLoadMoreListener
import com.dotori.news.ui.main.view.OnTopToScrollListener
import com.dotori.news.ui.main.view.RecyclerViewLoadMoreScroll
import com.dotori.news.ui.viewmodel.NewsViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

/**
 * A placeholder fragment containing a simple view.
 */
open class BaseNewsFragment : Fragment() {
    companion object {
        const val ITEMS_PER_AD = 10
    }

    private var section: String? = null
    private var page: Int = 1
    lateinit var scrollListener: RecyclerViewLoadMoreScroll

    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsViewModel: NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(requireActivity())
    }


    private fun showProgressDialog() {
        if (!binding.swipeLayout.isRefreshing) {
            binding.swipeLayout.isRefreshing = true
        }
    }

    private fun dismissProgressDialog() {
        if (binding.swipeLayout.isRefreshing) {
            binding.swipeLayout.isRefreshing = false
        }
    }

    fun setFragmentBinding(binding: FragmentNewsBinding) {
        this.binding = binding
    }

    fun setViewModel(viewModel: NewsViewModel) {
        this.newsViewModel = viewModel
    }

    fun setSection(section: String) {
        this.section = section
    }

    fun setLayout() {
        setSwipeRefreshLayout()
        setRecyclerView()
        setTopToScrollButton()
        setScrollListener()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeLayout.setColorSchemeResources(R.color.alive_orange);
        binding.swipeLayout.setOnRefreshListener {
            requestNews()
        }
    }

    private fun setRecyclerView() {
        binding.articlesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articlesRecyclerView.adapter = NewsListAdapter(
            ArrayList(),
            itemClicked = {
                itemClicked(it)
            })

    }

    private fun setTopToScrollButton() {
        binding.scrollToTopButton.visibility = View.INVISIBLE

        binding.scrollToTopButton.setOnClickListener {
            binding.scrollToTopButton.visibility = View.INVISIBLE

            binding.articlesRecyclerView.scrollToPosition(0)
        }
    }

    private fun setScrollListener() {
        scrollListener =
            RecyclerViewLoadMoreScroll(binding.articlesRecyclerView.layoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })

        scrollListener.setOnVisibleTopToScrollButtonListener(object : OnTopToScrollListener {
            override fun onVisibleTopToScrollButton() {
                visibleTopToScroll()
            }

            override fun onInvisibleTopToScrollButton() {
                invisibleTopToScroll()
            }
        })

        binding.articlesRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun visibleTopToScroll() {
        if (!binding.scrollToTopButton.isVisible)
            binding.scrollToTopButton.visibility = View.VISIBLE
    }

    private fun invisibleTopToScroll() {

        if (binding.scrollToTopButton.isVisible)
            binding.scrollToTopButton.visibility = View.INVISIBLE
    }

    fun requestNews() {
        Log.d("request", "requestNews $section")
        showProgressDialog()
        page = 1

        newsViewModel.requestNews(NewsUrl.getPreferredUrl(this.section))
    }

    private fun loadMoreData() {
        Log.d("scrolled", "onLoadMore()")
//            adapter.addLoadingView()
        //Use Handler if the items are loading too fast.
        //If you remove it, the data will load so fast that you can't even see the LoadingView
        page++
        requestNewsNextPage(page)
        //Remove the Loading View
//            adapter.removeLoadingView()
        //Update the recyclerView in the main thread
    }

    private fun requestNewsNextPage(page: Int) {
        newsViewModel.requestNews(NewsUrl.getPreferredUrl(section, page))
    }

    private fun itemClicked(item: News) {
        var intent = Intent(context, WebActivity::class.java)
        intent = intent.putExtra("url", item.webUrl)
        startActivity(intent)
    }

    fun subscribeObserver() {
        newsViewModel.resultNewsList.observe(this, {
            dismissProgressDialog()
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { newsList ->
                        addAdMobBannerAds(page, newsList)
                        (binding.articlesRecyclerView.adapter as NewsListAdapter).setData(newsList)
                        scrollListener.setLoaded()
                    }
                }

                Status.ERROR, Status.FAIL -> {
                    scrollListener.setLoaded()
                }

                Status.LASTPAGE -> {
                    Log.d("news", "lastpage")
                }
            }
        })
    }

    // Determine the screen width (less decorations) to use for the ad width.
    // If the ad hasn't been laid out, default to the full screen width.
    private val adSize: AdSize
        get() {
            val display = requireActivity().windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.articlesRecyclerView.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                requireActivity(),
                adWidth
            )
        }

    private fun addAdMobBannerAds(page: Int, newsList: ArrayList<Any>) {
        var i = if (page == 1) {
            ITEMS_PER_AD - 1
        } else {
            ITEMS_PER_AD - 1 + ((page - 1) * 100)
        }
        val adView = AdView(requireActivity())
        adView.adSize = AdSize.SMART_BANNER
        adView.adUnitId = resources.getString(R.string.admob_banner_ad_id)

        while (i < newsList.size) {
            if (newsList[i] !is AdView) {
                newsList.add(i, adView)
            }

            adView.loadAd(AdRequest.Builder().build())
            i += ITEMS_PER_AD
        }
    }
}