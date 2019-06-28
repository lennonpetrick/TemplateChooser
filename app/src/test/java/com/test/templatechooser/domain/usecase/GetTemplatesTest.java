package com.test.templatechooser.domain.usecase;

import com.test.templatechooser.BaseTest;
import com.test.templatechooser.data.entities.TemplateEntity;
import com.test.templatechooser.domain.TemplateRepository;
import com.test.templatechooser.domain.models.Template;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetTemplatesTest extends BaseTest {

    private GetTemplates mUseCase;
    private CompositeDisposable mDisposable;
    private TemplateEntity mEntity;

    @Mock
    private TemplateRepository mRepository;

    @Before
    public void setUp() throws IOException {
        mEntity = createObjectFromFile(
                "Template.json", TemplateEntity.class);

        when(mRepository.fetchTemplates())
                .thenReturn(Observable.just(""));
        when(mRepository.getTemplate(anyString()))
                .thenReturn(Single.just(mEntity));

        Scheduler scheduler = Schedulers.trampoline();
        mDisposable = new CompositeDisposable();
        mUseCase = new GetTemplates(scheduler, scheduler, mDisposable, mRepository);
    }

    @Test
    public void getTemplates() {
        mUseCase.execute(null)
                .test()
                .assertValue(templates -> {
                    if (templates.size() <= 0)
                        return false;

                    Template template = templates.get(0);
                    return is(template.getId()).matches(mEntity.getId())
                            && is(template.getName()).matches(mEntity.getName())
                            && is(template.getColor()).matches(mEntity.getMeta().getColor())
                            && is(template.getPreviewUrl())
                            .matches(mEntity.getScreenshots().getUrl());
                });

        verify(mRepository).fetchTemplates();
        verify(mRepository).getTemplate(anyString());

        mUseCase.dispose();
        assertThat(mDisposable.isDisposed(), is(true));
    }
}