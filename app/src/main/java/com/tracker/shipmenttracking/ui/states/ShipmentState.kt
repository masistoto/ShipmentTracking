package com.tracker.shipmenttracking.ui.states

import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.ShipmentItem

data class ShipmentsListState(
    val isLoading: Boolean = false,
    val shipments: List<ShipmentItem> = emptyList(),
    val error: String? = null,
    val isOffline: Boolean = false,
    val searchQuery: String = "",
    val selectedStatus: String? = null
)

data class ShipmentDetailState(
    val isLoading: Boolean = false,
    val detail: ShipmentDetail? = null,
    val error: String? = null,
    val isOffline: Boolean = false
)