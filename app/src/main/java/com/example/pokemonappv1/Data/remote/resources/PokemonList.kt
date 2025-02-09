package com.example.pokemonappv1.Data.remote.resources

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)