package ru.popov.bodya.howmoney.presentation.mvp.account

import com.arellomobile.mvp.InjectViewState
import io.reactivex.functions.Consumer
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.howmoney.domain.launch.LaunchInteractor
import ru.popov.bodya.howmoney.presentation.mvp.account.AccountView
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.BUDGET_SCREEN
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class AccountPresenter @Inject constructor(private val router: Router,
                                           private val rxSchedulersTransformer: RxSchedulersTransformer,
                                           private val launchInteractor: LaunchInteractor) : AppPresenter<AccountView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launchInteractor.getExchangeRate()
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer { router.replaceScreen(BUDGET_SCREEN) })
    }

    fun onBackPressed() {
        router.exit()
    }
}