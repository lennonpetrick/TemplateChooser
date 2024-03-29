package com.test.templatechooser.di;

import com.test.templatechooser.MainApplication;
import com.test.templatechooser.di.modules.ViewModule;
import com.test.templatechooser.di.modules.NetworkModule;
import com.test.templatechooser.di.modules.PresenterModule;
import com.test.templatechooser.di.modules.RepositoryModule;
import com.test.templatechooser.di.modules.UseCaseModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ViewModule.class,
        PresenterModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        UseCaseModule.class
})
public interface AppComponent extends AndroidInjector<MainApplication> {
    void inject(MainApplication application);
}