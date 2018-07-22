package ru.popov.bodya.howmoney.presentation.mvp.overview

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.popov.bodya.core.mvp.AppPresenter
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.howmoney.di.overview.OverviewScope
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.inject.Inject

/**
 *  @author popovbodya
 */
@InjectViewState
class OverviewPresenter @Inject constructor(private val rxSchedulersTransformer: RxSchedulersTransformer) : AppPresenter<OverviewView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Observable
                .timer(5L, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { Log.e("bodya", rxSchedulersTransformer.toString()) }
                .subscribe({ viewState.showNewData() }, { viewState.showError() })
    }
}