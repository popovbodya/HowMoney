package ru.popov.bodya.howmoney.data.repositories


import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import ru.popov.bodya.howmoney.data.database.dao.ExchangeRateDao
import ru.popov.bodya.howmoney.data.database.dao.TransactionsDao
import ru.popov.bodya.howmoney.data.database.dao.WalletDao
import ru.popov.bodya.howmoney.data.network.api.CurrenciesRateApiWrapper
import ru.popov.bodya.howmoney.domain.wallet.WalletInteractor
import ru.popov.bodya.howmoney.domain.wallet.models.*
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import java.util.*

@RunWith(JUnit4::class)
class WalletInteractorTest {

    private lateinit var transactionsRepository: TransactionsRepository
    private lateinit var walletRepository: WalletRepository
    private lateinit var currencyRateRepository: CurrencyRateRepository
    private lateinit var currenciesRateApiWrapper: CurrenciesRateApiWrapper
    private lateinit var walletInteractor: WalletInteractor

    private lateinit var walletDao: WalletDao
    private lateinit var exchangeRateDao: ExchangeRateDao
    private lateinit var transactionsDao: TransactionsDao

    private val transactionForTesting = Transaction(0, Currency.RUB, 1.0, Category.OTHER, 0, "OTHER", Date())
    private val walletForTesting = Wallet(0, 1.0, Type.CASH, Currency.RUB, "Основной кошелек")

    @Before
    fun setUp() {
        transactionsDao = mock(TransactionsDao::class.java)
        walletDao = mock(WalletDao::class.java)
        exchangeRateDao = mock(ExchangeRateDao::class.java)
        walletRepository = mock(WalletRepository::class.java)
        currenciesRateApiWrapper = mock(CurrenciesRateApiWrapper::class.java)
        currencyRateRepository = mock(CurrencyRateRepository::class.java)
        transactionsRepository = mock(TransactionsRepository::class.java)
        walletInteractor = WalletInteractor(currencyRateRepository, walletRepository, transactionsRepository)
    }

    @Test
    fun test_createsWallet() {
        walletInteractor.createWallet(walletForTesting).test()
        verify(walletDao).insert(walletForTesting)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getsWalletBalance() {
        `when`(walletDao.getWalletById(walletForTesting.id)).thenReturn(Single.just(walletForTesting))
        walletInteractor.getWalletBalance(walletForTesting.id).test().assertValue(1.0)
        verify(walletDao).getWalletById(walletForTesting.id)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getsWalletMajorCurrency() {
        `when`(walletDao.getWalletById(walletForTesting.id)).thenReturn(Single.just(walletForTesting))
        walletInteractor.getMajorCurrencyForWallet(walletForTesting.id).test().assertValue(Currency.RUB)
        verify(walletDao).getWalletById(walletForTesting.id)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getsAllWallets() {
        val expected = listOf(walletForTesting)
        `when`(walletDao.getAllWallets()).thenReturn(Single.just(expected))
        walletInteractor.getAllWallets().test().assertValue(expected)
        verify(walletDao).getAllWallets()
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getsAllIncomeTransactions() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllIncomeTransactions()).thenReturn(Single.just(expected))
        walletInteractor.getAllIncomeTransactions().test().assertValue(expected)
        verify(transactionsDao).getAllIncomeTransactions()
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getsAllExpenseTransactions() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllExpenseTransactions()).thenReturn(Single.just(expected))
        walletInteractor.getAllExpenseTransactions().test().assertValue(expected)
        verify(transactionsDao).getAllExpenseTransactions()
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getsAllIncomeTransactionsByWallet() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllIncomeTransactionsByWalletId(walletForTesting.id)).thenReturn(Single.just(expected))
        walletInteractor.getAllIncomeTransactionsByWallet(walletForTesting.id).test().assertValue(expected)
        verify(transactionsDao).getAllIncomeTransactionsByWalletId(walletForTesting.id)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getsAllExpenseTransactionsByWallet() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllExpenseTransactionsByWalletId(walletForTesting.id)).thenReturn(Single.just(expected))
        walletInteractor.getAllExpenseTransactionsByWallet(walletForTesting.id).test().assertValue(expected)
        verify(transactionsDao).getAllExpenseTransactionsByWalletId(walletForTesting.id)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_getsAllTransactionsByWallet() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsDao.getAllTransactionsByWalletId(walletForTesting.id)).thenReturn(Single.just(expected))
        walletInteractor.getAllTransactionsByWallet(walletForTesting.id).test().assertValue(expected)
        verify(transactionsDao).getAllTransactionsByWalletId(walletForTesting.id)
        verifyNoMoreInteractions(transactionsDao)
    }

    @Test
    fun test_createsTransaction_withDiffCurrency() {
        val transactionToCreateWithDifferentCurrency = transactionForTesting.copy(currency = Currency.USD)

        `when`(walletRepository.getWalletById(walletForTesting.id)).thenReturn(Single.just(walletForTesting))
        `when`(currencyRateRepository.getExchangeRate(transactionToCreateWithDifferentCurrency.currency, walletForTesting.majorCurrency))
                .thenReturn(Single.just(3.0))
        walletInteractor.createTransaction(transactionToCreateWithDifferentCurrency).test()
        verify(walletRepository, times(2)).getWalletById(walletForTesting.id)
        verify(currencyRateRepository).getExchangeRate(transactionToCreateWithDifferentCurrency.currency, walletForTesting.majorCurrency)
        verify(walletRepository).increaseWalletBalance(walletForTesting.id, 3.0)
    }

    @Test
    fun test_createsTransaction_withSameCurrency() {
        val transactionToCreateWithTheSameCurrency = transactionForTesting.copy(amount = 2.0)

        `when`(walletRepository.getWalletById(walletForTesting.id)).thenReturn(Single.just(walletForTesting))
        walletInteractor.createTransaction(transactionToCreateWithTheSameCurrency).test()
        verify(walletRepository, times(2)).getWalletById(walletForTesting.id)
        verify(walletRepository).updateWalletBalance(walletForTesting.id, 3.0)
    }
}