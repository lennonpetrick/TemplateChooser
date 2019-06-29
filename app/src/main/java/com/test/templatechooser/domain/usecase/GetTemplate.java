package com.test.templatechooser.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.templatechooser.di.qualifiers.UIScheduler;
import com.test.templatechooser.di.qualifiers.WorkScheduler;
import com.test.templatechooser.domain.TemplateRepository;
import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.domain.models.mapper.TemplateMapper;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * This class is an implementation of {@link UseCase}
 * which returns a specific template based on the {@link Params}.
 */
public class GetTemplate extends UseCase<Template, GetTemplate.Params> {

    private final TemplateRepository mRepository;

    @Inject
    public GetTemplate(@NonNull @WorkScheduler Scheduler workThread,
                       @NonNull @UIScheduler Scheduler uiThread,
                       @NonNull CompositeDisposable disposables,
                       @NonNull TemplateRepository repository) {
        super(workThread, uiThread, disposables);
        mRepository = repository;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected Single<Template> buildUseCase(@Nullable Params params) {
        ensureNotNull(params);
        return mRepository
                .getTemplate(params.url)
                .map(TemplateMapper::transformTemplate);
    }

    public static final class Params {

        private final String url;

        private Params(String url) {
            this.url = url;
        }

        public static Params forUrl(String url) {
            return new Params(url);
        }
    }
}
