package com.test.templatechooser.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.templatechooser.di.qualifiers.UIScheduler;
import com.test.templatechooser.di.qualifiers.WorkScheduler;
import com.test.templatechooser.domain.TemplateRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * This class is an implementation of {@link UseCase}
 * which returns a list of templates urls.
 */
public class FetchTemplates extends UseCase<List<String>, Void> {

    private final TemplateRepository mRepository;

    @Inject
    public FetchTemplates(@NonNull @WorkScheduler Scheduler workThread,
                          @NonNull @UIScheduler Scheduler uiThread,
                          @NonNull CompositeDisposable disposables,
                          @NonNull TemplateRepository repository) {
        super(workThread, uiThread, disposables);
        mRepository = repository;
    }

    @Override
    protected Single<List<String>> buildUseCase(@Nullable Void params) {
        return mRepository.fetchTemplates();
    }
}
