package com.tracker.shipmenttracking.data.models.repository

import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.ShipmentsListResponse
import com.tracker.shipmenttracking.data.models.data.remote.MockRestAPIServer
import com.tracker.shipmenttracking.data.models.data.remote.ShipmentApi
import com.tracker.shipmenttracking.data.models.local.ShipmentCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ShipmentRepository {
    fun getShipments(): Flow<Result<ShipmentsListResponse>>
    fun getShipmentDetail(id: String): Flow<Result<ShipmentDetail>>
}

class ShipmentRepositoryImpl @Inject constructor(
    private val api: ShipmentApi,
    private val cache: ShipmentCache
) : ShipmentRepository {

    override fun getShipments(): Flow<Result<ShipmentsListResponse>> = flow {
        try {
            //val shipments = api.getShipments() // Real API Call
            val shipments = MockRestAPIServer.getShipments() // Mock Server
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

    override fun getShipmentDetail(id: String): Flow<Result<ShipmentDetail>> = flow {
        try {
            //val detail = api.getShipmentDetail(shipmentId) // Real API Call
            val shipmentDetail = MockRestAPIServer.getShipmentDetail(id) // Mock Server
            emit(Result.success(shipmentDetail))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}