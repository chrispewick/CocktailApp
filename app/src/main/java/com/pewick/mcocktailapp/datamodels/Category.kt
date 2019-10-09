package com.pewick.mcocktailapp.datamodels

import com.squareup.moshi.Json

data class Category(@field:Json(name = "strCategory") val name: String)
