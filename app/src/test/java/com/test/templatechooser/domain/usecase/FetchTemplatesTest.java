package com.test.templatechooser.domain.usecase;

import com.test.templatechooser.BaseTest;
import com.test.templatechooser.domain.TemplateRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class FetchTemplatesTest extends BaseTest {

    private FetchTemplates mUseCase;
    private CompositeDisposable mDisposable;

    @Before
    public void setUp() {
        TemplateRepository repository = Mockito.mock(TemplateRepository.class);
        when(repository.fetchTemplates())
                .thenReturn(Single.just(new ArrayList<>()));

        Scheduler scheduler = Schedulers.trampoline();
        mDisposable = new CompositeDisposable();
        mUseCase = new FetchTemplates(scheduler, scheduler, mDisposable, repository);
    }

    @Test
    public void getTemplates() {
        mUseCase.execute(null)
                .test()
                .assertValueCount(1);

        mUseCase.dispose();
        assertThat(mDisposable.isDisposed(), is(true));
    }
}