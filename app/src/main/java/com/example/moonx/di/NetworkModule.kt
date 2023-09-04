package com.example.moonx.di

import android.content.Context
import androidx.room.Room
import com.example.moonx.api.GptAPI
import com.example.moonx.api.WeatherAPI
import com.example.moonx.repo.HomeRepository
import com.example.moonx.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(weatherAPI: WeatherAPI,gptAPI:GptAPI) : HomeRepository{
        return HomeRepository(weatherAPI,gptAPI)
    }

    @Provides
    @Singleton
    fun provideGPT() : GptAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_GPT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GptAPI::class.java)
    }



}
