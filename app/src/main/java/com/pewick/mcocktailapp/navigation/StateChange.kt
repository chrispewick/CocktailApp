package com.pewick.mcocktailapp.navigation

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.pewick.mcocktailapp.navigation.keys.ViewKey

/**
 * Class representing a navigation state change.
 *
 * Credit: https://medium.com/@Zhuinden/towards-a-fragmentless-world-creating-a-flow-like-custom-backstack-part-1-cf551ebda624
 */
class StateChange(
    previousState: List<ViewKey>,
    newState: List<ViewKey>,
    var direction: Direction
) {

    var previousState: List<ViewKey>
        internal set
    var newState: List<ViewKey>
        internal set

    enum class Direction {
        FORWARD,
        BACKWARD,
        REPLACE
    }

    init {
        this.previousState = previousState
        this.newState = newState
    }

    @Nullable
    fun topPreviousState(): ViewKey? {
        return if (previousState.size > 0) {

            previousState[previousState.size - 1]
        } else {
            null
        }
    }

    @NonNull
    fun topNewState(): ViewKey {

        return newState[newState.size - 1]
    }
}