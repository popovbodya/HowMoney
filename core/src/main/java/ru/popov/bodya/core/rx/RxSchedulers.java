package ru.popov.bodya.core.rx;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/**
 * @author popovbodya
 */

public interface RxSchedulers {

    @NonNull
    Scheduler getMainThreadScheduler();

    @NonNull
    Scheduler getIOScheduler();

    @NonNull
    Scheduler getComputationScheduler();

}
