package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Single
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import ru.popov.bodya.howmoney.data.network.api.CurrenciesRateApiWrapper
import ru.popov.bodya.howmoney.data.network.beans.CurrentRateBean

/**
 * @author popovbodya
 */
class CurrencyRateRepositoryTest {

    private lateinit var currenciesRateApiWrapper: CurrenciesRateApiWrapper
    private lateinit var sharedPreferencesWrapper: SharedPreferencesWrapper
    private lateinit var currencyRateRepository: CurrencyRateRepository

    @Before
    fun setUp() {
        currenciesRateApiWrapper = mock(CurrenciesRateApiWrapper::class.java)
        sharedPreferencesWrapper = mock(SharedPreferencesWrapper::class.java)
        currencyRateRepository = CurrencyRateRepository(currenciesRateApiWrapper, sharedPreferencesWrapper)
    }

    @Test
    fun testGetExchangeRateSuccess() {
        val expected = CurrentRateBean("25.06.1995", 33.0)
        `when`(currenciesRateApiWrapper.getCurrentRate()).thenReturn(Single.just(expected))
        currencyRateRepository.getExchangeRate()
                .test()
                .assertValue(expected)
        verify(currenciesRateApiWrapper).getCurrentRate()
        verifyNoMoreInteractions(currenciesRateApiWrapper)
        verify(sharedPreferencesWrapper).saveExchangeRate(expected)
        verifyNoMoreInteractions(sharedPreferencesWrapper)
    }

    @Test
    fun testGetExchangeRateError() {
        val expectedException = RuntimeException()
        `when`(currenciesRateApiWrapper.getCurrentRate()).thenReturn(Single.error(expectedException))
        currencyRateRepository.getExchangeRate()
                .test()
                .assertError(expectedException)
        verify(currenciesRateApiWrapper).getCurrentRate()
        verifyNoMoreInteractions(currenciesRateApiWrapper)
        verifyZeroInteractions(sharedPreferencesWrapper)
    }

    @Test
    fun testGetCachedExchangeRate() {
        val expected = 62.7
        `when`(sharedPreferencesWrapper.getExchangeRate()).thenReturn("62.7")
        val actual = currencyRateRepository.getCachedExchangeRate()
        assertThat(actual, `is`(expected))
        verify(sharedPreferencesWrapper).getExchangeRate()
        verifyNoMoreInteractions(sharedPreferencesWrapper)
    }

    @Test(expected = NumberFormatException::class)
    fun testGetCachedExchangeRateParseError() {
        `when`(sharedPreferencesWrapper.getExchangeRate()).thenReturn("`62..7")
        currencyRateRepository.getCachedExchangeRate()
    }


}