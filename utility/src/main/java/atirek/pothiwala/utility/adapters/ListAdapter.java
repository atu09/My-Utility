package atirek.pothiwala.utility.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Atirek Pothiwala on 12/6/2016.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface AdapterListener {
        RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

        void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

        int getItemCount();

        int getItemViewType(int position);

    }

    private AdapterListener listener;

    public ListAdapter(AdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return listener.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listener.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return listener.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return listener.getItemViewType(position);
    }

}
