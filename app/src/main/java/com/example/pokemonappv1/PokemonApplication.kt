package com.example.pokemonappv1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PokemonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any necessary libraries or components here
        Timber.plant(Timber.DebugTree())
    }
}