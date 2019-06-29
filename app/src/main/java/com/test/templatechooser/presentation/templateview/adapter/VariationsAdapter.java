package com.test.templatechooser.presentation.templateview.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.test.templatechooser.R;
import com.test.templatechooser.domain.models.Template;
import com.test.templatechooser.utils.ColorUtils;

import butterknife.BindView;

public class VariationsAdapter
        extends BaseRecyclerViewAdapter<VariationsAdapter.ViewHolder, Template> {

    private Template mCurrentTemplate;

    public VariationsAdapter(Template template) {
        super(template.getVariations());
        mCurrentTemplate = template;
    }

    @Override
    int getLayout() {
        return R.layout.layout_variation_adapter;
    }

    @Override
    ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    void bind(ViewHolder holder, Template item) {
        holder.bind(item);
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder {

        @BindView(R.id.variationCircleView) View mView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void bind(Template template) {
            mView.setSelected(template.getId() == mCurrentTemplate.getId());
            mView.setBackgroundTintList(ColorUtils
                    .parseToColorStateList(template.getColor()));
        }
    }

}
