package com.test.templatechooser.presentation.templates;

import com.test.templatechooser.BaseTest;
import com.test.templatechooser.domain.TemplateRepository;
import com.test.templatechooser.domain.usecase.FetchTemplates;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemplatesPresenterTest extends BaseTest {

    private TemplatesContract.Presenter mPresenter;
    private CompositeDisposable mDisposable;

    @Mock
    private TemplatesContract.View mView;

    @Mock
    private TemplateRepository mRepository;

    @Before
    public void setUp() {
        when(mRepository.fetchTemplates()).thenReturn(Single.just(new ArrayList<>()));

        Scheduler scheduler = Schedulers.trampoline();
        mDisposable = new CompositeDisposable();
        FetchTemplates useCase = new FetchTemplates(scheduler, scheduler, mDisposable, mRepository);

        mPresenter = new TemplatesPresenter(useCase);
        mPresenter.setView(mView);
    }

    @Test
    public void fetchTemplates() {
        mPresenter.fetchTemplates();

        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).loadTemplates(anyList());
        verify(mView, never()).showError(anyString());
        verify(mRepository).fetchTemplates();

        mPresenter.destroy();
        assertThat(mDisposable.isDisposed(), is(true));
    }

    @Test
    public void fetchTemplates_withError() {
        when(mRepository.fetchTemplates()).thenReturn(Single.error(new Throwable("ERROR")));

        mPresenter.fetchTemplates();

        verify(mView).showLoading();
        verify(mView, never()).loadTemplates(anyList());
        verify(mView).showError("ERROR");
    }

}