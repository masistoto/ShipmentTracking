package com.tracker.shipmenttracking.data.models

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class ShipmentsListResponse(
    val shipments: List<ShipmentItem>
)

@InternalSerializationApi @Serializable
data class ShipmentItem(
    val id: String,
    val carrier: Carrier,
    val trackingNumber: String,
    val lastStatus: Status,
    val lastUpdatedAt: String,
    val origin: Location,
    val destination: Location,
    val estimatedDeliveryAt: String
)

@InternalSerializationApi @Serializable
data class Carrier(
    val code: String,
    val name: String
)

@InternalSerializationApi @Serializable
data class Status(
    val code: String,
    val label: String
)

@InternalSerializationApi @Serializable
data class Location(
    val city: String,
    val country: String
)

@InternalSerializationApi @Serializable
data class ShipmentDetail(
    val id: String,
    val carrier: Carrier,
    val trackingNumber: String,
    val origin: Location,
    val destination: Location,
    val estimatedDeliveryAt: String,
    val statuses: List<StatusEvent>
)

@InternalSerializationApi @Serializable
data class StatusEvent(
    val time: String,
    val code: String,
    val label: String,
    val location: String
)