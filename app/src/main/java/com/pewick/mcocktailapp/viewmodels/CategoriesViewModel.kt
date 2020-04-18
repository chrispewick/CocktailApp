package com.pewick.mcocktailapp.viewmodels

import com.pewick.mcocktailapp.datamodels.Category
import com.pewick.mcocktailapp.models.CocktailsModel
import com.pewick.mcocktailapp.navigation.Navigator
import com.pewick.mcocktailapp.navigation.keys.DrinksViewKey
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val model: CocktailsModel,
    private val navigator: Navigator
): ViewModel() {

    val categoriesLoaded: Observable<Boolean> get() = categoriesLoadedPublisher
    private val categoriesLoadedPublisher = PublishSubject.create<Boolean>()

    fun loadCategories() {
        disposables.add(model.loadCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { false }
            .subscribe { success ->
                categoriesLoadedPublisher.onNext(success)
            })
    }

    fun getCategories() : ArrayList<Category> {
        return model.categoriesList
    }

    fun categorySelected(name: String) {
        model.updateCategorySelection(name)
        navigator.navigateTo(DrinksViewKey())
    }
}