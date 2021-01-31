package com.dotori.news.api.data

import android.net.Uri
import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class NewsResponse(
    @SerializedName("response") val response: Response
)

data class Response(
    @SerializedName("total") val total: Int,
    @SerializedName("startIndex") val startIndex: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("orderBy") val orderBy: String,
    @SerializedName("results") val results: ArrayList<Results>
)

data class Results(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("sectionId") val sectionId: String,
    @SerializedName("sectionName") val sectionName: String,
    @SerializedName("webPublicationDate") val webPublicationDate: String,
    @SerializedName("webTitle") val webTitle: String,
    @SerializedName("webUrl") val webUrl: String,
    @SerializedName("apiUrl") val apiUrl: String,
    @SerializedName("fields") val fields: Fields,
    @SerializedName("tags") val tags: ArrayList<Tags>
)

data class Tags(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("webTitle") val webTitle: String,
    @SerializedName("webUrl") val webUrl: String,
    @SerializedName("apiUrl") val apiUrl: String,
)

data class Fields (
    @SerializedName("thumbnail") val thumbnail: String?
)