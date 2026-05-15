package com.tracker.shipmenttracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.StatusEvent
import com.tracker.shipmenttracking.viewmodels.ShipmentDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentDetailScreen(
    viewModel: ShipmentDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Shipment Details") },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when {
                state.isLoading -> {
                    LoadingState()
                }
                state.error != null -> {
                    ErrorState(error = state.error!!, onRetry = { viewModel.loadShipmentDetail() })
                }
                state.detail != null -> {
                    DetailContent(detail = state.detail!!)
                }
            }
        }
    }
}

@Composable
fun DetailContent(detail: ShipmentDetail) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Carrier Info
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = detail.carrier.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Code: ${detail.carrier.code.uppercase()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Tracking Number
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Tracking Number",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = detail.trackingNumber,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Route Info
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Route",
                        style = MaterialTheme.typography.titleMedium
                    )
                    RouteRow(
                        label = "From",
                        location = "${detail.origin.city}, ${detail.origin.country}"
                    )
                    RouteRow(
                        label = "To",
                        location = "${detail.destination.city}, ${detail.destination.country}"
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = DividerDefaults.Thickness,
                        color = DividerDefaults.color
                    )
                    RouteRow(
                        label = "Est. Delivery",
                        location = formatDetailDate(detail.estimatedDeliveryAt)
                    )
                }
            }
        }

        // Status Timeline
        item {
            Text(
                text = "Status History",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(detail.statuses.size) { index ->
            val status = detail.statuses[index]
            StatusTimelineItem(
                status = status,
                isFirst = index == 0,
                isLast = index == detail.statuses.size - 1
            )
        }
    }
}

@Composable
fun RouteRow(
    label: String,
    location: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = location,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun StatusTimelineItem(
    status: StatusEvent,
    isFirst: Boolean = false,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Timeline line and dot
        Column(
            modifier = Modifier
                .width(40.dp)
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isFirst) {
                HorizontalDivider(
                    modifier = Modifier
                        .height(8.dp)
                        .width(2.dp), thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            }
            Surface(
                modifier = Modifier
                    .size(20.dp),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Box(contentAlignment = Alignment.Center) {}
            }
            if (!isLast) {
                HorizontalDivider(
                    modifier = Modifier
                        .height(8.dp)
                        .width(2.dp), thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            }
        }

        // Status content
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = if (isFirst) 0.dp else 8.dp, bottom = if (isLast) 0.dp else 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = status.label,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = formatDetailTime(status.time),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = status.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = status.code,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

fun formatDetailDate(isoString: String): String {
    return try {
        val parts = isoString.split("T")
        parts[0] // YYYY-MM-DD
    } catch (e: Exception) {
        isoString
    }
}

fun formatDetailTime(isoString: String): String {
    return try {
        val parts = isoString.split("T")
        if (parts.size >= 2) {
            val time = parts[1].split("Z")[0]
            "${parts[0]} ${time.substring(0, 5)}"
        } else {
            isoString
        }
    } catch (e: Exception) {
        isoString
    }
}