package com.tracker.shipmenttracking.data.models.local

import com.tracker.shipmenttracking.data.models.ShipmentsListResponse

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

const val SHIPMENT_DATASTORE_NAME = "shipment_cache"
val SHIPMENTS_CACHE_KEY = stringPreferencesKey("cached_shipments")
val CACHE_TIMESTAMP_KEY = stringPreferencesKey("cache_timestamp")

val Context.shipmentDataStore by preferencesDataStore(name = SHIPMENT_DATASTORE_NAME)

interface ShipmentCache {
    suspend fun getCachedShipments(): ShipmentsListResponse?
    suspend fun cacheShipments(shipments: ShipmentsListResponse)
    suspend fun clearCache()
}

@Singleton
class ShipmentCacheImpl @Inject constructor(
    private val context: Context
) : ShipmentCache {

    override suspend fun getCachedShipments(): ShipmentsListResponse? {
        return try {
            val preferences = context.shipmentDataStore.data.first()
            val json = preferences[SHIPMENTS_CACHE_KEY] ?: return null
            Json.decodeFromString(json)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun cacheShipments(shipments: ShipmentsListResponse) {
        context.shipmentDataStore.edit { preferences ->
            val json = Json.encodeToString(ShipmentsListResponse.serializer(), shipments)
            preferences[SHIPMENTS_CACHE_KEY] = json
            preferences[CACHE_TIMESTAMP_KEY] = System.currentTimeMillis().toString()
        }
    }

    override suspend fun clearCache() {
        context.shipmentDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}