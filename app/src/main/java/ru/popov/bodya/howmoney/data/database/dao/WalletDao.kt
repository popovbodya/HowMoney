package ru.popov.bodya.howmoney.data.database.dao

import ru.popov.bodya.howmoney.domain.operation.models.EnrollmentOperation
import ru.popov.bodya.howmoney.domain.operation.models.ExpenseOperation
import ru.popov.bodya.howmoney.domain.operation.models.Operation

/**
 *  @author popovbodya
 */
interface WalletDao {

    fun getDebitWalletEnrollmentOperationList(): List<EnrollmentOperation>

    fun getDebitWalletExpenseOperationList(): List<ExpenseOperation>

    fun getCreditWalletEnrollmentOperationList(): List<EnrollmentOperation>

    fun getCreditWalletExpenseOperationList(): List<ExpenseOperation>

    fun getCacheWalletEnrollmentOperationList(): List<EnrollmentOperation>

    fun getCacheWalletExpenseOperationList(): List<ExpenseOperation>

    fun saveDebitWalletEnrollOperation(enrollmentOperation: EnrollmentOperation)

    fun saveDebitWalletExpenseOperation(expenseOperation: ExpenseOperation)

    fun saveCreditWalletEnrollOperation(enrollmentOperation: EnrollmentOperation)

    fun saveCreditWalletExpenseOperation(expenseOperation: ExpenseOperation)

    fun saveCacheWalletEnrollOperation(enrollmentOperation: EnrollmentOperation)

    fun saveCacheWalletExpenseOperation(expenseOperation: ExpenseOperation)
}