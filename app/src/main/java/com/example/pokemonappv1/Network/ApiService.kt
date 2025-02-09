package com.example.pokemonappv1.Network

import com.example.pokemonappv1.Data.PokemonDetails
import com.example.pokemonappv1.Data.PokemonListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon")  // Query parameter
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{name}")  // Path parameter
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetails

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}