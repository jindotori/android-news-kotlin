package com.dotori.news.ui.main.data

import android.net.Uri

object NewsUrl {
    private fun getDefaultUrl() : Uri.Builder {
        val uriBuilder = Uri.Builder()
            .appendPath(Constants.SEARCH)

        // Append query parameter and its value. (e.g. the 'show-tag=contributor')
        uriBuilder.appendQueryParameter(Constants.QUERY_PARAM, "")
        uriBuilder.appendQueryParameter(Constants.ORDER_BY_PARAM, "newest")
        uriBuilder.appendQueryParameter(Constants.ORDER_DATE_PARAM, "published")
        uriBuilder.appendQueryParameter(Constants.PAGE_SIZE_PARAM, "100")
        uriBuilder.appendQueryParameter(Constants.SHOW_FIELDS_PARAM, Constants.SHOW_FIELDS)
//        uriBuilder.appendQueryParameter(Constants.FROM_DATE_PARAM, "2020-11-20")
        uriBuilder.appendQueryParameter(Constants.FORMAT_PARAM, Constants.FORMAT)
        uriBuilder.appendQueryParameter(Constants.SHOW_TAGS_PARAM, Constants.SHOW_TAGS)
        uriBuilder.appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY) // Use your API key when API rate limit exceeded

        return uriBuilder
    }

    fun getPreferredUrl(section: String?): String {
        val uriBuilder = getDefaultUrl()
        return if (section == "home")
            uriBuilder.toString()
        else
            uriBuilder.appendQueryParameter(Constants.SECTION_PARAM, section).toString()
    }


    fun getPreferredUrl(section: String?, page: Int): String {
        val uriBuilder = getDefaultUrl()
        uriBuilder.appendQueryParameter(Constants.PAGE_PARAM, page.toString()).toString()
        return if (section == "home")
            uriBuilder.toString()
        else
            uriBuilder.appendQueryParameter(Constants.SECTION_PARAM, section).toString()
    }
}