package com.test.templatechooser.presentation.templateview;

import com.test.templatechooser.BaseTest;
import com.test.templatechooser.data.entities.TemplateEntity;
import com.test.templatechooser.domain.TemplateRepository;
import com.test.templatechooser.domain.usecase.GetTemplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemplateViewPresenterTest extends BaseTest {

    private TemplateViewContract.Presenter mPresenter;
    private CompositeDisposable mDisposable;

    @Mock
    private TemplateViewContract.View mView;

    @Mock
    private TemplateRepository mRepository;

    @Before
    public void setUp() throws IOException {
        TemplateEntity entity = createObjectFromFile(
                "Template.json", TemplateEntity.class);

        when(mRepository.getTemplate(anyString())).thenReturn(Single.just(entity));

        Scheduler scheduler = Schedulers.trampoline();
        mDisposable = new CompositeDisposable();
        GetTemplate useCase = new GetTemplate(scheduler, scheduler, mDisposable, mRepository);

        mPresenter = new TemplateViewPresenter(useCase);
        mPresenter.setView(mView);
    }

    @Test
    public void getTemplate() {
        mPresenter.setUrl("");
        mPresenter.loadTemplate();

        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).displayTemplate(any());
        verify(mView, never()).showError(anyString());
        verify(mRepository).getTemplate(anyString());

        mPresenter.destroy();
        assertThat(mDisposable.isDisposed(), is(true));
    }

    @Test
    public void fetchTemplates_withError() {
        when(mRepository.getTemplate(anyString())).thenReturn(Single.error(new Throwable("ERROR")));

        mPresenter.setUrl("");
        mPresenter.loadTemplate();

        verify(mView).showLoading();
        verify(mView, never()).displayTemplate(any());
        verify(mView).showError("ERROR");
    }
}