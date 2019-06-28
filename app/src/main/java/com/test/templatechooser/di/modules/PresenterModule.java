package com.test.templatechooser.di.modules;

import androidx.annotation.NonNull;

import com.test.templatechooser.presentation.TemplatesContract;
import com.test.templatechooser.presentation.TemplatesPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PresenterModule {

    @Binds
    abstract TemplatesContract.Presenter providesTemplatesPresenter(@NonNull TemplatesPresenter presenter);

}
