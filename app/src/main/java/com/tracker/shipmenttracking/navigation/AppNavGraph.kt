package com.tracker.shipmenttracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tracker.shipmenttracking.com.tracker.shipmenttracking.navigation.Screen
import com.tracker.shipmenttracking.screens.ShipmentDetailScreen
import com.tracker.shipmenttracking.screens.ShipmentsListScreen

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

//        composable(
//            route = Screen.ShipmentDetail.route
//        ) {
//            ShipmentDetailScreen(
//                onBackPressed = { navController.popBackStack() }
//            )
//        }

        // TODO: test of back navigation works
        composable("shipment_detail/{id}") { backStackEntry ->
            val shipmentId = backStackEntry.arguments?.getString("id")
            ShipmentDetailScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}