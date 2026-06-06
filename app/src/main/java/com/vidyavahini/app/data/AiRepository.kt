package com.vidyavahini.app.data

import com.vidyavahini.app.model.BusLocation
import com.vidyavahini.app.model.BusRoute
import com.vidyavahini.app.model.CrowdPing
import com.vidyavahini.app.model.EtaPrediction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.math.max

class AiRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(AiApi::class.java)

    suspend fun predictEta(route: BusRoute, location: BusLocation, pings: List<CrowdPing>): EtaPrediction = withContext(Dispatchers.IO) {
        val recentDelay = pings.count { it.routeId == route.id && it.note.contains("delay", ignoreCase = true) } * 4
        val base = max(5, (route.distanceKm / max(location.speedKmph, 12.0) * 60).toInt())
        EtaPrediction(base + recentDelay, if (pings.isNotEmpty()) "High" else "Medium", "Based on live speed, route distance and ${pings.size} crowd reports.")
    }

    suspend fun askAssistant(question: String, route: BusRoute, pings: List<CrowdPing>): String = withContext(Dispatchers.IO) {
        val lower = question.lowercase()
        when {
            "delay" in lower -> "For ${route.name}, recent crowd reports show ${pings.count { it.routeId == route.id }} updates. Start 10 minutes early if you are near ${route.stops.first()}."
            "stop" in lower || "route" in lower -> "${route.name} stops: ${route.stops.joinToString(" -> ")}."
            "safe" in lower -> "Use Safe Reach after arriving. In an emergency, open Emergency and tap SOS."
            else -> "I can help with route stops, ETA, delays, pings and safety reports for ${route.name}."
        }
    }

    interface AiApi {
        @POST("v1beta/models/gemini-pro:generateContent")
        suspend fun generate(@Body body: Map<String, Any>): Map<String, Any>
    }
}
