package com.vidyavahini.app

import android.app.Application
import com.google.firebase.FirebaseApp

class VidyaVahiniApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        runCatching { FirebaseApp.initializeApp(this) }
    }
}

