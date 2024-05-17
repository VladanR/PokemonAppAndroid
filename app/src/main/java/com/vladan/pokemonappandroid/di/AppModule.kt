package com.vladan.pokemonappandroid.di

import com.vladan.pokemonappandroid.data.remote.PokeApi
import com.vladan.pokemonappandroid.data.remote.repository.PokemonRepository
import com.vladan.pokemonappandroid.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    fun providePokemonRepository(api: PokeApi) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi() : PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.baseURL)
            .build()
            .create(PokeApi::class.java)
    }
}