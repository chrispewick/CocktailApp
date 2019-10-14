package com.pewick.mcocktailapp.navigation.keys

import com.pewick.mcocktailapp.R
import kotlinx.android.parcel.Parcelize

/*Note: A bug in android studio can make it look like there is an error here. There is not.*/
@Parcelize
data class CategoriesViewKey(override var layoutId: Int = R.layout.view_categories): ViewKey(layoutId)