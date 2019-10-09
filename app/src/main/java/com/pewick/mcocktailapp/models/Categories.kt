package com.pewick.mcocktailapp.models

import android.util.Log
import com.pewick.mcocktailapp.datamodels.Category
import com.pewick.mcocktailapp.services.CocktailDB
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Categories(private val cocktailService: CocktailDB) {

    var categoriesList: Array<Category>? = null

    fun loadCategories(): Observable<Boolean> {
        return if (categoriesList != null){
            Log.i("Categories","category list not null")
            Observable.just(true)
        } else {
            Log.i("Categories","retrieving categories...")
            cocktailService.getCategories()
                .flatMap { response ->
                    Log.i("Categories", "Found List: $response")
                    categoriesList = response.drinks
                    Observable.just(true)
                }

                .onErrorReturn {
                    Log.e("Categories","Error message: ${it.message}")
                    false
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

}