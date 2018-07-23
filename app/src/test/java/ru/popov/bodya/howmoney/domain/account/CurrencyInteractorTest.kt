package ru.popov.bodya.howmoney.domain.account

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.howmoney.domain.global.models.Currency
import ru.popov.bodya.howmoney.domain.global.models.Operation
import ru.popov.bodya.howmoney.domain.global.models.OperationType.ADDITION
import ru.popov.bodya.howmoney.domain.global.models.OperationType.WITHDRAWAL

/**
 * @author popovbodya
 */
class CurrencyInteractorTest {

    private lateinit var currencyInteractor: CurrencyInteractor

    @Before
    fun setUp() {
        currencyInteractor = CurrencyInteractor()
    }

    @Test
    fun testCalculateBalance() {
        val operationList = createStubOperationList()
        val actial: Long = currencyInteractor.calculateBalance(operationList)
        assertThat(actial, `is`(-212L))

    }

    private fun createStubOperationList() = listOf<Operation>(
            Operation(WITHDRAWAL, Currency.RUB, 50),
            Operation(ADDITION, Currency.USD, 3),
            Operation(ADDITION, Currency.RUB, 15),
            Operation(ADDITION, Currency.RUB, 3),
            Operation(WITHDRAWAL, Currency.USD, 6)

    )
}