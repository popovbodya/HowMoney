package ru.popov.bodya.core.rx;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;

/**
 * @author popovbodya
 */

public class RxSchedulersTransformerImpl implements RxSchedulersTransformer {

    private final RxSchedulers mRxSchedulers;

    public RxSchedulersTransformerImpl(RxSchedulers rxSchedulers) {
        mRxSchedulers = rxSchedulers;
    }

    @Override
    public <T> ObservableTransformer<T, T> getIOToMainTransformer() {
        return upstream -> upstream
                .subscribeOn(mRxSchedulers.getIOScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler());
    }

    @Override
    public <T> SingleTransformer<T, T> getIOToMainTransformerSingle() {
        return upstream -> upstream
                .subscribeOn(mRxSchedulers.getIOScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler());
    }

    @Override
    public <T> MaybeTransformer<T, T> getIOToMainTransformerMaybe() {
        return upstream -> upstream
                .subscribeOn(mRxSchedulers.getIOScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler());
    }

    @Override
    public CompletableTransformer getIOToMainTransformerCompletable() {
        return upstream -> upstream
                .subscribeOn(mRxSchedulers.getIOScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler());
    }

    @Override
    public <T> FlowableTransformer<T, T> getIOToMainTransformerFlowable() {
        return upstream -> upstream
                .subscribeOn(mRxSchedulers.getIOScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler());
    }

    @Override
    public <T> ObservableTransformer<T, T> getComputationToMainTransformer() {
        return upstream -> upstream
                .subscribeOn(mRxSchedulers.getComputationScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler());
    }

    @Override
    public <T> SingleTransformer<T, T> getComputationToMainTransformerSingle() {
        return upstream -> upstream
                .subscribeOn(mRxSchedulers.getComputationScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler());
    }
}
