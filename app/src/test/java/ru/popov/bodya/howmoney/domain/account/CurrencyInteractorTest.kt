package ru.popov.bodya.howmoney.domain.account

import io.reactivex.Single
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.popov.bodya.howmoney.data.repositories.CurrencyRepository
import ru.popov.bodya.howmoney.domain.account.interactors.CurrencyInteractor
import ru.popov.bodya.howmoney.domain.account.models.Account
import ru.popov.bodya.howmoney.domain.account.models.Currency
import ru.popov.bodya.howmoney.domain.operation.models.Operation
import ru.popov.bodya.howmoney.domain.operation.models.OperationType.ADDITION
import ru.popov.bodya.howmoney.domain.operation.models.OperationType.WITHDRAWAL

/**
 * @author popovbodya
 */
class CurrencyInteractorTest {

    private lateinit var currencyInteractor: CurrencyInteractor
    private lateinit var currencyRepository: CurrencyRepository

    @Before
    fun setUp() {
        currencyRepository = mock(CurrencyRepository::class.java)
        currencyInteractor = CurrencyInteractor(currencyRepository)
    }

    @Test
    fun testCalculateBalance() {
        `when`(currencyRepository.getExchangeRate()).thenReturn(60)
        val operationList = createStubOperationList()
        val actual: Long = currencyInteractor.calculateBalance(operationList)

        assertThat(actual, `is`(-212L))
        verify(currencyRepository, times(2)).getExchangeRate()
        verifyNoMoreInteractions(currencyRepository)
    }

    @Test
    fun getCurrencyAmountUSD() {
        `when`(currencyRepository.getAmount()).thenReturn(301_456L)
        `when`(currencyRepository.getExchangeRate()).thenReturn(60)
        currencyInteractor.getCurrentCurrencyAmount(Currency.USD)
                .test()
                .assertValue(Account(5024L, Currency.USD))
        verify(currencyRepository).getAmount()
        verify(currencyRepository).getExchangeRate()
        verifyNoMoreInteractions(currencyRepository)
    }

    @Test
    fun getCurrencyAmountRUB() {
        `when`(currencyRepository.getAmount()).thenReturn(301_456L)
        `when`(currencyRepository.getExchangeRate()).thenReturn(60)
        currencyInteractor.getCurrentCurrencyAmount(Currency.RUB)
                .test()
                .assertValue(Account(301_456L, Currency.RUB))
        verify(currencyRepository).getAmount()
        verifyNoMoreInteractions(currencyRepository)
    }

    private fun createStubOperationList() = listOf(
            Operation(WITHDRAWAL, Currency.RUB, 50),
            Operation(ADDITION, Currency.USD, 3),
            Operation(ADDITION, Currency.RUB, 15),
            Operation(ADDITION, Currency.RUB, 3),
            Operation(WITHDRAWAL, Currency.USD, 6)
    )
}