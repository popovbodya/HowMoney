package ru.popov.bodya.howmoney.presentation.mvp.budget

import com.arellomobile.mvp.InjectViewState
import io.reactivex.functions.Consumer
import ru.popov.bodya.core.extensions.connect
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.howmoney.domain.enrollment.interactors.EnrollmentInteractor
import ru.popov.bodya.howmoney.domain.expense.interactor.ExpenseInteractor
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.popov.bodya.howmoney.presentation.ui.common.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class BudgetPresenter @Inject constructor(
        private val enrollmentInteractor: EnrollmentInteractor,
        private val expenseInteractor: ExpenseInteractor,
        private val rxSchedulersTransformer: RxSchedulersTransformer,
        private val router: Router
) : AppPresenter<BudgetView>() {

    private var currentWallet: Wallet = Wallet.DebitWallet

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        enrollmentInteractor.getEnrollmentCategoryMap(Wallet.DebitWallet)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer {
                    enrollmentInteractor.calculateFinalEnrollmentBalance(it)
                            .compose(rxSchedulersTransformer.computationToMainTransformerSingle())
                            .subscribe(Consumer { viewState.showEnrollmentBalance(it) })
                })
                .connect(compositeDisposable)

        expenseInteractor.getExpenseCategoryMap(Wallet.CreditWallet)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer {
                    expenseInteractor.calculateFinalExpenseBalance(it)
                            .compose(rxSchedulersTransformer.computationToMainTransformerSingle())
                            .subscribe(Consumer { viewState.showExpenseBalance(it) })
                })
                .connect(compositeDisposable)

    }

    fun onWalletChanged(wallet: Wallet) {
        currentWallet = wallet
    }

    fun onEnrollmentBlockClick() {
        router.navigateTo(Screens.ENROLLMENT_SCREEN, currentWallet)
    }

    fun onExpenseBlockClick() {
        router.navigateTo(Screens.EXPENSE_SCREEN, currentWallet)
    }

    fun onAboutMenuItemClick() {
        router.navigateTo(Screens.ABOUT_SCREEN)
    }

    fun onSettingsMenuItemClick() {
        router.navigateTo(Screens.SETTINGS_SCREEN)
    }
}