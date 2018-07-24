package ru.popov.bodya.core.mvp

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

/**
 * @author popovbodya
 */

open class AppPresenter<View : AppView> : MvpPresenter<View>() {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
