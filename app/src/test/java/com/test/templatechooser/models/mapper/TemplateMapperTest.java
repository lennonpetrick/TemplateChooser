package com.test.templatechooser.models.mapper;

import com.test.templatechooser.BaseTest;
import com.test.templatechooser.data.TemplateEntity;
import com.test.templatechooser.data.VariationEntity;
import com.test.templatechooser.models.Template;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TemplateMapperTest extends BaseTest {

    @Test
    public void transformTemplate() throws IOException {
        TemplateEntity entity = createObjectFromFile(
                "Template.json", TemplateEntity.class);

        Template template = TemplateMapper.transformTemplate(entity);

        assertThat(template.getId(), is(entity.getId()));
        assertThat(template.getName(), is(entity.getName()));
        assertThat(template.getColor(), is(entity.getMeta().getColor()));
        assertThat(template.getPreviewUrl(), is(entity.getScreenshots().getUrl()));

        List<Template> variations = template.getVariations();
        assertThat(variations.get(0), is(template));
        assertVariations(variations.subList(1, variations.size()), entity.getVariations());
    }

    private void assertVariations(List<Template> templates, List<VariationEntity> entities) {
        final int size = templates.size();
        for (int i = 0; i < size; i++) {
            Template template = templates.get(i);
            VariationEntity entity = entities.get(i);
            List<Template> variations = template.getVariations();

            assertThat(template.getName(), is(variations.get(0).getName()));
            assertThat(template.getId(), is(entity.getId()));
            assertThat(template.getColor(), is(entity.getIcon()));
            assertThat(template.getPreviewUrl(), is(entity.getScreenshots().getUrl()));

            assertThat(variations.get(i + 1), is(template));
        }
    }

}