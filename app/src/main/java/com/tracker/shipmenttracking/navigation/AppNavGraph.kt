package com.tracker.shipmenttracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tracker.shipmenttracking.com.tracker.shipmenttracking.navigation.Screen
import com.tracker.shipmenttracking.ui.screens.ShipmentDetailScreen
import com.tracker.shipmenttracking.ui.screens.ShipmentsListScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ShipmentsList.route
    ) {
        composable(Screen.ShipmentsList.route) {
            ShipmentsListScreen(
                onShipmentSelected = { id ->
                    navController.navigate(Screen.ShipmentDetail.createRoute(id))
                }
            )
        }

        composable("shipment_detail/{id}") { _ ->
            ShipmentDetailScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}