package com.test.templatechooser.di.modules;

import androidx.annotation.NonNull;

import com.test.templatechooser.BuildConfig;
import com.test.templatechooser.data.repository.datasource.apiservice.TemplateService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Reusable
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(@NonNull HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Reusable
    RxJava2CallAdapterFactory providesRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Reusable
    GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(@NonNull OkHttpClient client,
                              @NonNull RxJava2CallAdapterFactory rxAdapter,
                              @NonNull GsonConverterFactory jsonConverter) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(jsonConverter)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    TemplateService providesTemplateService(@NonNull Retrofit retrofit) {
        return retrofit.create(TemplateService.class);
    }

}
