package com.pewick.mcocktailapp.viewmodels

import com.pewick.mcocktailapp.datamodels.DrinkDetails
import com.pewick.mcocktailapp.models.CocktailsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.reflect.KProperty1

class DrinkDetailsViewModel @Inject constructor(
    private val model: CocktailsModel
): ViewModel() {

    val detailsLoaded: Observable<Boolean> get() = detailsLoadedPublisher
    private val detailsLoadedPublisher = PublishSubject.create<Boolean>()

    var drinkDetails: DrinkDetails? = null

    override fun onAttached() {
        super.onAttached()
    }

    override fun onDetached() {
        super.onDetached()
    }

    fun loadDrinkDetails(drinkId: String? = null) {
        disposables.add(model.loadDrinkDetails(drinkId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { null }
            .subscribe {
                drinkDetails = it
                detailsLoadedPublisher.onNext(true)
                getFormattedIngredientsList()
            })
    }

    fun getImageUrl(): String? {
        return drinkDetails?.imageUrl
    }

    fun getDrinkName(): String? {
        return drinkDetails?.name
    }

    fun getCategory(): String? {
        return drinkDetails?.category
    }

    fun getAlcoholic(): String? {
        return drinkDetails?.alcoholic
    }

    fun getGlass(): String? {
        return drinkDetails?.glass
    }

    fun getInstructions(): String? {
        return drinkDetails?.instructions
    }

    //Format the ingredients and their measurement together
    fun getFormattedIngredientsList(): ArrayList<String>{
        if(drinkDetails == null) {
            return ArrayList()
        }
        val formattedList = ArrayList<String>()

        //This data structure from the API is messy. We need to display values from an
        // arbitrary number of attributes, up to a maximum of 15 each
        for( i in 1..15) {
            getFormattedIngredient(
                readInstanceProperty<String>(drinkDetails!!, "measure$i"),
                readInstanceProperty<String>(drinkDetails!!, "ingredient$i"))?.let {
                    formattedList.add(it)
                }
        }

        return formattedList
    }

    private fun getFormattedIngredient(measure: String?, ingredient: String?): String? {
        if(ingredient == null) {
            return null
        }

        if(measure == null) {
            return ingredient
        }

        return "${measure}${ingredient}"
    }

    /**
     * This function uses reflection to access attributes by name using a string value.
     */
    @Suppress("UNCHECKED_CAST")
    fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
        val property = instance::class.members.first { it.name == propertyName } as KProperty1<Any, *>
        // force a invalid cast exception if incorrect type here
        return property.get(instance) as R
    }
}