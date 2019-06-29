package com.test.templatechooser.presentation.templates;

import androidx.annotation.NonNull;

import com.test.templatechooser.domain.usecase.FetchTemplates;
import com.test.templatechooser.presentation.base.BaseContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class TemplatesPresenter implements TemplatesContract.Presenter {

    private final FetchTemplates mFetchTemplates;

    private TemplatesContract.View mView;

    @Inject
    public TemplatesPresenter(@NonNull FetchTemplates useCase) {
        mFetchTemplates = useCase;
    }

    @Override
    public void setView(@NonNull TemplatesContract.View view) {
        mView = view;
    }

    @Override
    public BaseContract.State getState() {
        return null;
    }

    @Override
    public void restoreState(BaseContract.State state) {

    }

    @Override
    public void destroy() {
        mFetchTemplates.dispose();
        mView = null;
    }

    @Override
    public void fetchTemplates() {
        getTemplates();
    }

    private void getTemplates() {
        mView.showLoading();
        mFetchTemplates
                .execute(null)
                .doFinally(() -> mView.hideLoading())
                .subscribe(new DisposableSingleObserver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> templatesUrls) {
                        mView.loadTemplates(templatesUrls);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }
                });
    }
}
