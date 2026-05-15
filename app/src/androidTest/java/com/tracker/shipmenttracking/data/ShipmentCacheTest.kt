package com.tracker.shipmenttracking.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tracker.shipmenttracking.data.models.Carrier
import com.tracker.shipmenttracking.data.models.Location
import com.tracker.shipmenttracking.data.models.ShipmentItem
import com.tracker.shipmenttracking.data.models.ShipmentsListResponse
import com.tracker.shipmenttracking.data.models.Status
import com.tracker.shipmenttracking.data.local.ShipmentCacheImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ShipmentCacheTest {
    private lateinit var context: Context
    private lateinit var cache: ShipmentCacheImpl

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        cache = ShipmentCacheImpl(context)
    }

    @Test
    fun testCacheAndRetrieveShipments() = runBlocking {
        // Arrange
        val shipments = ShipmentsListResponse(
            shipments = listOf(
                ShipmentItem(
                    id = "shp_1001",
                    carrier = Carrier(code = "ups", name = "UPS"),
                    trackingNumber = "1Z999AA10123456784",
                    lastStatus = Status(code = "IN_TRANSIT", label = "In transit"),
                    lastUpdatedAt = "2026-02-26T14:20:00Z",
                    origin = Location(city = "Seattle", country = "US"),
                    destination = Location(city = "Austin", country = "US"),
                    estimatedDeliveryAt = "2026-03-02T23:00:00Z"
                )
            )
        )

        // Act
        cache.cacheShipments(shipments)
        val cachedShipments = cache.getCachedShipments()

        // Assert
        assertEquals(shipments.shipments.size, cachedShipments?.shipments?.size)
        assertEquals(shipments.shipments[0].id, cachedShipments?.shipments?.get(0)?.id)
        assertEquals(shipments.shipments[0].trackingNumber, cachedShipments?.shipments?.get(0)?.trackingNumber)
    }

    @Test
    fun testClearCache() = runBlocking {
        // Arrange
        val shipments = ShipmentsListResponse(
            shipments = listOf(
                ShipmentItem(
                    id = "shp_1001",
                    carrier = Carrier(code = "ups", name = "UPS"),
                    trackingNumber = "1Z999AA10123456784",
                    lastStatus = Status(code = "IN_TRANSIT", label = "In transit"),
                    lastUpdatedAt = "2026-02-26T14:20:00Z",
                    origin = Location(city = "Seattle", country = "US"),
                    destination = Location(city = "Austin", country = "US"),
                    estimatedDeliveryAt = "2026-03-02T23:00:00Z"
                )
            )
        )

        // Act
        cache.cacheShipments(shipments)
        cache.clearCache()
        val cachedShipments = cache.getCachedShipments()

        // Assert
        assertNull(cachedShipments)
    }
}
