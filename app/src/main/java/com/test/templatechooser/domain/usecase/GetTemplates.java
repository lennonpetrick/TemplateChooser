package com.test.templatechooser.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.templatechooser.di.qualifiers.UIScheduler;
import com.test.templatechooser.di.qualifiers.WorkScheduler;
import com.test.templatechooser.domain.TemplateRepository;
import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.domain.models.mapper.TemplateMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * This class is an implementation of {@link UseCase}
 * which returns a template list.
 */
public class GetTemplates extends UseCase<List<Template>, Void> {

    private final TemplateRepository mRepository;

    @Inject
    public GetTemplates(@NonNull @WorkScheduler Scheduler workThread,
                        @NonNull @UIScheduler Scheduler uiThread,
                        @NonNull CompositeDisposable disposables,
                        @NonNull TemplateRepository repository) {
        super(workThread, uiThread, disposables);
        mRepository = repository;
    }

    @Override
    protected Single<List<Template>> buildUseCase(@Nullable Void params) {
        return mRepository
                .fetchTemplates()
                .observeOn(getWorkThread())
                .flattenAsObservable(strings -> strings)
                .flatMapSingle(mRepository::getTemplate)
                .map(TemplateMapper::transformTemplate)
                .toList();
    }
}
