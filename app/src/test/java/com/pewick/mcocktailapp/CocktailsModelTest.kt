package com.pewick.mcocktailapp

import com.pewick.mcocktailapp.datamodels.*
import com.pewick.mcocktailapp.models.CocktailsModel
import com.pewick.mcocktailapp.services.CocktailDB
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class CocktailsModelTest {

    @Mock
    private lateinit var service: CocktailDB

    private lateinit var model: CocktailsModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        model = CocktailsModel(service)
    }

    @Test
    fun whenLoadingFirstTime_ThenLoadCategories() {
        val response = CategoriesResponse(
            drinks = arrayOf(Category(name = "first"), Category(name = "second"), Category(name = "third"))
        )
        Mockito.`when`(service.getCategories()).thenReturn(Observable.just(response))

        val testObserver = TestObserver<Boolean>()

        model.loadCategories().subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { true }

        assertEquals(model.categoriesList.size, 3)
        assertArrayEquals(model.categoriesList.toArray(), response.drinks)
    }

    @Test
    fun whenCategoriesAlreadyLoaded_ThenDoNotDownload() {
        val list = arrayListOf(Category(name = "first"), Category(name = "second"), Category(name = "third"))
        val unwantedResponse = CategoriesResponse(drinks = arrayOf(
            Category(name = "fail1"),
            Category(name = "fail2"),
            Category(name = "fail3"),
            Category(name = "fail4")))
        Mockito.`when`(service.getCategories()).thenReturn(Observable.just(unwantedResponse))
        model.categoriesList = list

        val testObserver = TestObserver<Boolean>()

        model.loadCategories().subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { true }
        assertEquals(model.categoriesList.size, 3)
        assertArrayEquals(model.categoriesList.toArray(), list.toArray())
    }

    @Test
    fun whenCategoryPassedIn_loadDrinks() {
        val goodResponse = DrinksResponse(
            drinks = arrayOf(
                Drink(name = "first", id = "id1", imageUrl = "url1"),
                Drink(name = "second", id = "id2", imageUrl = "url2"),
                Drink(name = "third", id = "id3", imageUrl = "url3")
            ))
        Mockito.`when`(service.getDrinksByCategory("test")).thenReturn(Observable.just(goodResponse))

        val testObserver = TestObserver<Boolean>()

        model.loadDrinks("test").subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { true }
        assertEquals(model.drinksList.size, 3)
        assertArrayEquals(model.drinksList.toArray(), goodResponse.drinks)
    }

    @Test
    fun whenCategoryAlreadySelected_loadDrinks() {
        val goodResponse = DrinksResponse(
            drinks = arrayOf(
                Drink(name = "first", id = "id1", imageUrl = "url1"),
                Drink(name = "second", id = "id2", imageUrl = "url2"),
                Drink(name = "third", id = "id3", imageUrl = "url3")
            ))
        Mockito.`when`(service.getDrinksByCategory("categoryTest")).thenReturn(Observable.just(goodResponse))

        val testObserver = TestObserver<Boolean>()

        model.updateCategorySelection("categoryTest")
        model.loadDrinks().subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { response -> response }
        assertEquals(model.drinksList.size, 3)
        assertArrayEquals(model.drinksList.toArray(), goodResponse.drinks)
    }



    @Test
    fun whenDrinkIdPassedIn_ThenLoadDrinkDetails() {
        val goodResponse = DrinkDetailsResponse(drinks = arrayOf(details)) // large 'details' object defined below
        Mockito.`when`(service.getDrinkDetails("123")).thenReturn(Observable.just(goodResponse))

        val testObserver = TestObserver<DrinkDetails?>()

        model.loadDrinkDetails("123").subscribe(testObserver)

        testObserver.assertNoErrors()
        assertEquals(model.drinkDetails, details)
    }

    @Test
    fun whenSelectedDrinkDetailsAlreadyLoaded_ThenDoNotDownload() {
        Mockito.`when`(service.getDrinkDetails("123"))
            .thenReturn(Observable.just(DrinkDetailsResponse(drinks = arrayOf(wrongDetails))))

        val testObserver = TestObserver<DrinkDetails>()

        model.updateDrinkIdSelection(details.id)
        model.drinkDetails = details
        model.loadDrinkDetails().subscribe(testObserver)

        testObserver.assertNoErrors()
        assertEquals(model.drinkDetails, details)
        assertNotEquals(model.drinkDetails, wrongDetails)
    }

    @Test
    fun whenChangingCategory_ThenClearPreviousDrinksList(){
        model.drinksList = arrayListOf(Drink(name = "first", id = "id1", imageUrl = "url1"),
            Drink(name = "second", id = "id2", imageUrl = "url2"),
            Drink(name = "third", id = "id3", imageUrl = "url3"))
        model.updateCategorySelection("newCategory")
        assertEquals(model.drinksList.size, 0)
    }

    @Test
    fun whenSelectingSameCategory_ThenKeepPreviousDrinksList(){
        val drinks = arrayListOf(Drink(name = "first", id = "id1", imageUrl = "url1"),
            Drink(name = "second", id = "id2", imageUrl = "url2"),
            Drink(name = "third", id = "id3", imageUrl = "url3"))
        model.updateCategorySelection("sameCategory")
        model.drinksList = drinks
        model.updateCategorySelection("sameCategory")
        assertEquals(model.drinksList.size, drinks.size)
        assertArrayEquals(model.drinksList.toArray(), drinks.toArray())
    }

    @Test
    fun whenChangingDrinkId_ThenDeletePreviousDrinkDetails(){
        model.drinkDetails = details
        model.updateDrinkIdSelection("newId")
        assertEquals(model.drinkDetails, null)
    }

    @Test
    fun whenSelectingSameDrinkId_ThenKeepPreviousDrinkDetails(){
        model.drinkDetails = details
        model.updateDrinkIdSelection(details.id)
        assertEquals(model.drinkDetails, details)
    }

    val details = DrinkDetails(
        name = "first",
        imageUrl = "url",
        id = "123",
        category = "category",
        glass = "cup",
        alcoholic = "alcoholic",
        instructions = "do stuff",
        ingredient1 = null,
        ingredient2 = null,
        ingredient3 = null,
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
        measure1 = null,
        measure2 = null,
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

    val wrongDetails = DrinkDetails(
        name = "fail",
        imageUrl = "fail",
        id = "123",
        category = "fail",
        glass = "fail",
        alcoholic = "fail",
        instructions = "do fail",
        ingredient1 = null,
        ingredient2 = null,
        ingredient3 = null,
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
        measure1 = null,
        measure2 = null,
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