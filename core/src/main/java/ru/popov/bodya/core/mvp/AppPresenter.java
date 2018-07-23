package ru.popov.bodya.core.mvp;

import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author popovbodya
 */

public class AppPresenter<View extends AppView> extends MvpPresenter<View> {

    private final CompositeDisposable mCompositeDisposable;

    public AppPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
        super.onDestroy();
    }

    protected void connect(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }
}
