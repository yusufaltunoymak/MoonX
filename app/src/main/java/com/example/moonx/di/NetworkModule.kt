package com.example.moonx.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.moonx.BuildConfig
import com.example.moonx.api.GptAPI
import com.example.moonx.api.WeatherAPI
import com.example.moonx.repo.HomeRepository
import com.example.moonx.util.Constants
import com.example.moonx.viewmodel.MeditationViewModel
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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideGPT(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): GptAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_GPT)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(GptAPI::class.java)
    }


    @Provides
    @Singleton
    fun provideHomeRepository(weatherAPI: WeatherAPI, gptAPI: GptAPI): HomeRepository {
        return HomeRepository(weatherAPI, gptAPI)
    }

    @Provides
    @Singleton
    fun provideMeditationViewModel(application: Application): MeditationViewModel {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MeditationViewModel::class.java)
    }
}
