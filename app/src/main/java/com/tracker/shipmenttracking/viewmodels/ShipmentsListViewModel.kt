package com.tracker.shipmenttracking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tracker.shipmenttracking.data.models.ShipmentItem
import com.tracker.shipmenttracking.data.models.repository.ShipmentRepository
import com.tracker.shipmenttracking.ui.states.ShipmentsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipmentsListViewModel @Inject constructor(
    private val repository: ShipmentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ShipmentsListState())
    val state: StateFlow<ShipmentsListState> = _state.asStateFlow()

    init {
        loadShipments()
    }

    fun loadShipments() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getShipments().collect { result ->
                result.onSuccess { response ->
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            shipments = response.shipments,
                            error = null,
                            isOffline = false
                        )
                    }
                }.onFailure { exception ->
                    _state.update { state ->
                        // Check if we have offline data
                        val hasOfflineData = state.shipments.isNotEmpty()
                        state.copy(
                            isLoading = false,
                            error = if (hasOfflineData) null else exception.message,
                            isOffline = hasOfflineData
                        )
                    }
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun updateSelectedStatus(status: String?) {
        _state.update { it.copy(selectedStatus = status) }
    }

    fun getFilteredShipments(): List<ShipmentItem> {
        val currentState = _state.value
        var filtered = currentState.shipments

        // Filter by search query
        if (currentState.searchQuery.isNotBlank()) {
            val query = currentState.searchQuery.lowercase()
            filtered = filtered.filter { shipment ->
                shipment.trackingNumber.lowercase().contains(query) ||
                        shipment.carrier.name.lowercase().contains(query) ||
                        shipment.carrier.code.lowercase().contains(query)
            }
        }

        // Filter by status
        if (!currentState.selectedStatus.isNullOrBlank()) {
            filtered = filtered.filter { it.lastStatus.code == currentState.selectedStatus }
        }

        return filtered
    }
}