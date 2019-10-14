package com.pewick.mcocktailapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.pewick.mcocktailapp.R
import com.pewick.mcocktailapp.application.ApplicationComponent
import com.pewick.mcocktailapp.navigation.*
import com.pewick.mcocktailapp.navigation.keys.CategoriesViewKey
import com.pewick.mcocktailapp.views.Injectable
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.util.Log
import com.pewick.mcocktailapp.navigation.keys.ViewKey


class MainActivity : AppCompatActivity(), ViewChanger, Injectable {

    @Inject
    lateinit var applicationComponent: ApplicationComponent
    private lateinit var  mainActivityComponent: MainActivityComponent
    private lateinit var viewInjector: ViewInjector

    private lateinit var frame: ViewGroup
    private lateinit var stateChanger: NavigationStageChanger
    private lateinit var backStack: BackStack
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frame = findViewById(R.id.frame)

        AndroidInjection.inject(this)

        stateChanger = NavigationStageChanger(this)
        backStack = BackStack(stateChanger)
        navigator = Navigator(backStack)

        val mainActivityModule = MainActivityModule(navigator)
        mainActivityComponent = applicationComponent
            .mainActivityComponentBuilder()
            .mainActivity(this)
            .mainActivityModule(mainActivityModule)
            .build()
        viewInjector = ViewInjector(mainActivityComponent)

        // initialize backstack
        val viewKeys = arrayListOf<ViewKey>()
        if (savedInstanceState != null) {
            Log.i("Main","savedInstance not null")
            viewKeys.addAll(savedInstanceState.getParcelableArrayList("BACKSTACK")) // restore from bundle if exists
        } else {
            viewKeys.add(CategoriesViewKey())
        }
        backStack.setHistory(viewKeys, StateChange.Direction.REPLACE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val history = ArrayList<ViewKey>()
        history.addAll(backStack.getHistory())
        outState.putParcelableArrayList(
            "BACKSTACK",
            history
        ) // persist copy of current backstack history to bundle
    }

    override fun onBackPressed() {
        if(!backStack.goBack()) {
            super.onBackPressed() // If our back stack did not handle the event, call super
        }
    }

    override fun changeView(layoutId: Int, direction: StateChange.Direction) {
        val view = layoutInflater.inflate(layoutId, frame, false)
        if(view is Injectable) {
            viewInjector.inject(view)
        }
        frame.removeAllViews()
        frame.addView(view)
    }
}
