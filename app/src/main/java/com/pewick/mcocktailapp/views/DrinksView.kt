package com.pewick.mcocktailapp.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pewick.mcocktailapp.R
import com.pewick.mcocktailapp.adapters.DrinksListAdapter
import com.pewick.mcocktailapp.viewmodels.DrinksViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DrinksView @JvmOverloads constructor(
    context: Context? = null,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : LinearLayout(context, attributeSet, defStyleAttr), Injectable {

    @Inject
    lateinit var viewModel: DrinksViewModel

    private lateinit var drinksRecycler: RecyclerView

    private lateinit var disposables: CompositeDisposable

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.onAttached()
        disposables = CompositeDisposable()
        bindViews()
        bindEvents()
        viewModel.loadDrinks()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.onDetached()
        disposables.dispose()
    }

    private fun bindViews() {
        drinksRecycler = findViewById<RecyclerView>(R.id.drinks_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun bindEvents() {
        disposables.add(viewModel.drinksLoaded
            .doOnError { Log.e("DrinksView","Found error: $it") }
            .subscribe{
                drinksRecycler.adapter = DrinksListAdapter(viewModel, viewModel.getDrinks())
            })
    }

}