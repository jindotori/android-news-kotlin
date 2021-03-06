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

class BusinessFragment : BaseNewsFragment() {
    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(
            this@BusinessFragment,
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
        Log.d("Business", "onCreateView")

        with(binding) {
            lifecycleOwner = this@BusinessFragment
            setFragmentBinding(binding)
            setViewModel(newsViewModel)
            setSection("business")
            setLayout()
            subscribeObserver()

            requestNews()
        }

        return binding.root
    }
}