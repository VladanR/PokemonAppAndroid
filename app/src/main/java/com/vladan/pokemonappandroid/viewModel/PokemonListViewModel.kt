package com.vladan.pokemonappandroid.viewModel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.vladan.pokemonappandroid.data.model.PokemonListItem
import com.vladan.pokemonappandroid.data.remote.repository.PokemonRepository
import com.vladan.pokemonappandroid.utils.Constants.PAGE_SIZE
import com.vladan.pokemonappandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val  pokemonRepository: PokemonRepository
): ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokemonListItem>>(listOf())
    var loadingError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPaginatedPokemon()
    }
    fun loadPaginatedPokemon() {
        viewModelScope.launch {
            isLoading.value = true

            val result = pokemonRepository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE )

            when(result) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count
                    val pokemonItems = result.data.results.mapIndexed { _, item ->
                        val number = if( item.url.endsWith("/")) {
                            item.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            item.url.takeLastWhile { it.isDigit() }
                        }

                        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

                        PokemonListItem(item.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }, imageUrl, number.toInt())
                    }
                    loadingError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokemonItems
                    currentPage++
                }
                is Resource.Error -> {
                    loadingError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate{ palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}