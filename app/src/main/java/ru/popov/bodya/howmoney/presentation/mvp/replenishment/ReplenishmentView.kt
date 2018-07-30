package ru.popov.bodya.howmoney.presentation.mvp.replenishment

import ru.popov.bodya.core.mvp.AppView

/**
 *  @author popovbodya
 */
interface ReplenishmentView : AppView {

    fun setWalletNameTitle(name: String)
}