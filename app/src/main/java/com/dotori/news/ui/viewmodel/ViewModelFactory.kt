package com.dotori.news.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dotori.news.repository.NewsRepository
import java.lang.IllegalArgumentException


class NewsViewModelFactory(private val url: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            NewsViewModel(NewsRepository(url)) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}