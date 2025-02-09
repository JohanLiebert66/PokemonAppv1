package com.example.pokemonappv1.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.pokemonappv1.Data.remote.resources.Pokemon
import com.example.pokemonappv1.repository.PokemonRepository
import com.example.pokemonappv1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {
    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

}