package com.vladan.pokemonappandroid.data.remote.responses.pokemonDetails

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)