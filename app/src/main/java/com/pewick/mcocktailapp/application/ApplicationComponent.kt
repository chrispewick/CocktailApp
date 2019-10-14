package com.pewick.mcocktailapp.application

import com.pewick.mcocktailapp.main.MainActivityComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, FrameworkInjectorModule::class])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: CocktailApplication): Builder
        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun build(): ApplicationComponent
    }

    fun mainActivityComponentBuilder(): MainActivityComponent.Builder

    fun inject(application: CocktailApplication)
}