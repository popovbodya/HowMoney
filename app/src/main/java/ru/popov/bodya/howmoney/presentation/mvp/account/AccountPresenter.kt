package ru.popov.bodya.howmoney.presentation.mvp.account

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import ru.popov.bodya.core.extensions.connect
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
        Observable.merge(
                currencyInteractor.getCurrentCurrencyAmount(Currency.RUB).toObservable(),
                currencyInteractor.getCurrentCurrencyAmount(Currency.USD).toObservable())
                .compose(rxSchedulersTransformer.ioToMainTransformer())
                .subscribe {
                    when (it.currency) {
                        Currency.RUB -> viewState.showRUBAmount(it.amount)
                        Currency.USD -> viewState.showUSDAmount(it.amount)
                    }
                }
                .connect(compositeDisposable)
    }

    fun onAboutMenuItemClick() {
        router.navigateTo(Screens.ABOUT_SCREEN)
    }

    fun onSettingsMenuItemClick() {
        router.navigateTo(Screens.SETTINGS_SCREEN)
    }

}