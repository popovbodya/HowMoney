package ru.popov.bodya.howmoney.presentation.mvp.account

import ru.popov.bodya.core.mvp.AppView
import ru.popov.bodya.howmoney.domain.global.models.Currency

/**
 *  @author popovbodya
 */
interface AccountView: AppView {

    fun showRUBAmount(amount: Long)

    fun showUSDAmount(amount: Long)
}