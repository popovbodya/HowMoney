package ru.popov.bodya.core.rx;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author popovbodya
 */
public class RxSchedulersTransformerImplTest {

    private RxSchedulersTransformer mRxSchedulersTransformer;
    private RxSchedulers mRxSchedulers;

    @Before
    public void setUp() {
        mRxSchedulers = spy(new RxSchedulersStub());
        mRxSchedulersTransformer = new RxSchedulersTransformerImpl(mRxSchedulers);
    }

    @Test
    public void testGetIOToMainTransformer() {
        Observable
                .just(1)
                .compose(mRxSchedulersTransformer.getIOToMainTransformer());
        verifyRxSchedulersIOMain();
    }

    @Test
    public void testGetIOToMainTransformerSingle() {
        Single
                .just(1)
                .compose(mRxSchedulersTransformer.getIOToMainTransformerSingle());
        verifyRxSchedulersIOMain();
    }

    @Test
    public void testGetIOToMainTransformerMaybe() {
        Maybe
                .just(1)
                .compose(mRxSchedulersTransformer.getIOToMainTransformerMaybe());
        verifyRxSchedulersIOMain();
    }

    @Test
    public void testGetIOToMainTransformerCompletable() {
        Completable
                .complete()
                .compose(mRxSchedulersTransformer.getIOToMainTransformerCompletable());
        verifyRxSchedulersIOMain();
    }

    @Test
    public void testGetIOToMainTransformerFlowable() {
        Flowable
                .just(1)
                .compose(mRxSchedulersTransformer.getIOToMainTransformerFlowable());
        verifyRxSchedulersIOMain();
    }

    @Test
    public void testGetComputationToMainTransformerSingle() {
        Single
                .just(1)
                .compose(mRxSchedulersTransformer.getComputationToMainTransformerSingle());
        verifyRxSchedulersComputationMain();
    }


    private void verifyRxSchedulersIOMain() {
        verify(mRxSchedulers).getIOScheduler();
        verify(mRxSchedulers).getMainThreadScheduler();
        verifyNoMoreInteractions(mRxSchedulers);
    }

    private void verifyRxSchedulersComputationMain() {
        verify(mRxSchedulers).getComputationScheduler();
        verify(mRxSchedulers).getMainThreadScheduler();
        verifyNoMoreInteractions(mRxSchedulers);
    }

}