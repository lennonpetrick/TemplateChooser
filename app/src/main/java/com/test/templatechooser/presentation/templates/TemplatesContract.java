package com.test.templatechooser.presentation.templates;

import androidx.annotation.NonNull;

import com.test.templatechooser.presentation.base.BaseContract;

import java.util.List;

public interface TemplatesContract {

    interface View extends BaseContract.View {;
        void loadTemplates(@NonNull List<String> templatesUrls);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void fetchTemplates();
    }

}
