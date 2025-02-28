package com.trantt.omdbexample.di

import com.trantt.omdbexample.BuildConfig
import com.trantt.omdbexample.data.OMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun getClient(): OMDBApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(OMDBApi::class.java)
    }
}