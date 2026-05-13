package com.tracker.shipmenttracking.di

import android.content.Context
import com.tracker.shipmenttracking.data.models.data.remote.ShipmentApi
import com.tracker.shipmenttracking.data.models.local.ShipmentCache
import com.tracker.shipmenttracking.data.models.local.ShipmentCacheImpl
import com.tracker.shipmenttracking.data.models.repository.ShipmentRepository
import com.tracker.shipmenttracking.data.models.repository.ShipmentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("BASE_URL") // TODO: Get API base url from Offerzen
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ShipmentApi {
        return retrofit.create(ShipmentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideShipmentCache(
        @ApplicationContext context: Context
    ): ShipmentCache {
        return ShipmentCacheImpl(context)
    }

    @Provides
    @Singleton
    fun provideShipmentRepository(
        api: ShipmentApi,
        cache: ShipmentCache
    ): ShipmentRepository {
        return ShipmentRepositoryImpl(api, cache)
    }
}