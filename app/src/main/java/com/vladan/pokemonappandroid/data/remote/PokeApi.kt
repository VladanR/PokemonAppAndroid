package com.vladan.pokemonappandroid.data.remote

import com.vladan.pokemonappandroid.data.remote.responses.pokemonDetails.Pokemon
import com.vladan.pokemonappandroid.data.remote.responses.pokemonList.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int,
                               @Query("offset") offset: Int): PokemonList
    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name")name: String):Pokemon
}