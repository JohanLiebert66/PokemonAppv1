package com.example.pokemonappv1

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemonappv1.ui.theme.PokemonAppV1Theme
import com.example.pokemonappv1.navigation.PokemonAppNavigation
import com.example.pokemonappv1.pokemonlist.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppV1Theme {
                val viewModel = hiltViewModel<PokemonListViewModel>()
                PokemonAppNavigation(viewModel = viewModel)
            }
        }
//        PokemonDetailScreen(
//            dominantColor = dominantColor,
//            pokemonName = pokemonName.toLowerCase(),
//            navController = navController
//        )
    }
}