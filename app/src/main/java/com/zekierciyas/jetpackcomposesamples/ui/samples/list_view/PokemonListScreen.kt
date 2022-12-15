package com.zekierciyas.jetpackcomposesamples.ui.samples.list_view

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview("PokemonListScreen")
@Composable
fun PokemonListScreen() {
    val pokemonList = remember { PokemonListProvider.pokemonList }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            count = pokemonList.size,
            itemContent = {
                PokemonListItem(pokemon = pokemonList[it])
            })
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PokemonListItem(pokemon: Pokemon) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row {
            PokemonImage(pokemon)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = pokemon.name, style = typography.h6, color = Black)
                Column(
                    modifier = Modifier
                        .padding(end = 18.dp)
                ) {
                    Text(text = "#${pokemon.type}", style = typography.subtitle1, color = Black)
                }
                Text(text = pokemon.description, style = typography.caption, color = Black)
            }
        }
    }
}

@Composable
private fun PokemonImage(puppy: Pokemon) {
    Image(
        painter = painterResource(id = puppy.imageResourceId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
