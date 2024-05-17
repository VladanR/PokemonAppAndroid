package com.vladan.pokemonappandroid.data.remote.responses.pokemonDetails

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)