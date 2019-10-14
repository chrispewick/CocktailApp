package com.pewick.mcocktailapp.navigation.keys

import com.pewick.mcocktailapp.R
import kotlinx.android.parcel.Parcelize

/* A bug in android studio can make it look like there is an error here. There is not. */
@Parcelize
data class DrinkDetailsKey(override var layoutId: Int = R.layout.view_drink_details): ViewKey(layoutId)