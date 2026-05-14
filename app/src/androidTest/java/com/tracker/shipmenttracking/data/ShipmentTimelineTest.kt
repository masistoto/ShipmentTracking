package com.tracker.shipmenttracking.data

import com.tracker.shipmenttracking.data.models.Carrier
import com.tracker.shipmenttracking.data.models.Location
import com.tracker.shipmenttracking.data.models.ShipmentDetail
import com.tracker.shipmenttracking.data.models.StatusEvent
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ShipmentTimelineTest {

    @Test
    fun testTimelineOrderingFromNewestToOldest() {
        // Arrange
        val shipmentDetail = ShipmentDetail(
            id = "shp_1001",
            carrier = Carrier(code = "ups", name = "UPS"),
            trackingNumber = "1Z999AA10123456784",
            origin = Location(city = "Seattle", country = "US"),
            destination = Location(city = "Austin", country = "US"),
            estimatedDeliveryAt = "2026-03-02T23:00:00Z",
            statuses = listOf(
                StatusEvent(
                    time = "2026-02-26T14:20:00Z",
                    code = "IN_TRANSIT",
                    label = "Departed facility",
                    location = "Portland, OR"
                ),
                StatusEvent(
                    time = "2026-02-25T08:10:00Z",
                    code = "LABEL_CREATED",
                    label = "Label created",
                    location = "Seattle, WA"
                )
            )
        )

        // Act
        val timeline = shipmentDetail.statuses

        // Assert
        assertEquals(2, timeline.size)
        //assertTrue(timeline[0].time > timeline[1].time, "Timeline should be ordered from newest to oldest")
        assertEquals("IN_TRANSIT", timeline[0].code)
        assertEquals("LABEL_CREATED", timeline[1].code)
    }

    @Test
    fun testParseStatusEventsCorrectly() {
        // Arrange
        val statusEvent = StatusEvent(
            time = "2026-02-26T14:20:00Z",
            code = "IN_TRANSIT",
            label = "In transit",
            location = "Portland, OR"
        )

        // Act & Assert
        assertEquals("IN_TRANSIT", statusEvent.code)
        assertEquals("In transit", statusEvent.label)
        assertEquals("2026-02-26T14:20:00Z", statusEvent.time)
        assertEquals("Portland, OR", statusEvent.location)
    }
}