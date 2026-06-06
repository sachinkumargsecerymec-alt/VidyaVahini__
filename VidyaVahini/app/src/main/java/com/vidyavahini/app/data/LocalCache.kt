package com.vidyavahini.app.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vidyavahini.app.model.CrowdPing
import com.vidyavahini.app.model.Student

class LocalCache(context: Context) {
    private val prefs = context.getSharedPreferences("vidya_vahini_cache", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveStudent(student: Student) = prefs.edit().putString("student", gson.toJson(student)).apply()

    fun getStudent(): Student? = prefs.getString("student", null)?.let { gson.fromJson(it, Student::class.java) }

    fun clearStudent() = prefs.edit().remove("student").apply()

    fun saveSelectedRoute(routeId: String) = prefs.edit().putString("selected_route", routeId).apply()

    fun selectedRouteId(): String = prefs.getString("selected_route", "r1") ?: "r1"

    fun savePings(pings: List<CrowdPing>) = prefs.edit().putString("pings", gson.toJson(pings)).apply()

    fun getPings(): List<CrowdPing> {
        val json = prefs.getString("pings", "[]") ?: "[]"
        val type = object : TypeToken<List<CrowdPing>>() {}.type
        return gson.fromJson(json, type)
    }
}
