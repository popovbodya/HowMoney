package ru.popov.bodya.howmoney.presentation.mvp.expense

import com.arellomobile.mvp.InjectViewState
import io.reactivex.functions.Consumer
import ru.popov.bodya.core.extensions.connect
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.howmoney.domain.expense.interactor.ExpenseInteractor
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class ExpensePresenter @Inject constructor(private val router: Router,
                                           private val rxSchedulersTransformer: RxSchedulersTransformer,
                                           private val expenseInteractor: ExpenseInteractor) : AppPresenter<ExpenseView>() {

    private lateinit var wallet: Wallet

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        expenseInteractor.getExpenseCategoryMap(wallet)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer {
                    val expenseCategoryMap = it
                    expenseInteractor.calculateFinalExpenseBalance(it)
                            .compose(rxSchedulersTransformer.computationToMainTransformerSingle())
                            .subscribe(Consumer { viewState.showDiagram(expenseCategoryMap, it) })
                })
                .connect(compositeDisposable)
    }

    fun initPresenterParams(wallet: Wallet) {
        this.wallet = wallet
    }

    fun onUpButtonPressed() {
        router.exit()
    }
}