package com.zekierciyas.jetpackcomposesamples.ui.samples.list_view

import com.zekierciyas.jetpackcomposesamples.R

object PokemonListProvider {

    val pokemonList = listOf(
        Pokemon(
            name = "Bulbasaur",
            description = "It uses the nutrients that are stored in the seed on its back in order to grow. The reception of Bulbasaur has been largely positive and it often appears in top Pokémon lists. Its English name is a portmanteau of bulb and dinosaur",
            type = "Grass",
            imageResourceId = R.drawable.image_bulbasaur
        ),

        Pokemon(
            name = "Venusaur",
            description = "Its English name is a portmanteau of Venus (relating to the Venus flytrap) and dinosaur. It is based on the Pareiasaur. It is the mascot of Pokémon Green and LeafGreen.[7] A bewitching aroma wafts from its flower. The fragrance becalms those engaged in a battle. Its plant blooms when it is absorbing solar energy.",
            type = "Fire",
            imageResourceId = R.drawable.image_venusaur
        ),

        Pokemon(
            name = "Charmander",
            description = "Charmander is a bipedal, salamander-like creature with a flame at the tip of its tail. Its English name is a portmanteau of \"char\" and \"salamander\". From the time it is born, a flame burns at the tip of its tail. Its life would end if the flame were to go out.",
            type = "Fire",
            imageResourceId = R.drawable.image_charmander
        ),

        Pokemon(
            name = "Charizard",
            description = "It is a playable character as a part of the \"Pokémon Trainer\" fighter in Super Smash Bros. Brawl and Super Smash Bros. Ultimate, as well as a standalone fighter in Super Smash Bros. for Nintendo 3DS and Wii U. It is also the mascot of Pokémon Red and FireRed. In its Mega Charizard X form, it gains the dragon typing. Its English name is a portmanteau of \"char\" and \"lizard\".",
            type = "Flying",
            imageResourceId = R.drawable.image_charizard
        ),

        Pokemon(
            name = "Squirtle",
            description = "It is a playable character in Super Smash Bros. Brawl and Super Smash Bros. Ultimate as a part of the \"Pokémon Trainer\" fighter. Its English name is a portmanteau of \"squirt\" and \"turtle\". It is one of the Kanto starter Pokémon.[9]",
            type = "Water",
            imageResourceId = R.drawable.image_squirtle
        ),

        Pokemon(
            name = "Pikachu",
            description = "Pikachu is the primary mascot of the Pokémon franchise, as well as Pokémon Yellow and Let's Go, Pikachu!. It is also playable in every Super Smash Bros. game. Its Gigantamax form looks like its old sprite from Red and Blue with a glowing, whitish tail. It raises its tail to check its surroundings and it sometimes gets struck by lightning in that pose. If Pikachu sees something new, it will shock it with electricity. When you will see blackened marks on the ground, you will find out that Pikachu had mistook its electrical power.",
            type = "Electric",
            imageResourceId = R.drawable.image_pikachu
        )
    )
}