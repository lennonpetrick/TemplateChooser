package com.test.templatechooser.presentation.templateview;

import androidx.annotation.NonNull;

import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.presentation.base.BaseContract;

public interface TemplateViewContract {

    interface View extends BaseContract.View {
        void displayTemplate(@NonNull Template template);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getTemplate(String url);
    }

}
