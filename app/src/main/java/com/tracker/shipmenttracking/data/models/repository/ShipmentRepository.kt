package com.tracker.shipmenttracking.data.models.repository

import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.ShipmentsListResponse
import com.tracker.shipmenttracking.data.models.data.remote.ShipmentApi
import com.tracker.shipmenttracking.data.models.local.ShipmentCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ShipmentRepository {
    fun getShipments(): Flow<Result<ShipmentsListResponse>>
    fun getShipmentDetail(shipmentId: String): Flow<Result<ShipmentDetail>>
}

class ShipmentRepositoryImpl @Inject constructor(
    private val api: ShipmentApi,
    private val cache: ShipmentCache
) : ShipmentRepository {

    override fun getShipments(): Flow<Result<ShipmentsListResponse>> = flow {
        try {
            // Try to fetch from remote
            val shipments = api.getShipments()
            // Cache the successful result
            cache.cacheShipments(shipments)
            emit(Result.success(shipments))
        } catch (e: Exception) {
            // Fall back to cached data if remote fails
            val cachedData = cache.getCachedShipments()
            if (cachedData != null) {
                emit(Result.success(cachedData))
            } else {
                emit(Result.failure(e))
            }
        }
    }

    override fun getShipmentDetail(shipmentId: String): Flow<Result<ShipmentDetail>> = flow {
        try {
            val detail = api.getShipmentDetail(shipmentId)
            emit(Result.success(detail))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}