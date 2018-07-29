package ru.popov.bodya.howmoney.domain.operation.models

import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory

/**
 *  @author popovbodya
 */
sealed class Operation

data class EnrollmentOperation(val amount: Double, val enrollmentCategory: EnrollmentCategory, val currency: Currency)
data class ExpenseOperation(val amount: Double, val expenseCategory: ExpenseCategory, val currency: Currency)