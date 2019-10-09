package com.pewick.mcocktailapp.services

import com.pewick.mcocktailapp.datamodels.CategoriesResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CocktailDB {

//    @GET("list.php?c=list")
//    fun getCategories(): Observable<Array<Category>>

    @GET("list.php?c=list")
    fun getCategories(): Observable<CategoriesResponse>
}