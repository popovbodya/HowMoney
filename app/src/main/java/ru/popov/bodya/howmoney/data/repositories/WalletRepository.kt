package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Single
import ru.popov.bodya.howmoney.data.database.dao.WalletDao
import ru.popov.bodya.howmoney.domain.operation.models.EnrollmentOperation
import ru.popov.bodya.howmoney.domain.operation.models.ExpenseOperation
import ru.popov.bodya.howmoney.domain.operation.models.Operation
import ru.popov.bodya.howmoney.domain.operation.models.OperationType
import ru.popov.bodya.howmoney.domain.operation.models.OperationType.*

import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet.*

/**
 *  @author popovbodya
 */
class WalletRepository(private val walletDao: WalletDao) {

    fun getEnrollmentOperationList(wallet: Wallet): List<EnrollmentOperation> {
        return when (wallet) {
            DebitWallet -> walletDao.getDebitWalletEnrollmentOperationList()
            CreditWallet -> walletDao.getCreditWalletEnrollmentOperationList()
            CacheWallet -> walletDao.getCacheWalletEnrollmentOperationList()
        }

    }

    fun getExpenseOperationList(wallet: Wallet): List<ExpenseOperation> {
        return when (wallet) {
            DebitWallet -> walletDao.getDebitWalletExpenseOperationList()
            CreditWallet -> walletDao.getCreditWalletExpenseOperationList()
            CacheWallet -> walletDao.getCacheWalletExpenseOperationList()
        }
    }

    fun saveEnrollmentOperation(wallet: Wallet, enrollmentOperation: EnrollmentOperation) {
        when (wallet) {
            DebitWallet -> walletDao.saveDebitWalletEnrollOperation(enrollmentOperation)
            CreditWallet -> walletDao.saveCreditWalletEnrollOperation(enrollmentOperation)
            CacheWallet -> walletDao.saveCacheWalletEnrollOperation(enrollmentOperation)
        }
    }

    fun saveExpenseOperation(wallet: Wallet, expenseOperation: ExpenseOperation) {
        when (wallet) {
            DebitWallet -> walletDao.saveDebitWalletExpenseOperation(expenseOperation)
            CreditWallet -> walletDao.saveCacheWalletExpenseOperation(expenseOperation)
            CacheWallet -> walletDao.saveCacheWalletExpenseOperation(expenseOperation)
        }
    }
}