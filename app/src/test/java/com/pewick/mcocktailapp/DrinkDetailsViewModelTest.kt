package com.pewick.mcocktailapp

import io.reactivex.Observable
import com.pewick.mcocktailapp.datamodels.DrinkDetails
import com.pewick.mcocktailapp.datamodels.DrinkDetailsResponse
import com.pewick.mcocktailapp.models.CocktailsModel
import com.pewick.mcocktailapp.services.CocktailDB
import com.pewick.mcocktailapp.viewmodels.DrinkDetailsViewModel
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DrinkDetailsViewModelTest {

    @Mock
    private lateinit var service: CocktailDB

    private lateinit var model: CocktailsModel
    private lateinit var drinkDetailsVM: DrinkDetailsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        model = CocktailsModel(service)
        drinkDetailsVM = DrinkDetailsViewModel(model)
    }

    @Test
    fun whenDetailsLoaded_ThenGetFormattedIngredientsList() {
        Mockito.`when`(service.getDrinkDetails(details.id)).thenReturn(Observable.just(DrinkDetailsResponse(drinks = arrayOf(details))))
        model.drinkDetails = details

        val expected = arrayListOf("meas1 in1", "meas2 in2", "in3")
        drinkDetailsVM.drinkDetails = details
        assertEquals(expected, drinkDetailsVM.getFormattedIngredientsList())
    }

    val details = DrinkDetails(
        name = "first",
        imageUrl = "url",
        id = "123",
        category = "category",
        glass = "cup",
        alcoholic = "alcoholic",
        instructions = "do stuff",
        ingredient1 = "in1",
        ingredient2 = "in2",
        ingredient3 = "in3",
        ingredient4 = null,
        ingredient5 = null,
        ingredient6 = null,
        ingredient7 = null,
        ingredient8 = null,
        ingredient9 = null,
        ingredient10 = null,
        ingredient11 = null,
        ingredient12 = null,
        ingredient13 = null,
        ingredient14 = null,
        ingredient15 = null,
        measure1 = "meas1 ",
        measure2 = "meas2 ",
        measure3 = null,
        measure4 = null,
        measure5 = null,
        measure6 = null,
        measure7 = null,
        measure8 = null,
        measure9 = null,
        measure10 = null,
        measure11 = null,
        measure12 = null,
        measure13 = null,
        measure14 = null,
        measure15 = null)
}