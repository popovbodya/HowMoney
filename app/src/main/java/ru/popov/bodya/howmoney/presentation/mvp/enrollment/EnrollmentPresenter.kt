package ru.popov.bodya.howmoney.presentation.mvp.enrollment

import com.arellomobile.mvp.InjectViewState
import io.reactivex.functions.Consumer
import ru.popov.bodya.core.extensions.connect
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.howmoney.domain.enrollment.interactors.EnrollmentInteractor
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.terrakok.cicerone.Router
import javax.inject.Inject


/**
 *  @author popovbodya
 */
@InjectViewState
class EnrollmentPresenter @Inject constructor(private val enrollmentInteractor: EnrollmentInteractor,
                                              private val rxSchedulersTransformer: RxSchedulersTransformer,
                                              private val router: Router) : AppPresenter<EnrollmentView>() {

    private lateinit var wallet: Wallet

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        enrollmentInteractor.getEnrollmentCategoryMap(wallet)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer {
                    val enrollmentCategoryMap = it
                    enrollmentInteractor.calculateFinalEnrollmentBalance(it)
                            .compose(rxSchedulersTransformer.computationToMainTransformerSingle())
                            .subscribe(Consumer { viewState.showDiagram(enrollmentCategoryMap, it) })
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