package com.test.templatechooser.presentation.templateview;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.domain.usecase.GetTemplate;
import com.test.templatechooser.presentation.base.BaseContract;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class TemplateViewPresenter implements TemplateViewContract.Presenter {

    private final GetTemplate mGetTemplate;

    private TemplateViewContract.View mView;
    private Template mTemplate;
    private String mTemplateUrl;

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
        return new State(this);
    }

    @Override
    public void restoreState(BaseContract.State state) {
        ((State) state).restore(this);
    }

    @Override
    public void destroy() {
        mView = null;
    }

    @Override
    public void setUrl(String url) {
        mTemplateUrl = url;
    }

    @Override
    public void onViewVisible() {
        if (mView != null
                && mTemplate != null) {
            mView.notifyTemplateChanged(mTemplate);
        }
    }

    @Override
    public void loadTemplate() {
        if (mTemplate != null) {
            displayTemplate(mTemplate);
        } else {
            getTemplate();
        }
    }

    @Override
    public void chooseTemplate() {
        if (mView != null
                && mTemplate != null) {
            mView.notifyTemplateChosen(mTemplate);
        }
    }

    @Override
    public void selectVariation(Template template) {
        mTemplate = template;
        displayTemplate(template);
    }

    private void getTemplate() {
        showLoading();
        mGetTemplate
                .execute(GetTemplate.Params.forUrl(mTemplateUrl))
                .doFinally(this::hideLoading)
                .subscribe(new DisposableSingleObserver<Template>() {
                    @Override
                    public void onSuccess(Template template) {
                        mTemplate = template;
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

    /**
     * Internal class used to expose this presenter's state to its view when
     * onSaveInstanceState is called.
     * */
    private static class State implements BaseContract.State {

        private Template template;
        private String url;

        private State(TemplateViewPresenter presenter) {
            this.template = presenter.mTemplate;
            this.url = presenter.mTemplateUrl;
        }

        private State(Parcel in) {
            template = in.readParcelable(Template.class.getClassLoader());
            url = in.readString();
        }

        private void restore(TemplateViewPresenter presenter) {
            presenter.mTemplate = template;
            presenter.mTemplateUrl = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(template, 0);
            dest.writeString(url);
        }

        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };
    }
}
