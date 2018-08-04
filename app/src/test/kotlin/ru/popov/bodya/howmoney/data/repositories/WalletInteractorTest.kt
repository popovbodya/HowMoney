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


    private lateinit var transactionForTesting: Transaction
    private lateinit var walletForTesting: Wallet

    @Before
    fun setUp() {
        transactionForTesting = Transaction(0, Currency.RUB, 1.0, Category.OTHER, 0, "OTHER", Date())
        walletForTesting = Wallet(0, 1.0, Type.CASH, Currency.RUB, "Основной кошелек")
        walletRepository = mock(WalletRepository::class.java)
        currenciesRateApiWrapper = mock(CurrenciesRateApiWrapper::class.java)
        currencyRateRepository = mock(CurrencyRateRepository::class.java)
        transactionsRepository = mock(TransactionsRepository::class.java)
        walletInteractor = WalletInteractor(currencyRateRepository, walletRepository, transactionsRepository)
    }

    @Test
    fun wallet_create() {
        walletInteractor.createWallet(walletForTesting).test().assertResult()
        verify(walletRepository).addWallet(walletForTesting)
        verifyNoMoreInteractions(walletRepository)
    }

    @Test
    fun walletBalance_get() {
        `when`(walletRepository.getWalletById(walletForTesting.id)).thenReturn(Single.just(walletForTesting))
        walletInteractor.getWalletBalance(walletForTesting.id).test().assertValue(1.0)
        verify(walletRepository).getWalletById(walletForTesting.id)
        verifyNoMoreInteractions(walletRepository)
    }

    @Test
    fun walletCurrency_get() {
        `when`(walletRepository.getWalletById(walletForTesting.id)).thenReturn(Single.just(walletForTesting))
        walletInteractor.getMajorCurrencyForWallet(walletForTesting.id).test().assertValue(Currency.RUB)
        verify(walletRepository).getWalletById(walletForTesting.id)
        verifyNoMoreInteractions(walletRepository)
    }

    @Test
    fun wallets_get() {
        val expected = listOf(walletForTesting)
        `when`(walletRepository.getWallets()).thenReturn(Single.just(expected))
        walletInteractor.getAllWallets().test().assertValue(expected)
        verify(walletRepository).getWallets()
        verifyNoMoreInteractions(walletRepository)
    }

    @Test
    fun incomeTransactions_getAll() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsRepository.getAllIncomeTransactions()).thenReturn(Single.just(expected))
        walletInteractor.getAllIncomeTransactions().test().assertValue(expected)
        verify(transactionsRepository).getAllIncomeTransactions()
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun expenseTransactions_getAll() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsRepository.getAllExpenseTransactions()).thenReturn(Single.just(expected))
        walletInteractor.getAllExpenseTransactions().test().assertValue(expected)
        verify(transactionsRepository).getAllExpenseTransactions()
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun incomeTransactions_getByWalletId() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsRepository.getAllIncomeTransactionsByWallet(walletForTesting.id)).thenReturn(Single.just(expected))
        walletInteractor.getAllIncomeTransactionsByWallet(walletForTesting.id).test().assertValue(expected)
        verify(transactionsRepository).getAllIncomeTransactionsByWallet(walletForTesting.id)
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun expenseTransactions_getByWalletId() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsRepository.getAllExpenseTransactionsByWallet(walletForTesting.id)).thenReturn(Single.just(expected))
        walletInteractor.getAllExpenseTransactionsByWallet(walletForTesting.id).test().assertValue(expected)
        verify(transactionsRepository).getAllExpenseTransactionsByWallet(walletForTesting.id)
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun transactions_getByWalletId() {
        val expected = listOf(transactionForTesting)
        `when`(transactionsRepository.getAllTransactionsByWallet(walletForTesting.id)).thenReturn(Single.just(expected))
        walletInteractor.getAllTransactionsByWallet(walletForTesting.id).test().assertValue(expected)
        verify(transactionsRepository).getAllTransactionsByWallet(walletForTesting.id)
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun transaction_createsWithDifferentCurrency() {
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
    fun transaction_createsWithSameCurrency() {
        val transactionToCreateWithTheSameCurrency = transactionForTesting.copy(amount = 2.0)

        `when`(walletRepository.getWalletById(walletForTesting.id)).thenReturn(Single.just(walletForTesting))
        walletInteractor.createTransaction(transactionToCreateWithTheSameCurrency).test()
        verify(walletRepository, times(2)).getWalletById(walletForTesting.id)
        verify(walletRepository).updateWalletBalance(walletForTesting.id, 3.0)
    }
}