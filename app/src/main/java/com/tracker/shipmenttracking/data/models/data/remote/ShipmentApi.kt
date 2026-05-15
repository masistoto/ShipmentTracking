package com.tracker.shipmenttracking.data.models.data.remote

import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.ShipmentsListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ShipmentApi {
    @GET("f2ea-4a6b-4c41-a12b")
    suspend fun getShipments(): ShipmentsListResponse

    @GET("shipments/{id}")
    suspend fun getShipmentDetail(@Path("id") shipmentId: String): ShipmentDetail
}