package com.test.templatechooser.presentation.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.test.templatechooser.R;
import com.test.templatechooser.domain.models.Template;

import java.util.List;

import butterknife.BindView;

public class TemplatesAdapter
        extends BaseRecyclerViewAdapter<TemplatesAdapter.ViewHolder, Template> {

    private OnTemplateListener mListener;

    public TemplatesAdapter(List<Template> templates) {
        super(templates);
    }

    @Override
    int getLayout() {
        return R.layout.layout_template_adapter;
    }

    @Override
    ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    void bind(ViewHolder holder, Template item) {
        holder.bind(item);
    }

    public void setOnTemplateListener(OnTemplateListener listener) {
        setOnItemListener(listener);
        this.mListener = listener;
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder {

        @BindView(R.id.ivPreview) ImageView mIvPreview;
        @BindView(R.id.tvTemplateName) TextView mTvTemplateName;
        @BindView(R.id.rvVariations) RecyclerView mRvVariations;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            setUpRecyclerView();
        }

        private void setUpRecyclerView() {
            mRvVariations.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    RecyclerView.HORIZONTAL, false));
            mRvVariations.setHasFixedSize(true);
            mRvVariations.addOnItemTouchListener(
                    new RecyclerView.SimpleOnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv,
                                                     @NonNull MotionEvent e) {
                    if (e.getAction() == MotionEvent.ACTION_MOVE) {
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }
            });
        }

        private void bind(Template template) {
            if (mListener != null) {
                mListener.onTemplateChanged(getItem(getAdapterPosition()));
            }

            mTvTemplateName.setText(template.getName());

            Picasso.get()
                    .load(template.getPreviewUrl())
                    .into(mIvPreview);

            VariationsAdapter adapter = new VariationsAdapter(template);
            adapter.setOnItemListener(item -> {
                final int currentPosition = getAdapterPosition();
                mItems.set(currentPosition, item);
                notifyItemChanged(currentPosition);
            });
            mRvVariations.setAdapter(adapter);
        }
    }

    public interface OnTemplateListener
            extends BaseRecyclerViewAdapter.OnItemListener<Template> {
        void onTemplateChanged(Template template);
    }
}
