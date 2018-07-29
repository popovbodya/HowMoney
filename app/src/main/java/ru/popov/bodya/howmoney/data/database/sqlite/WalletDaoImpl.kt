package ru.popov.bodya.howmoney.data.database.sqlite

import ru.popov.bodya.howmoney.data.database.dao.WalletDao
import ru.popov.bodya.howmoney.domain.account.models.Currency
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory
import ru.popov.bodya.howmoney.domain.operation.models.EnrollmentOperation
import ru.popov.bodya.howmoney.domain.operation.models.ExpenseOperation

/**
 *  @author popovbodya
 */
class WalletDaoImpl : WalletDao {

    override fun getDebitWalletEnrollmentOperationList(): List<EnrollmentOperation> {
        return createStubEnrollmentOperationList()
    }

    override fun getDebitWalletExpenseOperationList(): List<ExpenseOperation> {
        return createStubExpenseOperationList()
    }

    override fun getCreditWalletEnrollmentOperationList(): List<EnrollmentOperation> {
        return createStubEnrollmentOperationList()
    }

    override fun getCreditWalletExpenseOperationList(): List<ExpenseOperation> {
        return createStubExpenseOperationList()
    }

    override fun getCacheWalletEnrollmentOperationList(): List<EnrollmentOperation> {
        return createStubEnrollmentOperationList()
    }

    override fun getCacheWalletExpenseOperationList(): List<ExpenseOperation> {
        return createStubExpenseOperationList()
    }

    private fun createStubEnrollmentOperationList(): List<EnrollmentOperation> = listOf(
            EnrollmentOperation(1.0, EnrollmentCategory.Transfer, Currency.USD),
            EnrollmentOperation(250.0, EnrollmentCategory.Cache, Currency.RUB),
            EnrollmentOperation(5000.0, EnrollmentCategory.Salary, Currency.RUB),
            EnrollmentOperation(2.0, EnrollmentCategory.Transfer, Currency.USD)
    )

    private fun createStubExpenseOperationList(): List<ExpenseOperation> = listOf(
            ExpenseOperation(5.0, ExpenseCategory.Supermarket, Currency.USD),
            ExpenseOperation(25.0, ExpenseCategory.Transport, Currency.USD),
            ExpenseOperation(300.0, ExpenseCategory.Clothing, Currency.USD),
            ExpenseOperation(4000.0, ExpenseCategory.Health, Currency.RUB),
            ExpenseOperation(1000.0, ExpenseCategory.Restaurant, Currency.RUB)
    )
}

