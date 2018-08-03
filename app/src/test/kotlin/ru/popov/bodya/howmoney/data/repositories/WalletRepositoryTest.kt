package ru.popov.bodya.howmoney.data.repositories

import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*
import ru.popov.bodya.howmoney.data.database.dao.WalletDao
import ru.popov.bodya.howmoney.domain.wallet.models.*
import ru.popov.bodya.howmoney.domain.wallet.models.Currency

@RunWith(JUnit4::class)
class WalletRepositoryTest {

    private lateinit var walletDao: WalletDao
    private lateinit var walletRepository: WalletRepository

    private val walletForTesting = Wallet(0, 1.0, Type.CASH, Currency.RUB, "Основной кошелек")

    @Before
    fun setUp() {
        walletDao = Mockito.mock(WalletDao::class.java)
        walletRepository = WalletRepository(walletDao)
    }

    @Test
    fun test_getWalletsSuccess() {
        val expected = listOf(walletForTesting)
        `when`(walletDao.getAllWallets()).thenReturn(Single.just(expected))
        walletRepository.getWallets().test().assertValue(expected)
        verify(walletDao).getAllWallets()
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getWalletsError() {
        val expectedException = RuntimeException()
        `when`(walletDao.getAllWallets()).thenReturn(Single.error(expectedException))
        walletRepository.getWallets().test().assertError(expectedException)
        verify(walletDao).getAllWallets()
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_getsWalletsById() {
        val walletId = 0
        `when`(walletDao.getWalletById(walletId)).thenReturn(Single.just(walletForTesting))
        walletRepository.getWalletById(walletId).test().assertValue(walletForTesting)
        verify(walletDao).getWalletById(walletId)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_addsWallet() {
        walletRepository.addWallet(walletForTesting).test()
        verify(walletDao).insert(walletForTesting)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_deletesWallet() {
        walletRepository.deleteWallet(walletForTesting).test()
        verify(walletDao).delete(walletForTesting)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_updatesWalletBalance() {
        val walletId = 0
        val newBalance = 1.0
        walletRepository.updateWalletBalance(walletId, newBalance).test()
        verify(walletDao).updateWalletBalance(walletId, newBalance)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun test_increasesWalletBalance() {
        val walletId = 0
        val inc = 1.0
        walletRepository.increaseWalletBalance(walletId, inc).test()
        verify(walletDao).increaseWalletBalance(walletId, inc)
        verifyNoMoreInteractions(walletDao)
    }
}