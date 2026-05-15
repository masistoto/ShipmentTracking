package com.tracker.shipmenttracking.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ShipmentsListResponse(
    val shipments: List<ShipmentItem>
)

@Serializable
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

@Serializable
data class Carrier(
    val code: String,
    val name: String
)

@Serializable
data class Status(
    val code: String,
    val label: String
)

@Serializable
data class Location(
    val city: String,
    val country: String
)

@Serializable
data class ShipmentsDetailsResponse(
    val shipmentsDetails: List<ShipmentDetail>
)


@Serializable
data class ShipmentDetail(
    val id: String,
    val carrier: Carrier,
    val trackingNumber: String,
    val origin: Location,
    val destination: Location,
    val estimatedDeliveryAt: String,
    val statuses: List<StatusEvent>
)

@Serializable
data class StatusEvent(
    val time: String,
    val code: String,
    val label: String,
    val location: String
)
