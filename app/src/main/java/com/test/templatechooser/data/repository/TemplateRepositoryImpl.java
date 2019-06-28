package com.test.templatechooser.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.test.templatechooser.data.entities.TemplateEntity;
import com.test.templatechooser.data.repository.datasource.RemoteTemplateDataSource;
import com.test.templatechooser.domain.TemplateRepository;
import com.test.templatechooser.exceptions.NoConnectionException;
import com.test.templatechooser.utils.ConnectionUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * This class is a implementation of {@link TemplateRepository} and it is responsible for
 * working with template data from local and remote data sources. In this case, only remote.
 * */
@WorkerThread
public class TemplateRepositoryImpl implements TemplateRepository {

    private final RemoteTemplateDataSource mRemoteDataSource;

    @Inject
    public TemplateRepositoryImpl(@NonNull RemoteTemplateDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }

    /**
     * Returns the templates urls from the data source.
     * When getting data from remote data source and no connection is available,
     * it returns {@link NoConnectionException}.
     *
     * @return A list of strings.
     **/
    @Override
    public Single<List<String>> fetchTemplates() {
        return mRemoteDataSource
                .fetchTemplates()
                .onErrorResumeNext(throwable -> {
                    if (ConnectionUtils.noInternetAvailable(throwable)) {
                        return Single.error(new NoConnectionException());
                    }

                    return Single.error(throwable);
                });
    }

    /**
     * Gets a specific template from the data source.
     * When getting data from remote data source and no connection is available,
     * it returns {@link NoConnectionException}.
     *
     * @param url The template's url.
     * @return The template based on the path.
     **/
    @Override
    public Single<TemplateEntity> getTemplate(@NonNull String url) {
        return mRemoteDataSource
                .getTemplate(url)
                .onErrorResumeNext(throwable -> {
                    if (ConnectionUtils.noInternetAvailable(throwable)) {
                        return Single.error(new NoConnectionException());
                    }

                    return Single.error(throwable);
                });
    }
}
