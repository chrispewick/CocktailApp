package com.pewick.mcocktailapp.viewmodels

import io.reactivex.disposables.CompositeDisposable

abstract class ViewModel {

    lateinit var disposables: CompositeDisposable

    open fun onAttached(){
        disposables = CompositeDisposable()
    }

    open fun onDetached(){
        disposables.dispose()
    }
}