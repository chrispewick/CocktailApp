package com.pewick.mcocktailapp.services

import com.pewick.mcocktailapp.datamodels.CategoriesResponse
import com.pewick.mcocktailapp.datamodels.DrinkDetailsResponse
import com.pewick.mcocktailapp.datamodels.DrinksResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailDB {

    @GET("list.php?c=list")
    fun getCategories(): Observable<CategoriesResponse>

    @GET("filter.php")
    fun getDrinksByCategory(@Query("c") category: String): Observable<DrinksResponse>

    @GET("lookup.php")
    fun getDrinkDetails(@Query("i") drinkId: String): Observable<DrinkDetailsResponse>
}