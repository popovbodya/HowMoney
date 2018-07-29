package ru.popov.bodya.howmoney.data.database.sqlite

import ru.popov.bodya.howmoney.data.database.dao.WalletDao
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
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
            EnrollmentOperation(20.0, EnrollmentCategory.Transfer, Currency.USD),
            EnrollmentOperation(2500.0, EnrollmentCategory.Cache, Currency.RUB),
            EnrollmentOperation(5000.0, EnrollmentCategory.Salary, Currency.RUB),
            EnrollmentOperation(20.0, EnrollmentCategory.Transfer, Currency.USD)
    )

    private fun createStubExpenseOperationList(): List<ExpenseOperation> = listOf(
            ExpenseOperation(400.0, ExpenseCategory.Supermarket, Currency.USD),
            ExpenseOperation(250.0, ExpenseCategory.Transport, Currency.USD),
            ExpenseOperation(300.0, ExpenseCategory.Clothing, Currency.USD),
            ExpenseOperation(20000.0, ExpenseCategory.Health, Currency.RUB),
            ExpenseOperation(10000.0, ExpenseCategory.Restaurant, Currency.RUB)
    )
}

