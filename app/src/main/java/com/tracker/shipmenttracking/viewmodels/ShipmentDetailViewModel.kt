package com.tracker.shipmenttracking.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tracker.shipmenttracking.data.models.repository.ShipmentRepository
import com.tracker.shipmenttracking.ui.states.ShipmentDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipmentDetailViewModel @Inject constructor(
    private val repository: ShipmentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val shipmentId: String = checkNotNull(savedStateHandle["shipmentId"])

    private val _state = MutableStateFlow(ShipmentDetailState())
    val state: StateFlow<ShipmentDetailState> = _state.asStateFlow()

    init {
        loadShipmentDetail()
    }

    fun loadShipmentDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getShipmentDetail(shipmentId).collect { result ->
                result.onSuccess { detail ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            detail = detail,
                            error = null
                        )
                    }
                }.onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message,
                            detail = null
                        )
                    }
                }
            }
        }
    }
}