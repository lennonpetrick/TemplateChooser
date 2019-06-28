package com.test.templatechooser.presentation;

import androidx.annotation.NonNull;

import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.domain.usecase.GetTemplates;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class TemplatesPresenter implements TemplatesContract.Presenter {

    private final GetTemplates mGetTemplates;

    private TemplatesContract.View mView;

    @Inject
    public TemplatesPresenter(@NonNull GetTemplates useCase) {
        mGetTemplates = useCase;
    }

    @Override
    public void setView(TemplatesContract.View view) {
        mView = view;
    }

    @Override
    public TemplatesContract.State getState() {
        return null;
    }

    @Override
    public void restoreState(TemplatesContract.State state) {

    }

    @Override
    public void destroy() {
        mView = null;
    }

    @Override
    public void loadTemplates() {
        getTemplates();
    }

    private void getTemplates() {
        mView.showLoading();
        mGetTemplates
                .execute(null)
                .doFinally(() -> mView.hideLoading())
                .subscribe(new DisposableSingleObserver<List<Template>>() {
                    @Override
                    public void onSuccess(List<Template> templates) {
                        mView.showTemplates(templates);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }
                });
    }
}
