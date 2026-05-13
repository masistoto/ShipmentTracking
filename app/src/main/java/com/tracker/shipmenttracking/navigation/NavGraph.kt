package com.tracker.shipmenttracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tracker.shipmenttracking.screens.ShipmentDetailScreen
import com.tracker.shipmenttracking.screens.ShipmentsListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "shipments_list"
    ) {
        composable("shipments_list") {
            ShipmentsListScreen(
                onShipmentSelected = { shipmentId ->
                    navController.navigate("shipment_detail/$shipmentId")
                }
            )
        }
        composable("shipment_detail/{shipmentId}") { backStackEntry ->
            val shipmentId = backStackEntry.arguments?.getString("shipmentId")
            ShipmentDetailScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}