package com.test.templatechooser.domain.usecase;

import com.test.templatechooser.BaseTest;
import com.test.templatechooser.data.entities.TemplateEntity;
import com.test.templatechooser.domain.TemplateRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GetTemplateTest extends BaseTest {

    private GetTemplate mUseCase;
    private CompositeDisposable mDisposable;
    private TemplateEntity mEntity;

    @Before
    public void setUp() throws Exception {
        mEntity = createObjectFromFile(
                "Template.json", TemplateEntity.class);

        TemplateRepository repository = Mockito.mock(TemplateRepository.class);
        when(repository.getTemplate(anyString()))
                .thenReturn(Single.just(mEntity));

        Scheduler scheduler = Schedulers.trampoline();
        mDisposable = new CompositeDisposable();
        mUseCase = new GetTemplate(scheduler, scheduler, mDisposable, repository);
    }

    @Test
    public void getTemplate() {
        mUseCase.execute(GetTemplate.Params.forUrl(""))
                .test()
                .assertValue(template ->
                        is(template.getId()).matches(mEntity.getId())
                                && is(template.getName()).matches(mEntity.getName())
                                && is(template.getColor()).matches(mEntity.getMeta().getColor())
                                && is(template.getPreviewUrl())
                                .matches(mEntity.getScreenshots().getUrl()));

        mUseCase.dispose();
        assertThat(mDisposable.isDisposed(), is(true));
    }
}