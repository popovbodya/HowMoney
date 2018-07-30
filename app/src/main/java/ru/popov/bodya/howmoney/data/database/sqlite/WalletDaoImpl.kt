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

    private val enrollmentOperationList: MutableList<EnrollmentOperation>
    private val expenseOperationList: MutableList<ExpenseOperation>

    init {
        enrollmentOperationList = createStubEnrollmentOperationList()
        expenseOperationList = createStubExpenseOperationList()
    }

    override fun getDebitWalletEnrollmentOperationList(): List<EnrollmentOperation> {
        return enrollmentOperationList
    }

    override fun getDebitWalletExpenseOperationList(): List<ExpenseOperation> {
        return expenseOperationList
    }

    override fun getCreditWalletEnrollmentOperationList(): List<EnrollmentOperation> {
        return enrollmentOperationList
    }

    override fun getCreditWalletExpenseOperationList(): List<ExpenseOperation> {
        return expenseOperationList
    }

    override fun getCacheWalletEnrollmentOperationList(): List<EnrollmentOperation> {
        return enrollmentOperationList
    }

    override fun getCacheWalletExpenseOperationList(): List<ExpenseOperation> {
        return expenseOperationList
    }

    override fun saveDebitWalletEnrollOperation(enrollmentOperation: EnrollmentOperation) {
        enrollmentOperationList.add(enrollmentOperation)
    }

    override fun saveDebitWalletExpenseOperation(expenseOperation: ExpenseOperation) {
        expenseOperationList.add(expenseOperation)
    }

    override fun saveCreditWalletEnrollOperation(enrollmentOperation: EnrollmentOperation) {
        enrollmentOperationList.add(enrollmentOperation)
    }

    override fun saveCreditWalletExpenseOperation(expenseOperation: ExpenseOperation) {
        expenseOperationList.add(expenseOperation)
    }

    override fun saveCacheWalletEnrollOperation(enrollmentOperation: EnrollmentOperation) {
        enrollmentOperationList.add(enrollmentOperation)
    }

    override fun saveCacheWalletExpenseOperation(expenseOperation: ExpenseOperation) {
        expenseOperationList.add(expenseOperation)
    }

    private fun createStubEnrollmentOperationList(): MutableList<EnrollmentOperation> = arrayListOf(
            EnrollmentOperation(20.0, EnrollmentCategory.Transfer, Currency.USD),
            EnrollmentOperation(2500.0, EnrollmentCategory.Cache, Currency.RUB),
            EnrollmentOperation(5000.0, EnrollmentCategory.Salary, Currency.RUB),
            EnrollmentOperation(20.0, EnrollmentCategory.Transfer, Currency.USD)
    )

    private fun createStubExpenseOperationList(): MutableList<ExpenseOperation> = arrayListOf(
            ExpenseOperation(400.0, ExpenseCategory.Supermarket, Currency.USD),
            ExpenseOperation(250.0, ExpenseCategory.Transport, Currency.USD),
            ExpenseOperation(300.0, ExpenseCategory.Clothing, Currency.USD),
            ExpenseOperation(20000.0, ExpenseCategory.Health, Currency.RUB),
            ExpenseOperation(10000.0, ExpenseCategory.Restaurant, Currency.RUB)
    )
}

