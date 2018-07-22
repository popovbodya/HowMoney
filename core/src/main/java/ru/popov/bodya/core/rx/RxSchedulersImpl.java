package ru.popov.bodya.core.rx;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * @author popovbodya
 */

public class RxSchedulersImpl implements RxSchedulers {

    @Inject
    public RxSchedulersImpl() {
    }

    @NonNull
    @Override
    public Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }
}
