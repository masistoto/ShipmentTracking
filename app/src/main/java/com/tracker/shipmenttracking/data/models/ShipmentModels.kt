package com.tracker.shipmenttracking.data.models

data class ShipmentsListResponse(
    val shipments: List<ShipmentItem>
)

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

data class Carrier(
    val code: String,
    val name: String
)

data class Status(
    val code: String,
    val label: String
)

data class Location(
    val city: String,
    val country: String
)

data class ShipmentDetail(
    val id: String,
    val carrier: Carrier,
    val trackingNumber: String,
    val origin: Location,
    val destination: Location,
    val estimatedDeliveryAt: String,
    val statuses: List<StatusEvent>
)

data class StatusEvent(
    val time: String,
    val code: String,
    val label: String,
    val location: String
)