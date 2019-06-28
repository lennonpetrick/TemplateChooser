package com.test.templatechooser.data.repository.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.test.templatechooser.data.entities.TemplateEntity;
import com.test.templatechooser.data.repository.datasource.apiservice.TemplateService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * This data source is used to fetch template data from the api.
 * */
@WorkerThread
public class RemoteTemplateDataSource {

    private final TemplateService mSevice;

    @Inject
    public RemoteTemplateDataSource(@NonNull TemplateService service) {
        this.mSevice = service;
    }

    /**
     * Returns the templates urls from the remote service.
     *
     * @return A list of strings.
     **/
    public Single<List<String>> fetchTemplates() {
        return mSevice.fetchTemplates();
    }

    /**
     * Gets a specific template from the remote service.
     *
     * @param url The template's url.
     * @return The template based on the path.
     **/
    public Single<TemplateEntity> getTemplate(@NonNull String url) {
        return mSevice.getTemplate(url);
    }

}
