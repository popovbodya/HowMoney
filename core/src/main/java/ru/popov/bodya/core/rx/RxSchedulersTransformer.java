package ru.popov.bodya.core.rx;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;

/**
 * @author popovbodya
 */

public interface RxSchedulersTransformer {

    <T> ObservableTransformer<T, T> getIOToMainTransformer();

    <T> SingleTransformer<T, T> getIOToMainTransformerSingle();

    <T> MaybeTransformer<T, T> getIOToMainTransformerMaybe();

    CompletableTransformer getIOToMainTransformerCompletable();

    <T> FlowableTransformer<T, T> getIOToMainTransformerFlowable();

    <T> ObservableTransformer<T, T> getComputationToMainTransformer();

    <T> SingleTransformer<T, T> getComputationToMainTransformerSingle();

}
