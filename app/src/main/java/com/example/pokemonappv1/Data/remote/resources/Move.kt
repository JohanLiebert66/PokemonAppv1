package com.example.pokemonappv1.Data.remote.resources

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)