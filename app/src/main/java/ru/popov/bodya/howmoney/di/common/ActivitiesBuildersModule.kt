package ru.popov.bodya.howmoney.di.common

import dagger.Module
import ru.popov.bodya.howmoney.di.launch.LaunchModule

/**
 *  @author popovbodya
 */
@Module(includes = [
    LaunchModule::class
])
class ActivitiesBuildersModule