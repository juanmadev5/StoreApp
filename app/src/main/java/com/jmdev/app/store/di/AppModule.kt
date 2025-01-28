package com.jmdev.app.store.di

import com.jmdev.app.store.service.FakeStoreService
import com.jmdev.app.utilities.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideFakeStoreService(retrofit: Retrofit): FakeStoreService {
        return retrofit.create(FakeStoreService::class.java)
    }
}