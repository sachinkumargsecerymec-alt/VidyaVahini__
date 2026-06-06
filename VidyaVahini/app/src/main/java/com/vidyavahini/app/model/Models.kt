package com.vidyavahini.app.model

data class Student(val id: String, val name: String, val email: String, val selectedRouteId: String? = null)

data class BusRoute(
    val id: String,
    val name: String,
    val start: String,
    val end: String,
    val stops: List<String>,
    val distanceKm: Double
)

data class BusLocation(val routeId: String, val latitude: Double, val longitude: Double, val speedKmph: Double, val updatedAt: Long)

data class CrowdPing(
    val id: String,
    val routeId: String,
    val stopName: String,
    val note: String,
    val latitude: Double,
    val longitude: Double,
    val createdAt: Long
)

data class EtaPrediction(val minutes: Int, val confidence: String, val reason: String)

data class AppNotification(val id: String, val title: String, val message: String, val createdAt: Long)

data class EmergencyReport(val id: String, val routeId: String, val type: String, val message: String, val createdAt: Long)

data class ChatMessage(val fromUser: Boolean, val text: String, val createdAt: Long = System.currentTimeMillis())
