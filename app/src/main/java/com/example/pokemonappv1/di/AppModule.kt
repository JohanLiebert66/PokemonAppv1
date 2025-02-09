package com.example.pokemonappv1.di

import com.example.pokemonappv1.Data.remote.resources.PokeApi
import com.example.pokemonappv1.repository.PokemonRepository
import com.example.pokemonappv1.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Used to define how we construct our dependencies so that dagger be able to inject them into our classes
@Module
@InstallIn(SingletonComponent::class) // will live as long as our app does
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Provides
    @Singleton
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}