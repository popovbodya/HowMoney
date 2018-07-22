package ru.popov.bodya.howmoney.presentation.mvp.overview

import ru.popov.bodya.core.mvp.AppView

/**
 *  @author popovbodya
 */
interface OverviewView: AppView {

    fun showNewData()

    fun showError()

}