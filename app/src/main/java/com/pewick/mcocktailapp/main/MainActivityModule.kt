package com.pewick.mcocktailapp.main

import com.pewick.mcocktailapp.models.CocktailsModel
import com.pewick.mcocktailapp.navigation.Navigator
import com.pewick.mcocktailapp.viewmodels.CategoriesViewModel
import com.pewick.mcocktailapp.viewmodels.DrinksViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val navigator: Navigator) {

    @Provides
    fun provideNavigator(): Navigator{
        return navigator
    }

    @Provides
    fun provideCategoriesVM(cocktailsModel: CocktailsModel): CategoriesViewModel {
        return CategoriesViewModel(cocktailsModel, navigator)
    }

    @Provides
    fun provideDrinksVM(cocktailsModel: CocktailsModel): DrinksViewModel {
        return DrinksViewModel(cocktailsModel, navigator)
    }
}