package com.example.pokemonappv1.Data.remote.resources

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    // To which route this request should actually go? && What kind of request that actually is? >>> GET request == get data
    @GET("pokemon")  // to which route this request would go
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int

    ): PokemonList  // returns pokemon list

    @GET("pokemon/{name}") // Path parameter >> the name is a changing variable
    suspend fun getPokemonInfo(
        @Path("name") name: String

    ): Pokemon // returns a pokemon
}