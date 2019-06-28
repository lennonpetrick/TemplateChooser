package com.test.templatechooser.di.modules;

import com.test.templatechooser.presentation.TemplatesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = PresenterModule.class)
    abstract TemplatesActivity templatesActivity();

}
