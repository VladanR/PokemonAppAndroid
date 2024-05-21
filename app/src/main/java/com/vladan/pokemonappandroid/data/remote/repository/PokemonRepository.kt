package com.vladan.pokemonappandroid.data.remote.repository

import com.vladan.pokemonappandroid.data.remote.PokeApi
import com.vladan.pokemonappandroid.data.remote.responses.pokemonDetails.Pokemon
import com.vladan.pokemonappandroid.data.remote.responses.pokemonList.PokemonList
import com.vladan.pokemonappandroid.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
){
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit,offset)
        } catch (e: Exception) {
            return Resource.Error("An error occurred!")
        }
        return Resource.Success(response)
    }
    suspend fun getPokemonDetails(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonDetails(name)
        } catch (e: Exception) {
            return Resource.Error("An error occurred!")
        }
        return Resource.Success(response)
    }
}