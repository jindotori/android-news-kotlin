package com.dotori.news.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dotori.news.databinding.FragmentNewsBinding
import com.dotori.news.ui.main.data.Constants
import com.dotori.news.ui.viewmodel.NewsViewModel
import com.dotori.news.ui.viewmodel.NewsViewModelFactory

class OpinionFragment : BaseNewsFragment() {
    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(
            this@OpinionFragment,
            NewsViewModelFactory(Constants.NEWS_REQUEST_URL)
        ).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        Log.d("Opinion", "onCreateView")

        with(binding) {
            lifecycleOwner = this@OpinionFragment
            setFragmentBinding(binding)
            setViewModel(newsViewModel)
            setSection("opinion")
            setLayout()
            subscribeObserver()

            requestNews()
        }

        return binding.root
    }
}