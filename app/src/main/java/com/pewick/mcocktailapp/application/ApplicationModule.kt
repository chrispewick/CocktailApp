package com.pewick.mcocktailapp.application

import com.pewick.mcocktailapp.main.MainActivityComponent
import com.pewick.mcocktailapp.models.CocktailsModel
import com.pewick.mcocktailapp.services.CocktailDB
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(subcomponents = [MainActivityComponent::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCocktailDBService(retrofit: Retrofit): CocktailDB {
        return retrofit.create<CocktailDB>(CocktailDB::class.java)
    }

    @Singleton
    @Provides
    fun provideCocktailsModel(cocktailService: CocktailDB): CocktailsModel {
        return CocktailsModel(cocktailService)
    }
}