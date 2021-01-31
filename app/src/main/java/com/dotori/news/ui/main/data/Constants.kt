package com.dotori.news.ui.main.data

object Constants{
    /**  Extract the key associated with the JSONObject  */
    const val JSON_KEY_RESPONSE = "response"
    const val JSON_KEY_RESULTS = "results"
    const val JSON_KEY_WEB_TITLE = "webTitle"
    const val JSON_KEY_SECTION_NAME = "sectionName"
    const val JSON_KEY_WEB_PUBLICATION_DATE = "webPublicationDate"
    const val JSON_KEY_WEB_URL = "webUrl"
    const val JSON_KEY_TAGS = "tags"
    const val JSON_KEY_FIELDS = "fields"
    const val JSON_KEY_THUMBNAIL = "thumbnail"
    const val JSON_KEY_TRAIL_TEXT = "trailText"

    /** Read timeout for setting up the HTTP request  */
    const val READ_TIMEOUT = 10000 /* milliseconds */

    /** Connect timeout for setting up the HTTP request  */
    const val CONNECT_TIMEOUT = 15000 /* milliseconds */

    /** HTTP response code when the request is successful  */
    const val SUCCESS_RESPONSE_CODE = 200

    /** Request method type "GET" for reading information from the server  */
    const val REQUEST_METHOD_GET = "GET"

    /** URL for news data from the guardian data set  */
    const val NEWS_REQUEST_URL = "https://content.guardianapis.com/"

    /**
     * ENDPOINT URL
     */
    const val SEARCH = "search"
    const val TAGS = "tags"

    /** Parameters  */
    const val QUERY_PARAM = "q"
    const val ORDER_BY_PARAM = "order-by"
    const val PAGE_SIZE_PARAM = "page-size"
    const val ORDER_DATE_PARAM = "order-date"
    const val FROM_DATE_PARAM = "from-date"
    const val SHOW_FIELDS_PARAM = "show-fields"
    const val FORMAT_PARAM = "format"
    const val SHOW_TAGS_PARAM = "show-tags"
    const val API_KEY_PARAM = "api-key"
    const val SECTION_PARAM = "section"
    const val PAGE_PARAM = "page"

    /** The show fields we want our API to return  */
    const val SHOW_FIELDS = "thumbnail,trailText"

    /** The format we want our API to return  */
    const val FORMAT = "json"

    /** The show tags we want our API to return  */
    const val SHOW_TAGS = "contributor"

    /** API Key  */
    const val API_KEY = "test" // Use your API Key when API rate limit exceeded

    /** Default number to set the image on the top of the textView  */
    const val DEFAULT_NUMBER = 0

    /** Constants value for each fragment  */
    const val HOME = 0
    const val WORLD = 1
    const val SCIENCE = 2
    const val SPORT = 3
    const val ENVIRONMENT = 4
    const val SOCIETY = 5
    const val FASHION = 6
    const val BUSINESS = 7
    const val CULTURE = 8
}