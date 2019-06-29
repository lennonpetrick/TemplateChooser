package com.test.templatechooser.presentation.templateview;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.test.templatechooser.R;
import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.presentation.base.BaseContract;
import com.test.templatechooser.presentation.base.FragmentLifecycle;
import com.test.templatechooser.presentation.templateview.adapter.VariationsAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class TemplateViewFragment extends Fragment
        implements FragmentLifecycle, TemplateViewContract.View {

    private static final String ARG_TEMPLATE_URL = "template_url";
    private static final String PRESENTER_STATE = "presenter_state";

    @Inject TemplateViewContract.Presenter mPresenter;

    @BindView(R.id.ivPreview) ImageView mIvPreview;
    @BindView(R.id.tvTemplateName) TextView mTvTemplateName;
    @BindView(R.id.rvVariations) RecyclerView mRvVariations;
    @BindView(R.id.previewProgress) ProgressBar mPreviewProgress;

    private Template mCurrentTemplate;
    private String mTemplateUrl;
    private OnTemplateListener mListener;

    public static TemplateViewFragment newInstance(String templateUrl) {
        TemplateViewFragment fragment = new TemplateViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEMPLATE_URL, templateUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof OnTemplateListener) {
            mListener = (OnTemplateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTemplateListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTemplateUrl = bundle.getString(ARG_TEMPLATE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        setUpViews();
        setUpRecyclerView();

        if (savedInstanceState != null) {
            restoreViewState(savedInstanceState);
        }

        mPresenter.setView(this);
        mPresenter.getTemplate(mTemplateUrl);
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
    public void onVisible() {
        notifyTemplateChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideLoading();
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPresenter = null;
        mCurrentTemplate = null;
        mTemplateUrl = null;
    }

    @Override
    public void showLoading() {
        mPreviewProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPreviewProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        hideLoading();

        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.error_default);
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayTemplate(@NonNull Template template) {
        mCurrentTemplate = template;
        if (isMenuVisible()) {
            notifyTemplateChanged();
        }

        mTvTemplateName.setText(template.getName());
        loadImagePreview(template.getPreviewUrl());
        loadVariations(template);
    }

    private void setUpViews() {
        mIvPreview.setOnClickListener(v -> {
            if (mListener != null
                    && mCurrentTemplate != null) {
                mListener.onTemplateChosen(mCurrentTemplate);
            }
        });
    }

    private void setUpRecyclerView() {
        mRvVariations.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        mRvVariations.setHasFixedSize(true);
    }

    private void loadImagePreview(String url) {
        showLoading();
        Picasso.get()
                .load(url)
                .placeholder(mIvPreview.getDrawable())
                .into(mIvPreview, new Callback() {
                    @Override
                    public void onSuccess() {
                        hideLoading();
                    }

                    @Override
                    public void onError(Exception e) {
                        showError(e.getMessage());
                    }
                });
    }

    private void loadVariations(Template template) {
        VariationsAdapter adapter = new VariationsAdapter(template);
        adapter.setOnItemListener(item -> {
            displayTemplate(item);
            notifyTemplateChanged();
        });
        mRvVariations.setAdapter(adapter);
    }

    private void restoreViewState(Bundle state) {
        if (state.containsKey(PRESENTER_STATE)) {
            mPresenter.restoreState(state.getParcelable(PRESENTER_STATE));
        }
    }

    private void notifyTemplateChanged() {
        if (mListener != null
                && mCurrentTemplate != null) {
            mListener.onTemplateChanged(mCurrentTemplate);
        }
    }

    public interface OnTemplateListener {
        void onTemplateChosen(Template template);
        void onTemplateChanged(Template template);
    }
}
