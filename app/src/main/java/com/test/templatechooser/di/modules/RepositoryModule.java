package com.test.templatechooser.di.modules;

import androidx.annotation.NonNull;

import com.test.templatechooser.data.repository.TemplateRepositoryImpl;
import com.test.templatechooser.domain.TemplateRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract TemplateRepository providesTemplateRepository(@NonNull TemplateRepositoryImpl repository);

}
