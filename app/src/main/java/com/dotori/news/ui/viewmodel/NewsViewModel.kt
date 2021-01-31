package com.dotori.news.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dotori.news.repository.NewsRepository
import kotlinx.coroutines.*

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val resultNewsList = newsRepository.resultNewsList

    fun requestNews(url:String) {
        Log.d("ViewModel", "requestNews $url")
        GlobalScope.launch {
            Log.d("ViewModel", "requestNews coroutine $url")

            newsRepository.requestNews(url)
        }
    }
}