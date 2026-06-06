package com.vidyavahini.app

import android.content.Context
import com.vidyavahini.app.data.AiRepository
import com.vidyavahini.app.data.AuthRepository
import com.vidyavahini.app.data.FirebaseTransportRepository
import com.vidyavahini.app.data.LocalCache
import com.vidyavahini.app.data.NotificationRepository
import com.vidyavahini.app.data.RouteRepository

class AppContainer(context: Context) {
    val cache = LocalCache(context.applicationContext)
    val authRepository = AuthRepository(cache)
    val routeRepository = RouteRepository(cache)
    val aiRepository = AiRepository()
    val firebaseTransportRepository = FirebaseTransportRepository()
    val notificationRepository = NotificationRepository()
}

val Context.appContainer: AppContainer
    get() {
        val app = applicationContext as VidyaVahiniApp
        val field = VidyaVahiniApp::class.java.getDeclaredField("container").apply { isAccessible = true }
        return field.get(app) as? AppContainer ?: AppContainer(app).also { field.set(app, it) }
    }
