package com.pewick.mcocktailapp.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pewick.mcocktailapp.R
import com.pewick.mcocktailapp.adapters.CategoriesListAdapter
import com.pewick.mcocktailapp.viewmodels.CategoriesViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CategoriesView @JvmOverloads constructor(
    context: Context? = null,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : LinearLayout(context, attributeSet, defStyleAttr), Injectable {

    @Inject
    lateinit var viewModel: CategoriesViewModel

    private lateinit var categoriesRecycler: RecyclerView

    private lateinit var disposables: CompositeDisposable


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.onAttached()
        disposables = CompositeDisposable()
        bindViews()
        bindEvents()
        viewModel.loadCategories()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.onDetached()
        disposables.dispose()
    }

    private fun bindViews() {
        categoriesRecycler = findViewById<RecyclerView>(R.id.categories_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun bindEvents(){
        disposables.add(viewModel.categoriesLoaded
            .doOnError { Log.e("CategoriesView","Found error: $it") }
            .subscribe{
                categoriesRecycler.adapter = CategoriesListAdapter(viewModel, viewModel.getCategories())
            })
    }

}