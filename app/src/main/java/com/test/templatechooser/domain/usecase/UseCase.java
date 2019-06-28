package com.test.templatechooser.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.templatechooser.utils.EspressoIdlingResource;

import java.util.Objects;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * This abstract class is an UseCase which represents an execution unit
 * for different cases.
 */
public abstract class UseCase<T, P> {

    private final Scheduler mWorkThread,
                            mUiThread;

    private final CompositeDisposable mDisposables;

    public UseCase(@NonNull Scheduler workThread,
                   @NonNull Scheduler uiThread,
                   @NonNull CompositeDisposable disposables) {
        this.mWorkThread = workThread;
        this.mUiThread = uiThread;
        this.mDisposables = disposables;
    }

    /**
     * Returns an observable which will be used when executing the current use case.
     *
     * @param params The parameters to execute the use case.
     */
    protected abstract Single<T> buildUseCase(@Nullable P params);

    /**
     * Disposes all observers.
     */
    public void dispose() {
        if (!mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    /**
     * Adds a disposable into {@link CompositeDisposable}.
     *
     * @param disposable The disposable from an observer.
     */
    private void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    /**
     * Returns the work thread.
     *
     * @return A {@link Scheduler}
     * */
    protected Scheduler getWorkThread() {
        return mWorkThread;
    }

    /**
     * Ensures the use case subclass which requires parameters
     * does not receive a null parameter.
     *
     * @throws NullPointerException if {@code params} is null
     */
    protected void ensureNotNull(P params) {
        Objects.requireNonNull(params,
                "The parameters for the " + getClass().getName() + " cannot be null");
    }

    /**
     * Runs the current use case.
     *
     * @param params The parameters to execute the current use case.
     * @return An observable of the execution to be subscribed.
     */
    public Single<T> execute(@Nullable P params) {
        return buildUseCase(params)
                .doOnSubscribe(disposable -> {
                    addDisposable(disposable);
                    EspressoIdlingResource.increment();
                })
                .doFinally(EspressoIdlingResource::decrement)
                .subscribeOn(mWorkThread)
                .observeOn(mUiThread);
    }
}
