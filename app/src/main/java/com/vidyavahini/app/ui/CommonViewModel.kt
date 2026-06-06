package com.vidyavahini.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vidyavahini.app.AppContainer

class VidyaViewModel(val container: AppContainer) : ViewModel()

class VidyaViewModelFactory(private val container: AppContainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return VidyaViewModel(container) as T
    }
}
