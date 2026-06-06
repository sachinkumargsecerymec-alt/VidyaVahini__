package com.vidyavahini.app.data

import com.vidyavahini.app.model.BusLocation
import com.vidyavahini.app.model.BusRoute
import com.vidyavahini.app.model.CrowdPing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class RouteRepository(private val cache: LocalCache) {
    private val routes = listOf(
        BusRoute("r1", "Village A - Govt High School", "Village A", "Govt High School", listOf("Village A", "Canal Stop", "Market", "Govt High School"), 18.4),
        BusRoute("r2", "Kottai Hamlet - Polytechnic", "Kottai Hamlet", "Polytechnic College", listOf("Kottai Hamlet", "PHC", "Bus Stand", "Polytechnic"), 24.0),
        BusRoute("r3", "Lake Road - Arts College", "Lake Road", "Arts College", listOf("Lake Road", "Temple", "Milk Society", "Arts College"), 13.2),
        BusRoute("r4", "Hill Colony - ITI", "Hill Colony", "ITI Campus", listOf("Hill Colony", "Forest Gate", "Main Road", "ITI Campus"), 31.5)
    )

    private val _pings = MutableStateFlow(seedPings())
    val pings: StateFlow<List<CrowdPing>> = _pings

    fun allRoutes(): List<BusRoute> = routes

    fun selectedRoute(): BusRoute = routes.firstOrNull { it.id == cache.selectedRouteId() } ?: routes.first()

    fun selectRoute(routeId: String) = cache.saveSelectedRoute(routeId)

    fun latestBusLocation(routeId: String): BusLocation {
        val offset = routes.indexOfFirst { it.id == routeId }.coerceAtLeast(0)
        return BusLocation(routeId, 11.0168 + offset * 0.04, 76.9558 + offset * 0.05, 28.0 - offset, System.currentTimeMillis())
    }

    fun addPing(routeId: String, stopName: String, note: String, lat: Double, lng: Double): CrowdPing {
        val ping = CrowdPing(UUID.randomUUID().toString(), routeId, stopName, note, lat, lng, System.currentTimeMillis())
        val updated = listOf(ping) + _pings.value
        _pings.value = updated
        cache.savePings(updated)
        return ping
    }

    private fun seedPings(): List<CrowdPing> {
        val saved = cache.getPings()
        if (saved.isNotEmpty()) return saved
        val now = System.currentTimeMillis()
        return listOf(
            CrowdPing("p1", "r1", "Canal Stop", "Bus crossed with many seats available", 11.02, 76.96, now - 6 * 60_000),
            CrowdPing("p2", "r2", "PHC", "Driver reported 10 minute delay", 11.05, 77.00, now - 12 * 60_000),
            CrowdPing("p3", "r3", "Temple", "Bus arrived on time", 10.98, 76.93, now - 18 * 60_000)
        )
    }
}
