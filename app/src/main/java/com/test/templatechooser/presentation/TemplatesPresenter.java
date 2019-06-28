package com.test.templatechooser.presentation;

import javax.inject.Inject;

public class TemplatesPresenter implements TemplatesContract.Presenter {

    private TemplatesContract.View mView;

    @Inject
    public TemplatesPresenter() {
    }

    @Override
    public void setView(TemplatesContract.View view) {
        mView = view;
    }

    @Override
    public TemplatesContract.State getState() {
        return null;
    }

    @Override
    public void restoreState(TemplatesContract.State state) {

    }

    @Override
    public void destroy() {
        mView = null;
    }

    @Override
    public void loadTemplates() {
    }
}
