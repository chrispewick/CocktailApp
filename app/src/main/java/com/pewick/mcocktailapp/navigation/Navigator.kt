package com.pewick.mcocktailapp.navigation

import com.pewick.mcocktailapp.navigation.keys.ViewKey

/**
 * Provides navigation functionality, without exposing the back stack
 */
class Navigator(private val backStack: BackStack) {

    fun navigateTo(key: ViewKey){
        backStack.goTo(key)
    }

    fun goBack() {
        backStack.goBack()
    }
}