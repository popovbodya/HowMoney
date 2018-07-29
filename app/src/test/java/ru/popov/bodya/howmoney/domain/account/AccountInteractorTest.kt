package ru.popov.bodya.howmoney.domain.account

/**
 * @author popovbodya
 */
class AccountInteractorTest {

//    private lateinit var accountInteractor: AccountInteractor
//    private lateinit var currencyRateRepository: CurrencyRateRepository
//
//    @Before
//    fun setUp() {
//        currencyRateRepository = mock(CurrencyRateRepository::class.java)
//        accountInteractor = AccountInteractor(currencyRateRepository)
//    }
//
//    @Test
//    fun testCalculateBalance() {
//        `when`(currencyRateRepository.getExchangeRate()).thenReturn(60)
//        val operationList = createStubOperationList()
//        val actual: Long = accountInteractor.calculateBalance(operationList)
//
//        assertThat(actual, `is`(-212L))
//        verify(currencyRateRepository, times(2)).getExchangeRate()
//        verifyNoMoreInteractions(currencyRateRepository)
//    }
//
//    @Test
//    fun getCurrencyAmountUSD() {
//        `when`(currencyRateRepository.getCurrentBalance()).thenReturn(301_456L)
//        `when`(currencyRateRepository.getExchangeRate()).thenReturn(60)
//        accountInteractor.getCurrentCurrencyAmount(Currency.USD)
//                .test()
//                .assertValue(ExpenseCategoryBalance(5024L, Currency.USD))
//        verify(currencyRateRepository).getCurrentBalance()
//        verify(currencyRateRepository).getExchangeRate()
//        verifyNoMoreInteractions(currencyRateRepository)
//    }
//
//    @Test
//    fun getCurrencyAmountRUB() {
//        `when`(currencyRateRepository.getCurrentBalance()).thenReturn(301_456L)
//        `when`(currencyRateRepository.getExchangeRate()).thenReturn(60)
//        accountInteractor.getCurrentCurrencyAmount(Currency.RUB)
//                .test()
//                .assertValue(ExpenseCategoryBalance(301_456L, Currency.RUB))
//        verify(currencyRateRepository).getCurrentBalance()
//        verifyNoMoreInteractions(currencyRateRepository)
//    }
//
//    private fun createStubOperationList() = listOf(
//            Operation(Expense, Currency.RUB, 50),
//            Operation(Enrollment, Currency.USD, 3),
//            Operation(Enrollment, Currency.RUB, 15),
//            Operation(Enrollment, Currency.RUB, 3),
//            Operation(Expense, Currency.USD, 6)
//    )
}