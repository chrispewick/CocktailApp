package com.pewick.mcocktailapp.datamodels

import com.squareup.moshi.Json

data class Cocktail(@field:Json(name = "strDrink") val name: String,
                    @field:Json(name = "strDrinkThumb") val imageUrl: String,
                    @field:Json(name = "idDrink") val id: String)