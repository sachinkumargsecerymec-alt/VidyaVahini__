package com.vidyavahini.app.data

import com.google.firebase.firestore.FirebaseFirestore
import com.vidyavahini.app.model.CrowdPing
import com.vidyavahini.app.model.EmergencyReport
import kotlinx.coroutines.tasks.await

class FirebaseTransportRepository {
    private val firestore by lazy { runCatching { FirebaseFirestore.getInstance() }.getOrNull() }

    suspend fun savePing(ping: CrowdPing) {
        runCatching { firestore?.collection("crowdPings")?.document(ping.id)?.set(ping)?.await() }
    }

    suspend fun saveEmergency(report: EmergencyReport) {
        runCatching { firestore?.collection("emergencyReports")?.document(report.id)?.set(report)?.await() }
    }
}
