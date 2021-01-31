package com.dotori.news.api

import android.util.Log
import com.dotori.news.api.data.NewsResponse
import retrofit2.Callback

class NewsApiClient(url: String) {
    private val retrofit = Retrofit().create(url)

    fun requestNews(url:String, callback: Callback<NewsResponse>) {
        Log.d("RetrofitClient", "requestNews $url")
        retrofit.requestNews(url).enqueue(callback)
    }
}