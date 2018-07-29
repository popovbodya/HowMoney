package ru.popov.bodya.howmoney.presentation.mvp.enrollment

import com.arellomobile.mvp.InjectViewState
import ru.popov.bodya.core.mvp.AppPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class EnrollmentPresenter @Inject constructor(private val router: Router) : AppPresenter<EnrollmentView>() {

    fun onUpButtonPressed() {
        router.exit()
    }
}