package com.pewick.mcocktailapp.main

import com.pewick.mcocktailapp.views.CategoriesView
import com.pewick.mcocktailapp.views.DrinkDetailsView
import com.pewick.mcocktailapp.views.DrinksView
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun mainActivity(activity: MainActivity): Builder
        fun mainActivityModule(module: MainActivityModule): Builder
        fun build(): MainActivityComponent
    }

    fun inject(view: CategoriesView)
    fun inject(view: DrinksView)
    fun inject(view: DrinkDetailsView)

}