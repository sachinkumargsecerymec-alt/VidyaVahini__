package com.vidyavahini.app.data

import com.vidyavahini.app.model.AppNotification

class NotificationRepository {
    fun notifications(): List<AppNotification> {
        val now = System.currentTimeMillis()
        return listOf(
            AppNotification("n1", "Bus arriving", "Route r1 bus is expected in 8 minutes.", now - 3 * 60_000),
            AppNotification("n2", "Delay alert", "Route r2 is delayed near PHC.", now - 16 * 60_000),
            AppNotification("n3", "Safe reach", "Your safe reach update was recorded.", now - 40 * 60_000)
        )
    }
}
