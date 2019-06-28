package com.test.templatechooser.data.repository.datasource.apiservice;

import com.test.templatechooser.data.entities.TemplateEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * This interface is a service used for getting template data from api using
 * retrofit. The framework uses this interface.
 */
public interface TemplateService {

    /**
     * Returns the templates urls from the api.
     *
     * @return A list of strings.
     **/
    @GET("api/published_designs/")
    Single<List<String>> fetchTemplates();

    /**
     * Gets a specific template from the api.
     *
     * @param url The template's url.
     * @return The template based on the path.
     **/
    @Headers({"Accept: application/json"})
    @GET
    Single<TemplateEntity> getTemplate(@Url String url);

}
