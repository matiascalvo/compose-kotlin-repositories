package com.matias.data.remote.services

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PagingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url: HttpUrl = chain.request().url.newBuilder().addQueryParameter("per_page", "30").build()

        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}
