package ru.popov.bodya.core.rx;


import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * @author popovbodya
 */

public class RxSchedulersStub implements RxSchedulers {

    @NonNull
    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.trampoline();
    }
}
