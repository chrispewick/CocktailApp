package com.pewick.mcocktailapp.navigation

class NavigationStageChanger(private val viewChanger: ViewChanger) {

    fun handleStateChange(
        stateChange: StateChange,
        completionCallback: (stateChange: StateChange) -> Unit
    ) {
        val key = stateChange.topNewState()
        key.navigateTo(viewChanger, stateChange)
        completionCallback(stateChange)
    }
}