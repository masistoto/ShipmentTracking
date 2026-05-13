package com.tracker.shipmenttracking.data.models.data.remote

import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.ShipmentsListResponse
import retrofit2.http.GET
import retrofit2.http.Path

public interface ShipmentApi {
    @GET("shipments")
    suspend fun getShipments(): ShipmentsListResponse

    @GET("shipments/{shipmentId}")
    suspend fun getShipmentDetail(@Path("id") shipmentId: String): ShipmentDetail
}