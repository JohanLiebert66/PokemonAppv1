package com.example.pokemonappv1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonappv1.Data.Pokemon
import com.example.pokemonappv1.Data.PokemonDetails
import com.example.pokemonappv1.Network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import javax.inject.Inject


class PokemonViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    private val _selectedPokemon = MutableStateFlow<PokemonDetails?>(null)
    val selectedPokemon: StateFlow<PokemonDetails?> = _selectedPokemon

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                val response = apiService.getPokemonList()
                _pokemonList.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchPokemonDetails(name: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPokemonDetails(name)
                _selectedPokemon.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



@Composable
fun PokemonItem(pokemon: Pokemon, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(pokemon.name) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


