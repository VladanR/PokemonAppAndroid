package com.vladan.pokemonappandroid.data.remote.responses.pokemonList

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)