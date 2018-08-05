package ru.popov.bodya.howmoney.presentation.mvp.settings

import com.arellomobile.mvp.InjectViewState
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.howmoney.presentation.ui.common.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SettingsPresenter @Inject constructor(
        private val router: Router
) : AppPresenter<SettingsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun onBackPressed() {
        router.navigateTo(Screens.WALLET_SCREEN)
    }

}