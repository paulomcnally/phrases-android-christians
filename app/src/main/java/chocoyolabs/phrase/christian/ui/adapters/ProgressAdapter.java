package chocoyolabs.phrase.christian.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import chocoyolabs.phrase.christian.R;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {

    private int height;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ProgressBar progressBar;
        public RelativeLayout container;
        public ViewGroup parent;
        public ViewHolder(Context context, View view, ViewGroup parent) {
            super(view);
            this.container = (RelativeLayout) view.findViewById(R.id.progressBarContainer);
            this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            this.parent = parent;
        }
    }

    public ProgressAdapter(int height) {
        this.height = height;
    }

    @Override
    public ProgressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_progress_bar, parent, false);
        return new ViewHolder(parent.getContext(), view, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);

        holder.container.getLayoutParams().height = height;
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}