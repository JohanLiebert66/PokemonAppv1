package com.example.pokemonappv1.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonappv1.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.palette.graphics.Palette
import com.example.pokemonappv1.Data.models.PokedexListEntry
import com.example.pokemonappv1.util.Constants.PAGE_SIZE
import com.example.pokemonappv1.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.util.Locale

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {
    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    private var cachedPokemonList = listOf<PokedexListEntry>()  // Backup the original list
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String){
        val listToSearch = if (isSearchStarting){  // if we are starting the search... text field is empty
            pokemonList.value  // our searched list is the original list and our cached list is empty
        }else {  // if we typed atleast 1 character
            cachedPokemonList // return cached list, which is the original list
        }
        viewModelScope.launch(Dispatchers.Default){ //
            if(query.isEmpty()) {
                pokemonList.value = cachedPokemonList // if query is empty, return original list
                isSearching.value = false
                isSearchStarting = true
                return@launch // ??
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(
                    query.trim(), // remove leading and trailing spaces
                    ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if(isSearchStarting) {  // first time we search
                cachedPokemonList = pokemonList.value // cache original list
                isSearchStarting = false // we are no longer starting the search
            }
            pokemonList.value = results // update the list with the filtered results (displayed in lazy column)
            isSearching.value = true

        }
    }

    fun loadPokemonPaginated(){
        viewModelScope.launch {
            val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEnries = result.data.results.mapIndexed { index,entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        }else {
                                entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        //PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                        PokedexListEntry(entry.name.replaceFirstChar { it.uppercase(Locale.ROOT) }, url, number.toInt())


                    }

                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEnries
                }
                is Resource.Error -> {
                    loadError.value = result.message ?: "An unknown error occurred."
                    isLoading.value = false
                }

                is Resource.Loading<*> -> TODO()
            }
        }
    }
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit){
        // transfer Drawable into Bitmap
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate{ palette ->
            palette?.dominantSwatch?.rgb?.let{ colorValue ->
                onFinish(Color(colorValue))
            }
        }

    }
}