package com.tracker.shipmenttracking.data.models.data.remote

import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.ShipmentsDetailsResponse
import com.tracker.shipmenttracking.data.models.ShipmentsListResponse
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.Path

interface ShipmentApi {
    @GET("f2ea-4a6b-4c41-a12b")
    suspend fun getShipments(): ShipmentsListResponse

    @GET("shipments/{id}")
    suspend fun getShipmentDetail(@Path("id") id: String): ShipmentDetail
}

class MockRestAPIServer
{
    companion object {

        fun getShipments(): ShipmentsListResponse
        {
            val rawJson =
                "{\"shipments\":[{\"id\":\"shp_1001\",\"carrier\":{\"code\":\"ups\",\"name\":\"UPS\"},\"trackingNumber\":\"1Z999AA10123456784\",\"lastStatus\":{\"code\":\"IN_TRANSIT\",\"label\":\"In transit\"},\"lastUpdatedAt\":\"2026-02-26T14:20:00Z\",\"origin\":{\"city\":\"Seattle\",\"country\":\"US\"},\"destination\":{\"city\":\"Austin\",\"country\":\"US\"},\"estimatedDeliveryAt\":\"2026-03-02T23:00:00Z\"},{\"id\":\"shp_1002\",\"carrier\":{\"code\":\"fedex\",\"name\":\"FedEx\"},\"trackingNumber\":\"123456789012\",\"lastStatus\":{\"code\":\"DELIVERED\",\"label\":\"Delivered\"},\"lastUpdatedAt\":\"2026-02-25T09:05:00Z\",\"origin\":{\"city\":\"Chicago\",\"country\":\"US\"},\"destination\":{\"city\":\"New York\",\"country\":\"US\"},\"estimatedDeliveryAt\":\"2026-02-25T18:00:00Z\"}]}"
            val json = Json { ignoreUnknownKeys = true }

            return json.decodeFromString<ShipmentsListResponse>(rawJson)
        }

        fun getShipmentDetail(id: String): ShipmentDetail
        {
            val rawShipmentsDetail =
                "{\"shipmentsDetails\":[{\"id\":\"shp_1001\",\"carrier\":{\"code\":\"ups\",\"name\":\"UPS\"},\"trackingNumber\":\"1Z999AA10123456784\",\"origin\":{\"city\":\"Seattle\",\"country\":\"US\"},\"destination\":{\"city\":\"Austin\",\"country\":\"US\"},\"estimatedDeliveryAt\":\"2026-03-02T23:00:00Z\",\"statuses\":[{\"time\":\"2026-02-26T14:20:00Z\",\"code\":\"IN_TRANSIT\",\"label\":\"Departed facility\",\"location\":\"Portland, OR\"},{\"time\":\"2026-02-25T08:10:00Z\",\"code\":\"LABEL_CREATED\",\"label\":\"Label created\",\"location\":\"Seattle, WA\"}]},{\"id\":\"shp_1002\",\"carrier\":{\"code\":\"fedex\",\"name\":\"FedEx\"},\"trackingNumber\":\"123456789012\",\"origin\":{\"city\":\"Chicago\",\"country\":\"US\"},\"destination\":{\"city\":\"New York\",\"country\":\"US\"},\"estimatedDeliveryAt\":\"2026-02-25T18:00:00Z\",\"statuses\":[{\"time\":\"2026-02-25T09:05:00Z\",\"code\":\"DELIVERED\",\"label\":\"Delivered to front door\",\"location\":\"New York, NY\"},{\"time\":\"2026-02-25T06:30:00Z\",\"code\":\"OUT_FOR_DELIVERY\",\"label\":\"Out for delivery\",\"location\":\"New York, NY\"},{\"time\":\"2026-02-24T22:15:00Z\",\"code\":\"IN_TRANSIT\",\"label\":\"Arrived at destination facility\",\"location\":\"Newark, NJ\"},{\"time\":\"2026-02-24T11:40:00Z\",\"code\":\"IN_TRANSIT\",\"label\":\"Departed facility\",\"location\":\"Philadelphia, PA\"},{\"time\":\"2026-02-23T09:00:00Z\",\"code\":\"LABEL_CREATED\",\"label\":\"Label created\",\"location\":\"Chicago, IL\"}]}]}"

            val json = Json { ignoreUnknownKeys = true }
            val shipmentsDetails = json.decodeFromString<ShipmentsDetailsResponse>(rawShipmentsDetail)
            val shipment = shipmentsDetails.shipmentsDetails.find { it.id == id }
            return shipment!!
        }
    }
}