package com.dotori.news.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class NewsCallback<T> : Callback<T> {
    companion object {
        private const val TAG = "SimpleCallback"
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {

        when(response.code()) {
            200 -> {
                 Log.d(TAG,"[onResponse] Status Success(200) : ${response.body()}")
                onSuccess(response.body())
            }

            else -> {
                Log.e(TAG, "[onResponse] Status Fail(${response.code()})")

                val failCode = response.code()
                val failMessage = FailData.values().find {
                    it.failCode == failCode
                }?.failMsg ?: FailData.FAIL.failMsg

                onFail(failCode, failMessage)
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "[onResponse] Status Error(${t.message})")
        with(FailData.FAIL) {
            onFail(failCode, failMsg)
        }
    }

    abstract fun onSuccess(result: T?)
    abstract fun onFail(code: Int, result: String)
}

enum class FailData(val failCode: Int, val failMsg: String) {
    FAIL(0, "Fail."),
    FAIL_BAD_REQUEST(400, "Bad Request. Please check parameters."),
    FAIL_UNAUTHORIZED(401, "Unauthorized. Please check authority(token)."),
    FAIL_FORBIDDEN(403, "Forbidden. Please check authority."),
    FAIL_BAD_REQUEST2(404, "Bad Request. Please check parameters."),
    FAIL_TOO_MUCH_CALL(404, "Bad Request. Please wait for a while."),
    FAIL_INTERNAL_SERVER(500, "Internal server error."),
    FAIL_BAD_GATEWAY(502, "Bad Gateway."),
    FAIL_SERVICE_UNAVAILABLE(503, "Service under maintenance.")
}