package com.dotori.news.api.data

enum class Status {
    SUCCESS,
    LASTPAGE,
    FAIL,
    ERROR
}

class SimpleResult<T>(val status: Status, val data: T?, val message: String?) {
    constructor(status: Status, data: T?) : this(status, data, null)
    constructor(status: Status, message: String?) : this(status, null, message)
}