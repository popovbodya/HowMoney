package ru.popov.bodya.howmoney.presentation.mvp.account

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.howmoney.domain.account.CurrencyInteractor
import ru.popov.bodya.howmoney.domain.global.models.Currency
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

        val disposable = Single.zip(
                currencyInteractor.getCurrentCurrencyAmount(Currency.RUB),
                currencyInteractor.getCurrentCurrencyAmount(Currency.USD),
                BiFunction<Long, Long, Pair<Long, Long>> { rubAmount, usdAmount -> Pair(rubAmount, usdAmount) })
                .compose(rxSchedulersTransformer.getIOToMainTransformerSingle())
                .subscribe { pair ->
                    viewState.showRUBAmount(pair.first)
                    viewState.showUSDAmount(pair.second)
                }

        connect(disposable)
    }

    fun onAboutMenuItemClick() {
        router.navigateTo(Screens.ABOUT_SCREEN)
    }

    fun onSettingsMenuItemClick() {
        router.navigateTo(Screens.SETTINGS_SCREEN)
    }

}