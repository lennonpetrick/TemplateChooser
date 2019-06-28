package com.test.templatechooser.di;

import com.test.templatechooser.MainApplication;
import com.test.templatechooser.di.modules.ActivityModule;
import com.test.templatechooser.di.modules.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        PresenterModule.class
})
public interface AppComponent extends AndroidInjector<MainApplication> {
    void inject(MainApplication application);
}