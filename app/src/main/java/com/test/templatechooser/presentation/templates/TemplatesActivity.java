package com.test.templatechooser.presentation.templates;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.test.templatechooser.R;
import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.presentation.base.BaseContract;
import com.test.templatechooser.presentation.templates.adapter.ViewPagerAdapter;
import com.test.templatechooser.presentation.base.FragmentLifecycle;
import com.test.templatechooser.presentation.templateview.TemplateViewFragment;
import com.test.templatechooser.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class TemplatesActivity extends AppCompatActivity
        implements TemplatesContract.View, TemplateViewFragment.OnTemplateListener {

    private static final String PRESENTER_STATE = "presenter_state";

    @Inject TemplatesContract.Presenter mPresenter;

    @BindView(R.id.templatesPager) ViewPager mTemplatesPager;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        ButterKnife.bind(this);
        setUpViewPager();

        if (savedInstanceState != null) {
            restoreViewState(savedInstanceState);
        }

        mPresenter.setView(this);
        mPresenter.fetchTemplates();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        BaseContract.State state = mPresenter.getState();
        if (state != null) {
            outState.putParcelable(PRESENTER_STATE, state);
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mPresenter.destroy();
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        hideLoading();

        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.progress_please_wait));
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void showError(String message) {
        hideLoading();

        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.error_default);
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadTemplates(@NonNull List<String> templatesUrls) {
        List<Fragment> fragments = new ArrayList<>();
        for (String url : templatesUrls) {
            TemplateViewFragment fragment = TemplateViewFragment.newInstance(url);
            fragments.add(fragment);
        }

        mTemplatesPager.setAdapter(new ViewPagerAdapter(
                getSupportFragmentManager(), fragments));
    }

    @Override
    public void onTemplateChanged(Template template) {
        final int color = ColorUtils.parseColor(template.getColor());
        mTemplatesPager.setBackgroundColor(color);
        getWindow().setStatusBarColor(ColorUtils.darkenColor(color));
    }

    @Override
    public void onTemplateChosen(Template template) {
        final String message = String.format(
                Locale.getDefault(),
                getString(R.string.template_chosen),
                template.getName(),
                template.getColor());

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setUpViewPager() {
        final int size = getResources()
                .getDimensionPixelSize(R.dimen.view_pager_divider_size);
        mTemplatesPager.setPageMargin(size);

        mTemplatesPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ViewPagerAdapter adapter = (ViewPagerAdapter) mTemplatesPager.getAdapter();
                if (adapter != null) {
                    Fragment fragment = adapter.getItem(position);
                    if (fragment instanceof FragmentLifecycle) {
                        ((FragmentLifecycle) fragment).onVisible();
                    }
                }
            }
        });
    }

    private void restoreViewState(Bundle state) {
        if (state.containsKey(PRESENTER_STATE)) {
            mPresenter.restoreState(state.getParcelable(PRESENTER_STATE));
        }
    }
}
