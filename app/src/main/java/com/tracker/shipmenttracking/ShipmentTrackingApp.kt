package com.tracker.shipmenttracking

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShipmentTrackingApp : Application() {
    companion object {
        const val SP_TRACKER_APP_URI = "https://www.google.com/" // TODO: Get API BASE_URL from Offerzen forks
    }
}