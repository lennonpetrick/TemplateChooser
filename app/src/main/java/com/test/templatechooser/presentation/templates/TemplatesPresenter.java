package com.test.templatechooser.presentation.templates;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.test.templatechooser.domain.usecase.FetchTemplates;
import com.test.templatechooser.presentation.base.BaseContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class TemplatesPresenter implements TemplatesContract.Presenter {

    private final FetchTemplates mFetchTemplates;
    private TemplatesContract.View mView;
    private List<String> mTemplatesUrls;

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
        return new State(this);
    }

    @Override
    public void restoreState(BaseContract.State state) {
        ((State) state).restore(this);
    }

    @Override
    public void destroy() {
        mFetchTemplates.dispose();
        mView = null;
        mTemplatesUrls = null;
    }

    @Override
    public void fetchTemplates() {
        if (mTemplatesUrls != null) {
            loadTemplates(mTemplatesUrls);
        } else {
            getTemplates();
        }
    }

    private void getTemplates() {
        showLoading();
        mFetchTemplates
                .execute(null)
                .doFinally(this::hideLoading)
                .subscribe(new DisposableSingleObserver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> templatesUrls) {
                        mTemplatesUrls = templatesUrls;
                        loadTemplates(templatesUrls);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e.getMessage());
                    }
                });
    }

    private void loadTemplates(List<String> templatesUrls) {
        if (mView == null)
            return;

        mView.loadTemplates(templatesUrls);
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

        private List<String> templatesUrls;

        private State(TemplatesPresenter presenter) {
            this.templatesUrls = presenter.mTemplatesUrls;
        }

        private State(Parcel in) {
            in.readList(templatesUrls, String.class.getClassLoader());
        }

        private void restore(TemplatesPresenter presenter) {
            presenter.mTemplatesUrls = templatesUrls;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(templatesUrls);
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
