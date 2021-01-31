package com.dotori.news.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dotori.news.api.NewsCallback
import com.dotori.news.api.NewsApiClient
import com.dotori.news.api.data.NewsResponse
import com.dotori.news.api.data.SimpleResult
import com.dotori.news.api.data.Status
import com.dotori.news.ui.main.data.News
import com.dotori.news.ui.main.data.NewsMetaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(url:String) {
    private val client = NewsApiClient(url)
    private val news = ArrayList<Any>()

    var metaData : NewsMetaData? = null
    var resultNewsList = MutableLiveData<SimpleResult<ArrayList<Any>>>()

    suspend fun requestNews(url:String) = withContext(Dispatchers.IO) {
        Log.d("NewsRepository", "requestNews $url")
        client.requestNews(url, object : NewsCallback<NewsResponse>() {
            override fun onFail(code: Int, result: String) {
                resultNewsList.postValue(when(code) {
                    0 -> SimpleResult(Status.ERROR, result)
                    else -> SimpleResult(Status.FAIL, result)
                })
            }

            override fun onSuccess(result: NewsResponse?) {
                val response = result!!.response
                if (response.currentPage == 1) {
                    news.clear()
                }
                for(item in response.results) {
                    val author = if (item.tags.size != 0) {
                        item.tags[0].webTitle
                    } else {
                        "Anonymous"
                    }
                    news.add(News(item.webTitle,
                        item.sectionName,
                        author,
                        item.webPublicationDate,
                        item.webUrl,
                        item.fields.thumbnail))
                }
                metaData = NewsMetaData(response.total, response.startIndex, response.pageSize, response.currentPage, response.pages)
                resultNewsList.postValue(SimpleResult(Status.SUCCESS, news))
            }
        })
    }


}