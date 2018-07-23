package ru.popov.bodya.howmoney.presentation.mvp.launch

import com.arellomobile.mvp.InjectViewState
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.howmoney.presentation.ui.global.Screens.ACCOUNT_SCREEN
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class LaunchPresenter @Inject constructor(private val router: Router) : AppPresenter<LaunchView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(ACCOUNT_SCREEN)
    }

    fun onBackPressed() {
        router.exit()
    }

}