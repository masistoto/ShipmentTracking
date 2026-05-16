- Architecture overview (MVVM + Clean Architecture)

- Scaling strategies for multiple carriers, push updates, larger datasets

- Design decisions with rationale

- Data model examples (JSON schemas)

- Performance optimizations

- Security considerations
- Future enhancement roadmap

Tests:

- ShipmentCacheTest - Validates cache persistence & serialization
- ShipmentTimelineTest - Ensures timeline ordering and data integrity

Key Tech Stack:
Component	Technology

Architecture	MVVM + Clean Architecture

UI Framework	Jetpack Compose + Material Design 3

DI	Hilt

Networking	Ktor Client with JSON serialization

Caching	DataStore (modern, Flow-based)

Navigation	Compose Navigation

Serialization	Kotlinx.serialization

All files have been pushed to the feature/shipment-tracking branch. The solution is production-ready with clear separation of concerns, testable code, and a scalable architecture.
