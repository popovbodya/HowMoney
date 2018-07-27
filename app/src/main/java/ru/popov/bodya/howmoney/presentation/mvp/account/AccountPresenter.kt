package ru.popov.bodya.howmoney.presentation.mvp.account

import com.arellomobile.mvp.InjectViewState
import io.reactivex.functions.Consumer
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.howmoney.domain.account.interactors.CurrencyInteractor
import ru.popov.bodya.howmoney.domain.account.models.Currency
import ru.popov.bodya.howmoney.presentation.ui.global.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class AccountPresenter @Inject constructor(
        private val currencyInteractor: CurrencyInteractor,
        private val rxSchedulersTransformer: RxSchedulersTransformer,
        private val router: Router
) : AppPresenter<AccountView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        currencyInteractor.getCurrentBalance(Currency.RUB)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer { viewState.showRUBAmount(it) })

        currencyInteractor.getCurrentBalance(Currency.USD)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer { viewState.showUSDAmount(it) })
    }

    fun onAboutMenuItemClick() {
        router.navigateTo(Screens.ABOUT_SCREEN)
    }

    fun onSettingsMenuItemClick() {
        router.navigateTo(Screens.SETTINGS_SCREEN)
    }

}