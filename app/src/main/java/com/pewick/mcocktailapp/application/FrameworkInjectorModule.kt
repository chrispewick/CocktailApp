package com.pewick.mcocktailapp.application

import com.pewick.mcocktailapp.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FrameworkInjectorModule {

    @ContributesAndroidInjector
    fun mainActivity(): MainActivity
}