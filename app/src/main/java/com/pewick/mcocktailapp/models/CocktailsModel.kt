package com.pewick.mcocktailapp.models

import com.pewick.mcocktailapp.datamodels.Category
import com.pewick.mcocktailapp.datamodels.Drink
import com.pewick.mcocktailapp.datamodels.DrinkDetails
import com.pewick.mcocktailapp.services.CocktailDB
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CocktailsModel(private val cocktailService: CocktailDB) {

    var categoriesList = ArrayList<Category>()
    var drinksList = ArrayList<Drink>()

    var drinkDetails: DrinkDetails? = null

    private var selectedCategory = ""
    private var selectedDrinkId = ""


    fun loadCategories(): Observable<Boolean> {
        return if (categoriesList.isNotEmpty()) {
            Observable.just(true)
        } else {
            cocktailService.getCategories()
                .flatMap { response ->
                    categoriesList.addAll(response.drinks)
                    Observable.just(true)
                }
                .onErrorReturn {
                    false
                }
        }
    }

    fun loadDrinks(categoryName: String? = null): Observable<Boolean> {
        val category = categoryName ?: selectedCategory

        return if (drinksList.isNotEmpty()) {
            Observable.just(true)
        } else {
            cocktailService.getDrinksByCategory(category)
                .flatMap { response ->
                    drinksList.addAll(response.drinks)
                    Observable.just(true)
                }
                .onErrorReturn {
                    false
                }
        }
    }

    fun loadDrinkDetails(drinkId: String? = null): Observable<DrinkDetails?> {
        val id = drinkId ?: selectedDrinkId

        return if (id == drinkDetails?.id) {
            Observable.just(drinkDetails)
        } else {
            cocktailService.getDrinkDetails(id)
                .flatMap { response ->
                    drinkDetails = response.drinks[0]
                    Observable.just(drinkDetails)
                }
                .onErrorReturn {
                    null
                }
        }
    }

    fun updateCategorySelection(category: String) {
        if(category != selectedCategory) {
            selectedCategory = category
            drinksList.clear()
        }
        // no op
    }

    fun updateDrinkIdSelection(drinkId: String) {
        if(drinkId != drinkDetails?.id) {
            selectedDrinkId = drinkId
            drinkDetails = null
        }
        // no op
    }
}