package com.pewick.mcocktailapp.viewmodels

import com.pewick.mcocktailapp.datamodels.Drink
import com.pewick.mcocktailapp.models.CocktailsModel
import com.pewick.mcocktailapp.navigation.Navigator
import com.pewick.mcocktailapp.navigation.keys.DrinkDetailsKey
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DrinksViewModel @Inject constructor(
    private val model: CocktailsModel,
    private val navigator: Navigator
): ViewModel() {

    val drinksLoaded: Observable<Boolean> get() = drinksLoadedPublisher
    private val drinksLoadedPublisher = PublishSubject.create<Boolean>()

    override fun onAttached() {
        super.onAttached()
    }

    override fun onDetached() {
        super.onDetached()
    }

    fun loadDrinks(category: String? = null) {
        disposables.add(model.loadDrinks(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { false }
            .subscribe { success ->
                drinksLoadedPublisher.onNext(success)
            })
    }

    fun getDrinks() : ArrayList<Drink> {
        return model.drinksList
    }

    fun drinkSelected(drinkId: String) {
        model.updateDrinkIdSelection(drinkId)
        navigator.navigateTo(DrinkDetailsKey())
    }
}