package com.test.templatechooser.presentation.templateview;

import androidx.annotation.NonNull;

import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.domain.usecase.GetTemplate;
import com.test.templatechooser.presentation.base.BaseContract;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class TemplateViewPresenter implements TemplateViewContract.Presenter {

    private final GetTemplate mGetTemplate;

    private TemplateViewContract.View mView;

    @Inject
    public TemplateViewPresenter(@NonNull GetTemplate useCase) {
        mGetTemplate = useCase;
    }

    @Override
    public void setView(@NonNull TemplateViewContract.View view) {
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
        mView = null;
    }

    @Override
    public void getTemplate(String url) {
        showLoading();
        mGetTemplate
                .execute(GetTemplate.Params.forUrl(url))
                .doFinally(this::hideLoading)
                .subscribe(new DisposableSingleObserver<Template>() {
                    @Override
                    public void onSuccess(Template template) {
                        displayTemplate(template);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e.getMessage());
                    }
                });
    }

    private void displayTemplate(Template template) {
        if (mView == null)
            return;

        mView.displayTemplate(template);
    }

    private void showLoading() {
        if (mView == null)
            return;

        mView.showLoading();
    }

    private void hideLoading() {
        if (mView == null)
            return;

        mView.hideLoading();
    }

    private void showError(String message) {
        if (mView == null)
            return;

        mView.showError(message);
    }
}
