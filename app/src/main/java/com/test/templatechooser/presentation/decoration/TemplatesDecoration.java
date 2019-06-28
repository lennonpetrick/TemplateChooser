package com.test.templatechooser.presentation.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TemplatesDecoration extends RecyclerView.ItemDecoration {

    private final int mDecorationSize;

    public TemplatesDecoration(int size) {
        mDecorationSize = size;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if(parent.getChildCount() == 0) {
            return;
        }

        outRect.right = mDecorationSize;
        outRect.left = mDecorationSize;
    }
}
