package com.test.templatechooser.presentation.base;

import android.os.Parcelable;

import androidx.annotation.NonNull;

public interface BaseContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
    }

    interface Presenter<T extends View> {
        void setView(@NonNull T view);
        State getState();
        void restoreState(State state);
        void destroy();
    }

    interface State extends Parcelable {}
}
