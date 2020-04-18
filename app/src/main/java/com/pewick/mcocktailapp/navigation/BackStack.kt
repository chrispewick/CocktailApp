package com.pewick.mcocktailapp.navigation

import com.pewick.mcocktailapp.navigation.keys.ViewKey
import java.util.Collections.unmodifiableList
import kotlin.collections.ArrayList


class BackStack (private val stateChanger: NavigationStageChanger) {
    private val stack = ArrayList<ViewKey>()
    private var isStateChangeInProgress = false

    fun getHistory(): ArrayList<ViewKey> {
        return stack
    }

    fun goTo(newKey: ViewKey) {
        val newHistory = ArrayList<ViewKey>()
        var isNewKey = true
        for (key in stack) { // check if already in stack
            newHistory.add(key)
            if (key == newKey) { // if found, forget the later elements
                isNewKey = false
                break
            }
        }
        val direction: StateChange.Direction
        direction = if (isNewKey) {
            newHistory.add(newKey) // if not found, then this is a forward navigation
            StateChange.Direction.FORWARD
        } else {
            StateChange.Direction.BACKWARD // if found, this is a backward navigation
        }
        changeState(newHistory, direction)
    }

    fun goBack(): Boolean{
        if (isStateChangeInProgress) {
            return true
        }

        if (stack.size <= 1) {
            stack.clear()
            return false // if backstack can't go back, return false
        }
        val newHistory = ArrayList<ViewKey>()
        for (i in 0 until stack.size - 1) { // set up new history, without the last element
            newHistory.add(stack[i])
        }
        changeState(newHistory, StateChange.Direction.BACKWARD) // backwards navigation
        return true // backstack handled this
    }

    fun setHistory(newHistory: List<ViewKey>, direction: StateChange.Direction) {
        // check state and arguments
        changeState(newHistory, direction)
    }

    private fun changeState(newHistory: List<ViewKey>, direction: StateChange.Direction) {
        isStateChangeInProgress = true
        val previousState = ArrayList<ViewKey>(stack) // we need a copy of the original state

        val stateChange = StateChange( // create state change
            unmodifiableList(previousState),
            unmodifiableList(newHistory),
            direction
        )

        stateChanger.handleStateChange(stateChange, this::completeStateChange)
    }

    private fun completeStateChange(stateChange: StateChange) {
        this.stack.clear()
        this.stack.addAll(stateChange.newState) // clear state and add new state instead
        isStateChangeInProgress = false
    }
}