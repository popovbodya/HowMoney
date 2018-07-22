package ru.popov.bodya.howmoney.di.global

import dagger.Module
import ru.popov.bodya.howmoney.di.overview.module.OverviewModule

/**
 *  @author popovbodya
 */
@Module(includes = [
    OverviewModule::class
])
class ActivitiesBuildersModule