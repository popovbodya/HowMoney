package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import ru.popov.bodya.howmoney.data.database.dao.TransactionsDao
import ru.popov.bodya.howmoney.domain.wallet.models.Category
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.wallet.models.Transaction
import java.util.Date

@RunWith(JUnit4::class)
class TransactionsRepositoryTest {

    private lateinit var transactionsRepository: TransactionsRepository
    private lateinit var transactionsDao: TransactionsDao

    private val transactionForTesting = Transaction(0, Currency.RUB, 1.0, Category.OTHER, 0, "OTHER", Date())

    @Before
    fun setUp() {
        transactionsDao = mock(TransactionsDao::class.java)
        transactionsRepository = TransactionsRepository(transactionsDao)
    }

    @Test
    fun test_addsTransaction() {
        transactionsRepository.addTransaction(transactionForTesting).test()
        verify(transactionsDao).insert(transactionForTesting)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getsAllTransactions() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllTransactions()).thenReturn(Single.just(expected))
        transactionsRepository.getAllTransactions().test().assertValue(expected)
        verify(transactionsDao).getAllTransactions()
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getsAllIncomeTransactions() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllIncomeTransactions()).thenReturn(Single.just(expected))
        transactionsRepository.getAllIncomeTransactions().test().assertValue(expected)
        verify(transactionsDao).getAllIncomeTransactions()
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getsAllExpenseTransactions() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllExpenseTransactions()).thenReturn(Single.just(expected))
        transactionsRepository.getAllExpenseTransactions().test().assertValue(expected)
        verify(transactionsDao).getAllExpenseTransactions()
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getsAllTransactionsByWallet() {
        val walletId = 228
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllTransactionsByWalletId(walletId)).thenReturn(Single.just(expected))
        transactionsRepository.getAllTransactionsByWallet(walletId).test().assertValue(expected)
        verify(transactionsDao).getAllTransactionsByWalletId(walletId)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getsAllIncomeTransactionsByWallet() {
        val walletId = 0
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllIncomeTransactionsByWalletId(walletId)).thenReturn(Single.just(expected))
        transactionsRepository.getAllIncomeTransactionsByWallet(walletId).test().assertValue(expected)
        verify(transactionsDao).getAllIncomeTransactionsByWalletId(walletId)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getAllExpenseTransactionsByWallet() {
        val walletId = 0
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllExpenseTransactionsByWalletId(walletId)).thenReturn(Single.just(expected))
        transactionsRepository.getAllExpenseTransactionsByWallet(walletId).test().assertValue(expected)
        verify(transactionsDao).getAllExpenseTransactionsByWalletId(walletId)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_deletesTransaction() {
        transactionsRepository.deleteTransaction(transactionForTesting).test()
        verify(transactionsDao).delete(transactionForTesting)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_updatesTransaction() {
        transactionsRepository.updateTransaction(transactionForTesting).test()
        verify(transactionsDao).update(transactionForTesting)
        verifyNoMoreInteractions(transactionsDao)
    }
}