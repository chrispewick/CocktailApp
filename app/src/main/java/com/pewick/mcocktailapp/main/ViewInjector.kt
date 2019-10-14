package com.pewick.mcocktailapp.main

import android.view.View
import com.pewick.mcocktailapp.views.CategoriesView
import com.pewick.mcocktailapp.views.DrinkDetailsView
import com.pewick.mcocktailapp.views.DrinksView
import java.lang.IllegalArgumentException

class ViewInjector(private val component: MainActivityComponent) {

    fun inject(view: View) {
        when (view) {
            is CategoriesView -> component.inject(view)
            is DrinksView -> component.inject(view)
            is DrinkDetailsView -> component.inject(view)
            else -> throw IllegalArgumentException("No injector found for ${view.javaClass.name}")
        }
    }
}