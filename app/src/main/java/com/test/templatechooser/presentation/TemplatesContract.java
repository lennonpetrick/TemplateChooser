package com.test.templatechooser.presentation;

import android.os.Parcelable;

import com.test.templatechooser.models.Template;

import java.util.List;

public interface TemplatesContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showTemplates(List<Template> templates);
    }

    interface Presenter {
        void setView(View view);
        State getState();
        void restoreState(State state);
        void destroy();
        void loadTemplates();
    }

    interface State extends Parcelable {}
}
