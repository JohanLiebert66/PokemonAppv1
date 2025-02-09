package com.example.pokemonappv1.repository

import com.example.pokemonappv1.Data.remote.resources.PokeApi
import com.example.pokemonappv1.Data.remote.resources.Pokemon
import com.example.pokemonappv1.Data.remote.resources.PokemonList
import com.example.pokemonappv1.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped  // will live as long as our Activity does -- our app
class PokemonRepository @Inject constructor(
    // inject dependencies into the constructor of this repository
    private val api: PokeApi) {  // access to API instance of PokeApi ( a Retrofit instance)
        suspend fun getPokemonList(limit: Int, offset: Int) : Resource<PokemonList> {
            val response = try {
                api.getPokemonList(limit, offset)
            } catch (e: Exception) {
                return Resource.Error("An unknown error occurred.")
            }
            return Resource.Success(response)
        }
        //suspend fun getPokemonInfo(pokemonName: String) = api.getPokemonInfo(pokemonName)
        suspend fun getPokemonInfo(pokemonName: String) : Resource<Pokemon> {
            val response = try {
                api.getPokemonInfo(pokemonName)
            } catch (e: Exception) {
                return Resource.Error("An unknown error occurred.")
            }
            return Resource.Success(response)
        }
}