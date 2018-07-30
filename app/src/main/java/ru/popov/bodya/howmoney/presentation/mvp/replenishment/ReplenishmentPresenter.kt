package ru.popov.bodya.howmoney.presentation.mvp.replenishment

import com.arellomobile.mvp.InjectViewState
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.resources.ResourceManager
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.data.repositories.WalletRepository
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory
import ru.popov.bodya.howmoney.domain.operation.models.EnrollmentOperation
import ru.popov.bodya.howmoney.domain.operation.models.ExpenseOperation
import ru.popov.bodya.howmoney.domain.operation.models.OperationType
import ru.popov.bodya.howmoney.domain.operation.models.OperationType.Enrollment
import ru.popov.bodya.howmoney.domain.operation.models.OperationType.Expense
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class ReplenishmentPresenter @Inject constructor(
        private val walletRepository: WalletRepository,
        private val resourceManager: ResourceManager,
        private val router: Router) : AppPresenter<ReplenishmentView>() {

    private lateinit var wallet: Wallet

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initWalletName()
    }

    fun initPresenterParams(wallet: Wallet) {
        this.wallet = wallet
    }

    fun onAddReplenishmentButtonClick(operationType: OperationType?,
                                      enrollmentCategory: EnrollmentCategory?,
                                      expenseCategory: ExpenseCategory?,
                                      currency: Currency?, amount: Long?) {


        if (operationType == null || currency == null || amount == null) {
            return
        }

        when (operationType) {
            Enrollment -> enrollmentCategory?.let { saveEnrollment(it, currency, amount) }
            Expense -> expenseCategory?.let { saveExpense(it, currency, amount) }
        }
    }

    fun onUpButtonPressed() {
        router.exit()
    }

    private fun initWalletName() {
        val walletName = when (wallet) {
            Wallet.DebitWallet -> resourceManager.getString(R.string.wallet_debit)
            Wallet.CreditWallet -> resourceManager.getString(R.string.wallet_credit)
            Wallet.CacheWallet -> resourceManager.getString(R.string.wallet_cache)
        }
        viewState.setWalletNameTitle(walletName)
    }

    private fun saveEnrollment(enrollmentCategory: EnrollmentCategory, currency: Currency, amount: Long) {
        walletRepository.saveEnrollmentOperation(wallet, EnrollmentOperation(amount.toDouble(), enrollmentCategory, currency))
    }

    private fun saveExpense(expenseCategory: ExpenseCategory, currency: Currency, amount: Long) {
        walletRepository.saveExpenseOperation(wallet, ExpenseOperation(amount.toDouble(), expenseCategory, currency))
    }

}