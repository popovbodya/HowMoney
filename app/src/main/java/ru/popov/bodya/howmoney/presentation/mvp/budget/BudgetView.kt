package ru.popov.bodya.howmoney.presentation.mvp.budget

import ru.popov.bodya.core.mvp.AppView

/**
 *  @author popovbodya
 */
interface BudgetView : AppView {

    fun showEnrollmentBalance(amount: Double)

    fun showExpenseBalance(amount: Double)
}