package com.mostafahelal.AtmoDrive.Atmo_di

import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    @Provides
    @Singleton
    fun provideOkhttp():OkHttpClient{
        val client=OkHttpClient.Builder()
            .connectTimeout(50,TimeUnit.SECONDS)
            .writeTimeout(50,TimeUnit.SECONDS)
            .readTimeout(50,TimeUnit.SECONDS)
            .callTimeout(50,TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url
                val url = originalUrl.newBuilder().build()
                val requestBuilder = originalRequest.newBuilder().url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer ${Constants.USER_IS_LOGGED_IN}")
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                response.code
                return response
                 }
            }).build()
    return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
          return Retrofit.Builder()
              .baseUrl("https://s1.drive.api.atmosphere.solutions/api/v1/passengers/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
    @Provides
    fun provideApiService(retrofit: Retrofit):ApiServices{
        return retrofit.create(ApiServices::class.java)
    }
}