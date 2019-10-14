package com.pewick.mcocktailapp.navigation.keys

import android.os.Parcelable
import com.pewick.mcocktailapp.navigation.StateChange
import com.pewick.mcocktailapp.navigation.ViewChanger

/**
 * View keys contain their layout IDs and pass the to the viewChanger where it will inflate the view.
 * These keys are used to maintain our backstack
 */
abstract class ViewKey(open var layoutId : Int) : Parcelable {

    open fun navigateTo(viewChanger: ViewChanger, stateChange: StateChange) {
        viewChanger.changeView(layoutId, stateChange.direction)
    }
}
