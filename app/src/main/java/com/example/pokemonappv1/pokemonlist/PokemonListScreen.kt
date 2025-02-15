package com.example.pokemonappv1.pokemonlist



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.pokemonappv1.Data.models.PokedexListEntry
import com.example.pokemonappv1.R
import com.example.pokemonappv1.ui.theme.RobotoCondensed
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.toArgb


@Composable
fun PokemonListScreen(
    navController: NavController,
    //viewModel: PokemonListViewModel = hiltNavGraphViewModel(),
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonClick: (String, Int) -> Unit // added by AI
){
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()

    ){
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                // TODO function that gets triggered when we search something
                viewModel.searchPokemonList(it)
                //navController.navigate("pokemon_list_screen")
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController, onPokemonClick = onPokemonClick)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
){
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint!="")
    }
    Box(modifier = modifier){
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged(){
                    isHintDisplayed = it.isFocused != true && text.isNotEmpty()
                    //isHintDisplayed = it != FocusState.Active
                }
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp))
        }

    }
}

//@Composable
//fun PokedexEntry(
//    entry: PokedexListEntry,
//    navController: NavController,
//    modifier: Modifier = Modifier,
//    viewModel: PokemonListViewModel = hiltViewModel()
//
//){
//    val defaultDominantColor = MaterialTheme.colorScheme.surface
//    var dominantColor by remember {
//        mutableStateOf(defaultDominantColor)
//    }
//
//    Box(
//        contentAlignment = CenterHorizontally,
//        modifier = Modifier
//            .shadow(5.dp, RoundedCornerShape(10.dp))
//            .clip(RoundedCornerShape(10.dp))
//            .aspectRatio(1f)
//            .background(
//                Brush.verticalGradient(
//                    listOf(
//                        dominantColor,
//                        defaultDominantColor
//                    )
//                )
//            )
//            .clickable {
//                navController.navigate(
//                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
//                )
//            }
//    ) {
//        // inside Box
//        Column {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(entry.imageUrl)
//                    .target{
//                        viewModel.calcDominantColor (it){ color ->
//                            dominantColor = color
//                        }
//                    }
//                    .build(),
//                contentDescription = entry.pokemonName,
//                fadeIn = true,
//                modifier = Modifier
//                    .size(120.dp)
//                    .align(Alignment.CenterHorizontally)
//            ) {
//                CircularProgressIndicator(
//                    color = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.scale(0.5f)
//                )
//            }
//            Text(
//                text = entry.pokemonName,
//                fontFamily = RobotoCondensed,
//                fontSize = 20.sp,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonClick: (String, Int) -> Unit
){
    val pokemonList by remember {viewModel.pokemonList}
    val endReached by remember {viewModel.endReached}
    val loadError by remember {viewModel.loadError}
    val isLoading by remember {viewModel.isLoading}
    val isSearching by remember{viewModel.isSearching}


    LazyColumn (contentPadding = PaddingValues(16.dp)){
        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        }else {
            pokemonList.size / 2 + 1
        }
        items(itemCount){
            if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                // load more items
                LaunchedEffect(key1 = true) {
                    viewModel.loadPokemonPaginated()
                }
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController = navController, onPokemonClick = onPokemonClick)
        }
    }
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ){
        if (isLoading){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (loadError.isNotEmpty()){
            RetrySection(error = loadError) {  //display RetrySection with loadError
                viewModel.loadPokemonPaginated()
            }
        }
    }

}

@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonClick: (String, Int) -> Unit
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember { mutableStateOf(defaultDominantColor) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(dominantColor, defaultDominantColor)
                )
            )
            .clickable {
                onPokemonClick(entry.pokemonName, dominantColor.toArgb())
//                navController.navigate(
//                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
//                )
            }
    ) {  // Content of the Box
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .build(),
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                onSuccess = { state ->
                    // Access the drawable and calculate the dominant color
                    val drawable = state.result.drawable
                    if (drawable != null) {
                        viewModel.calcDominantColor(drawable) { color ->
                            dominantColor = color
                        }
                    }
                },
                onError = { state ->
                    // Optional: Handle error (e.g., show a placeholder or log the error)
                }
            )

            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.scale(0.5f)
            )

            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PokedexRow(
    rowIndex: Int,
    entries: List<PokedexListEntry>,
    navController: NavController,
    onPokemonClick: (String, Int) -> Unit
) {
    Column(){
        Row(){
            PokedexEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f),
                onPokemonClick = onPokemonClick
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                PokedexEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f),
                    onPokemonClick = onPokemonClick
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier =Modifier.height(16.dp))
    }
}


@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column() {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}