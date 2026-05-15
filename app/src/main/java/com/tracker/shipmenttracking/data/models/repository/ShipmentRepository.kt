package com.tracker.shipmenttracking.data.models.repository

import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.ShipmentsDetailsResponse
import com.tracker.shipmenttracking.data.models.ShipmentsListResponse
import com.tracker.shipmenttracking.data.models.data.remote.ShipmentApi
import com.tracker.shipmenttracking.data.models.local.ShipmentCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

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
            val rawJson =
                "{\"shipments\":[{\"id\":\"shp_1001\",\"carrier\":{\"code\":\"ups\",\"name\":\"UPS\"},\"trackingNumber\":\"1Z999AA10123456784\",\"lastStatus\":{\"code\":\"IN_TRANSIT\",\"label\":\"In transit\"},\"lastUpdatedAt\":\"2026-02-26T14:20:00Z\",\"origin\":{\"city\":\"Seattle\",\"country\":\"US\"},\"destination\":{\"city\":\"Austin\",\"country\":\"US\"},\"estimatedDeliveryAt\":\"2026-03-02T23:00:00Z\"},{\"id\":\"shp_1002\",\"carrier\":{\"code\":\"fedex\",\"name\":\"FedEx\"},\"trackingNumber\":\"123456789012\",\"lastStatus\":{\"code\":\"DELIVERED\",\"label\":\"Delivered\"},\"lastUpdatedAt\":\"2026-02-25T09:05:00Z\",\"origin\":{\"city\":\"Chicago\",\"country\":\"US\"},\"destination\":{\"city\":\"New York\",\"country\":\"US\"},\"estimatedDeliveryAt\":\"2026-02-25T18:00:00Z\"}]}"
            val json = Json { ignoreUnknownKeys = true }
            val shipments = json.decodeFromString<ShipmentsListResponse>(rawJson)
            // Try to fetch from remote
            //val shipments = api.getShipments()
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
            //val detail = api.getShipmentDetail(shipmentId)
            val rawShipmentsDetail = "{\n" +
                    "  \"shipmentsDetails\": [\n" +
                    "    {\n" +
                    "      \"id\": \"shp_1001\",\n" +
                    "      \"carrier\": { \"code\": \"ups\", \"name\": \"UPS\" },\n" +
                    "      \"trackingNumber\": \"1Z999AA10123456784\",\n" +
                    "      \"origin\": { \"city\": \"Seattle\", \"country\": \"US\" },\n" +
                    "      \"destination\": { \"city\": \"Austin\", \"country\": \"US\" },\n" +
                    "      \"estimatedDeliveryAt\": \"2026-03-02T23:00:00Z\",\n" +
                    "      \"statuses\": [\n" +
                    "        {\n" +
                    "          \"time\": \"2026-02-26T14:20:00Z\",\n" +
                    "          \"code\": \"IN_TRANSIT\",\n" +
                    "          \"label\": \"Departed facility\",\n" +
                    "          \"location\": \"Portland, OR\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"time\": \"2026-02-25T08:10:00Z\",\n" +
                    "          \"code\": \"LABEL_CREATED\",\n" +
                    "          \"label\": \"Label created\",\n" +
                    "          \"location\": \"Seattle, WA\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": \"shp_1002\",\n" +
                    "      \"carrier\": { \"code\": \"fedex\", \"name\": \"FedEx\" },\n" +
                    "      \"trackingNumber\": \"123456789012\",\n" +
                    "      \"origin\": { \"city\": \"Chicago\", \"country\": \"US\" },\n" +
                    "      \"destination\": { \"city\": \"New York\", \"country\": \"US\" },\n" +
                    "      \"estimatedDeliveryAt\": \"2026-02-25T18:00:00Z\",\n" +
                    "      \"statuses\": [\n" +
                    "        {\n" +
                    "          \"time\": \"2026-02-25T09:05:00Z\",\n" +
                    "          \"code\": \"DELIVERED\",\n" +
                    "          \"label\": \"Delivered to front door\",\n" +
                    "          \"location\": \"New York, NY\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"time\": \"2026-02-25T06:30:00Z\",\n" +
                    "          \"code\": \"OUT_FOR_DELIVERY\",\n" +
                    "          \"label\": \"Out for delivery\",\n" +
                    "          \"location\": \"New York, NY\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"time\": \"2026-02-24T22:15:00Z\",\n" +
                    "          \"code\": \"IN_TRANSIT\",\n" +
                    "          \"label\": \"Arrived at destination facility\",\n" +
                    "          \"location\": \"Newark, NJ\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"time\": \"2026-02-24T11:40:00Z\",\n" +
                    "          \"code\": \"IN_TRANSIT\",\n" +
                    "          \"label\": \"Departed facility\",\n" +
                    "          \"location\": \"Philadelphia, PA\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"time\": \"2026-02-23T09:00:00Z\",\n" +
                    "          \"code\": \"LABEL_CREATED\",\n" +
                    "          \"label\": \"Label created\",\n" +
                    "          \"location\": \"Chicago, IL\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}"

            val json = Json { ignoreUnknownKeys = true }
            val shipmentsDetails = json.decodeFromString<ShipmentsDetailsResponse>(rawShipmentsDetail)
            val shipment = shipmentsDetails.shipmentsDetails.find { it.id == id }

            if (shipment != null) {
                emit(Result.success(shipment))
            } else {
                emit(Result.failure(Exception("Shipment not found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}