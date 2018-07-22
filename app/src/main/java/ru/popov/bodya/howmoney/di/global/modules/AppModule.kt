package ru.popov.bodya.howmoney.di.global.modules

import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 *  @author popovbodya
 */
@Module(includes = [
    AndroidInjectionModule::class,
    RxModule::class
])
interface AppModule