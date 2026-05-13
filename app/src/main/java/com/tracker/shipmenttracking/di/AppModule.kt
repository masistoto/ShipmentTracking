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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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