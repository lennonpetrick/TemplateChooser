package com.test.templatechooser.domain.models.mapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.templatechooser.data.entities.MetaEntity;
import com.test.templatechooser.data.entities.ScreenshotsEntity;
import com.test.templatechooser.data.entities.TemplateEntity;
import com.test.templatechooser.data.entities.VariationEntity;
import com.test.templatechooser.domain.models.Template;

import java.util.ArrayList;
import java.util.List;

public class TemplateMapper {

    @NonNull
    public static Template transformTemplate(@NonNull TemplateEntity entity) {
        Template model = new Template();
        model.setId(entity.getId());

        final String templateName = entity.getName();
        model.setName(templateName);

        MetaEntity metaEntity = entity.getMeta();
        if (metaEntity != null) {
            model.setColor(metaEntity.getColor());
        }

        ScreenshotsEntity screenshotsEntity = entity.getScreenshots();
        if (screenshotsEntity != null) {
            model.setPreviewUrl(screenshotsEntity.getUrl());
        }

        List<Template> variations = new ArrayList<>();
        variations.add(model);

        List<VariationEntity> variationEntities = entity.getVariations();
        if (variationEntities != null) {
            for (VariationEntity variationEntity : variationEntities) {
                variations.add(transformVariation(variationEntity, variations, templateName));
            }
        }

        model.setVariations(variations);
        return model;
    }

    private static Template transformVariation(@NonNull VariationEntity entity,
                                               @NonNull List<Template> variations,
                                               @Nullable String templateName) {
        Template model = new Template();
        model.setId(entity.getId());
        model.setName(templateName);
        model.setColor(entity.getIcon());

        ScreenshotsEntity screenshotsEntity = entity.getScreenshots();
        if (screenshotsEntity != null) {
            model.setPreviewUrl(screenshotsEntity.getUrl());
        }

        model.setVariations(variations);
        return model;
    }
}
