package com.pewick.mcocktailapp.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pewick.mcocktailapp.R
import com.pewick.mcocktailapp.adapters.IngredientsListAdapter
import com.pewick.mcocktailapp.viewmodels.DrinkDetailsViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DrinkDetailsView @JvmOverloads constructor(
    context: Context? = null,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : ScrollView(context, attributeSet, defStyleAttr), Injectable {

    @Inject
    lateinit var viewModel: DrinkDetailsViewModel

    private lateinit var disposables: CompositeDisposable

    private lateinit var contentLayout: ConstraintLayout
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var subtitle: TextView
    private lateinit var glass: TextView
    private lateinit var ingredientsRecycler: RecyclerView
    private lateinit var instructions: TextView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.onAttached()
        disposables = CompositeDisposable()
        bindViews()
        bindEvents()
        viewModel.loadDrinkDetails()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.onDetached()
        disposables.dispose()
    }

    private fun bindViews() {
        contentLayout = findViewById(R.id.content_layout)
        image = findViewById(R.id.image)
        title = findViewById(R.id.title)
        subtitle  = findViewById(R.id.subtitle)
        glass = findViewById(R.id.glass)
        instructions = findViewById(R.id.instructions)
        ingredientsRecycler = findViewById<RecyclerView>(R.id.ingredients_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun bindEvents() {
        disposables.add(viewModel.detailsLoaded
            .doOnError { Log.e("DrinkDetailsView","Found error: $it") }
            .subscribe{
                populateFields()
            })
    }

    private fun populateFields() {
        contentLayout.visibility = View.VISIBLE
        Glide.with(this).load(viewModel.getImageUrl()).into(image)
        title.text = viewModel.getDrinkName()
        subtitle.text = String.format(
            resources.getString(R.string.subtitle),
            viewModel.getCategory(),
            viewModel.getAlcoholic())
        glass.text = viewModel.getGlass()
        ingredientsRecycler.adapter = IngredientsListAdapter(viewModel.getFormattedIngredientsList())
        instructions.text = viewModel.getInstructions()
    }

}