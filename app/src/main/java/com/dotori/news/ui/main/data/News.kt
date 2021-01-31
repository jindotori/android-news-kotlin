package com.dotori.news.ui.main.data

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class News (
    @SerializedName("webTitle") val webTitle: String,
    @SerializedName("sectionName") val sectionName: String,
    @SerializedName("author") val author: String,
    @SerializedName("webPublicationDate") val webPublicationDate: String,
    @SerializedName("webUrl") val webUrl: String,
    @SerializedName("thumbnail") val thumbnail: String?
)


data class NewsMetaData (
    @SerializedName("total") val total: Int,
    @SerializedName("startIndex") val startIndex: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("currentPage") val currrentPage: Int,
    @SerializedName("pages") val pages: Int
)