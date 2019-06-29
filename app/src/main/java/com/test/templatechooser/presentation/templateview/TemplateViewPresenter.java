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
        mView.showLoading();
        mGetTemplate
                .execute(GetTemplate.Params.forUrl(url))
                .doFinally(() -> mView.hideLoading())
                .subscribe(new DisposableSingleObserver<Template>() {
                    @Override
                    public void onSuccess(Template template) {
                        mView.displayTemplate(template);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }
                });
    }
}
