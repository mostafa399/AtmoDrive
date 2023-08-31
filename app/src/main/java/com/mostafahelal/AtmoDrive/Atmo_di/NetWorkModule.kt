package com.mostafahelal.AtmoDrive.Atmo_di

import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
const val BASE_URL="https://s1.drive.api.atmosphere.solutions/api/v1/passengers/"
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
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
        return client
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
          return Retrofit.Builder()
              .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiServices{
        return retrofit.create(ApiServices::class.java)
    }
}