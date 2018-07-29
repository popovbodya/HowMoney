package ru.popov.bodya.howmoney.presentation.mvp.expense

import ru.popov.bodya.core.mvp.AppView
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory

/**
 *  @author popovbodya
 */
interface ExpenseView: AppView {

    fun showDiagram(expenseCategoryMap: Map<ExpenseCategory, Double>, finalBalance: Double)

}