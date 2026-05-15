package com.tracker.shipmenttracking.com.tracker.shipmenttracking.navigation

sealed class Screen(val route: String) {

    object ShipmentsList : Screen("shipments_list")

    object ShipmentDetail : Screen("shipment_detail/{shipmentId}") {

        fun createRoute(shipmentId: String) =
            "shipment_detail/$shipmentId"
    }
}