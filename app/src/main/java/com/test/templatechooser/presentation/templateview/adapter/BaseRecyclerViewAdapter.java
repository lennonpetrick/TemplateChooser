package com.test.templatechooser.presentation.templateview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewAdapter<HV extends BaseRecyclerViewAdapter.ViewHolder, I>
        extends RecyclerView.Adapter<HV> {

    private OnItemListener<I> mListener;
    protected final List<I> mItems;

    public BaseRecyclerViewAdapter(List<I> items) {
        mItems = items;
    }

    public void setOnItemListener(OnItemListener<I> listener) {
        this.mListener = listener;
    }

    protected I getItem(int position) {
        return mItems.get(position);
    }

    @NonNull
    @Override
    public HV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getLayout(), parent, false);
        return createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HV holder, int position) {
        bind(holder, getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @LayoutRes
    abstract int getLayout();
    abstract HV createViewHolder(View view);
    abstract void bind(HV holder, I item);

    abstract class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClicked(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemListener<I> {
        void onItemClicked(I item);
    }
}
