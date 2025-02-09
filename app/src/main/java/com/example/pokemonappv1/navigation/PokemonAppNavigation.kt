package com.example.pokemonappv1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.util.Locale
import com.example.pokemonappv1.pokemondetail.PokemonDetailScreen
import com.example.pokemonappv1.pokemonlist.PokemonListScreen
import com.example.pokemonappv1.pokemonlist.PokemonListViewModel

//import com.example.pokemonappv1.screens.PokemonListScreen
//import com.example.pokemonappv1.viewmodel.PokemonViewModel

@Composable
fun PokemonAppNavigation(viewModel: PokemonListViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pokemon_list") {
        // Pokémon List Screen
        composable("pokemon_list_screen") {
            PokemonListScreen(navController = navController, viewModel) { name, dominantColor ->
                viewModel.fetchPokemonDetails(name)
                navController.navigate("pokemon_detail_screen/$dominantColor/$name")
            }
        }

        // Pokémon Detail Screen
        composable(
            "pokemon_detail_screen/{dominantColor}/{pokemonName}",
            arguments = listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            // Extract dominantColor and pokemonName from navigation arguments
            val dominantColor = navBackStackEntry.arguments?.getInt("dominantColor") ?: 0
            val pokemonName = navBackStackEntry.arguments?.getString("pokemonName") ?: ""

//            // Pass dominantColor and pokemonName to PokemonDetailScreen
//            PokemonDetailScreen(
//                viewModel = viewModel,
//                dominantColor = Color(dominantColor),
//                pokemonName = pokemonName,
//                onBackClick = {
//                    navController.popBackStack() // Navigate back to the list
//                }
//            )

            PokemonDetailScreen(
                dominantColor = dominantColor,
                pokemonName = pokemonName.toLowerCase(Locale.ROOT) ?. "",
                navController = navController
            )
        }
    }
}

