package com.test.templatechooser.di.modules;

import com.test.templatechooser.presentation.templates.TemplatesActivity;
import com.test.templatechooser.presentation.templateview.TemplateViewFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ViewModule {

    @ContributesAndroidInjector(modules = PresenterModule.class)
    abstract TemplatesActivity templatesActivity();

    @ContributesAndroidInjector(modules = PresenterModule.class)
    abstract TemplateViewFragment templateViewFragment();

}
