package com.matias.data.di

import com.matias.data.BuildConfig
import com.matias.data.remote.services.PagingInterceptor
import com.matias.data.remote.services.SearchServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun searchService(retrofit: Retrofit): SearchServiceApi =
        retrofit.create(SearchServiceApi::class.java)

    @Provides
    @Singleton
    fun retrofit2(@Named("github-base-url") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun okhttp(loggingInterceptor: HttpLoggingInterceptor, pagingInterceptor: PagingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(pagingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun pagingInterceptor(): PagingInterceptor = PagingInterceptor()
}
