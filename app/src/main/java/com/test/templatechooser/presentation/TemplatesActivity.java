package com.test.templatechooser.presentation;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.test.templatechooser.R;
import com.test.templatechooser.models.Template;
import com.test.templatechooser.presentation.adapter.TemplatesAdapter;
import com.test.templatechooser.presentation.decoration.TemplatesDecoration;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class TemplatesActivity extends AppCompatActivity
        implements TemplatesContract.View, TemplatesAdapter.OnTemplateListener {

    private static final String PRESENTER_STATE = "presenter_state";

    @Inject
    public TemplatesContract.Presenter mPresenter;

    @BindView(R.id.rvTemplates) RecyclerView mRvTemplates;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        ButterKnife.bind(this);
        setUpRecyclerView();

        if (savedInstanceState != null) {
            restoreViewState(savedInstanceState);
        }

        mPresenter.setView(this);
        mPresenter.loadTemplates();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        TemplatesContract.State state = mPresenter.getState();
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
    public void showTemplates(List<Template> templates) {
        TemplatesAdapter adapter = new TemplatesAdapter(templates);
        adapter.setOnTemplateListener(this);
        mRvTemplates.setAdapter(adapter);
    }

    @Override
    public void onTemplateChanged(Template template) {
        final int color = Color.parseColor(template.getColor());
        mRvTemplates.setBackgroundColor(color);
        getWindow().setStatusBarColor(color);
    }

    @Override
    public void onItemClicked(Template template) {
        final String message = String.format(
                Locale.getDefault(),
                getString(R.string.template_chosen),
                template.getName(),
                template.getColor());

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setUpRecyclerView() {
        final int size = getResources()
                .getDimensionPixelSize(R.dimen.recycler_view_divider_size);

        mRvTemplates.addItemDecoration(new TemplatesDecoration(size));
        mRvTemplates.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.HORIZONTAL, false));
        mRvTemplates.setHasFixedSize(false);

        new PagerSnapHelper()
                .attachToRecyclerView(mRvTemplates);
    }

    private void restoreViewState(Bundle state) {
        if (state.containsKey(PRESENTER_STATE)) {
            mPresenter.restoreState(state.getParcelable(PRESENTER_STATE));
        }
    }
}
