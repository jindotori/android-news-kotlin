package com.dotori.news.api


import com.dotori.news.api.data.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface NewsService {
    @GET
    fun requestNews(@Url  url:String) : Call<NewsResponse>
}