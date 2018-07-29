package ru.popov.bodya.howmoney.presentation.mvp.expense

import com.arellomobile.mvp.InjectViewState
import ru.popov.bodya.core.mvp.AppPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class ExpensePresenter @Inject constructor(private val router: Router) : AppPresenter<ExpenseView>() {

    fun onUpButtonPressed() {
        router.exit()
    }
}