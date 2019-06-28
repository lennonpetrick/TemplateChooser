package com.test.templatechooser.domain;

import androidx.annotation.NonNull;

import com.test.templatechooser.data.entities.TemplateEntity;

import java.util.List;

import io.reactivex.Single;

public interface TemplateRepository {
    Single<List<String>> fetchTemplates();
    Single<TemplateEntity> getTemplate(@NonNull String path);
}
