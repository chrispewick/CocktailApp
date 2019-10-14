package com.pewick.mcocktailapp.navigation

interface ViewChanger {

    fun changeView(layoutId: Int, direction: StateChange.Direction)
}