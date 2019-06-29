package com.test.templatechooser.di.modules;

import androidx.annotation.NonNull;

import com.test.templatechooser.presentation.templates.TemplatesContract;
import com.test.templatechooser.presentation.templates.TemplatesPresenter;
import com.test.templatechooser.presentation.templateview.TemplateViewContract;
import com.test.templatechooser.presentation.templateview.TemplateViewPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PresenterModule {

    @Binds
    abstract TemplatesContract.Presenter providesTemplatesPresenter(@NonNull TemplatesPresenter presenter);

    @Binds
    abstract TemplateViewContract.Presenter providesTemplateViewPresenter(@NonNull TemplateViewPresenter presenter);

}
