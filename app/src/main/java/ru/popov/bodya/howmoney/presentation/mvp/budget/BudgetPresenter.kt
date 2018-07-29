package ru.popov.bodya.howmoney.presentation.mvp.budget

import com.arellomobile.mvp.InjectViewState
import io.reactivex.functions.Consumer
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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        enrollmentInteractor.getEnrollmentOperationList(Wallet.DebitWallet)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer {
                    enrollmentInteractor.calculateBalanceOfEnrollmentList(it)
                            .compose(rxSchedulersTransformer.computationToMainTransformerSingle())
                            .subscribe(Consumer { viewState.showEnrollmentBalance(it) })
                })

        expenseInteractor.getExpenseOperationList(Wallet.CreditWallet)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer {
                    expenseInteractor.calculateBalanceOfExpenseList(it)
                            .compose(rxSchedulersTransformer.computationToMainTransformerSingle())
                            .subscribe(Consumer { viewState.showExpenseBalance(it) })
                })
    }

    fun onAboutMenuItemClick() {
        router.navigateTo(Screens.ABOUT_SCREEN)
    }

    fun onSettingsMenuItemClick() {
        router.navigateTo(Screens.SETTINGS_SCREEN)
    }

}