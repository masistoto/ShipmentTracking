🚀 Getting Started
Prerequisites
Android Studio Hedgehog or later
Android SDK 24+ (API level for app compatibility)
Kotlin 1.9+
Gradle 9.0+
Installation
Clone the repository

bash
[git clone https://github.com/masistoto/ERYFlowApp.git](https://github.com/masistoto/ShipmentTracking.git)
cd ShipmentTracking
Checkout main branch

Build the project

bash
./gradlew build
Run on emulator or device

bash
./gradlew installDebug
Configuration
Update the API endpoint in data/remote/ShipmentApi.kt:

Kotlin
private val httpClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
    }
}

private const val BASE_URL = "https://your-api-endpoint.com/api"

Usage
Accessing Shipments List
Kotlin

ShipmentsListScreen(
    onNavigateToDetail = { shipmentId ->
        navController.navigate("detail/$shipmentId")
    }
)

Accessing Shipment Details

Kotlin

ShipmentDetailScreen(
    shipmentId = "shp_1001",
    onNavigateBack = { navController.popBackStack() }
)

Manual Refresh

The shipments list includes a refresh button in the top app bar. Tap to manually sync with the server.

Offline Mode
When offline:

Cached shipments display automatically
"Offline - Cached Data" banner appears
Refresh action is disabled
All search/filter functions work on cached data
🧪 Testing
Run All Tests
bash
./gradlew test
Run Specific Test
bash
./gradlew testDebugUnitTest --tests com.tracker.shipmenttracking.ShipmentCacheTest
Test Coverage
ShipmentCacheTest.kt
Tests the DataStore caching mechanism:

✅ Cache write operation
✅ Cache read operation
✅ Cache clear operation
✅ Serialization/deserialization integrity
Running:

bash
./gradlew testDebugUnitTest --tests "*.ShipmentCacheTest"
ShipmentTimelineTest.kt
Tests shipment timeline ordering and parsing:

✅ Timeline events in chronological order (newest first)
✅ Timestamp parsing correctness
✅ Status code mapping
✅ Location data extraction
Running:

bash
./gradlew testDebugUnitTest --tests "*.ShipmentTimelineTest"
