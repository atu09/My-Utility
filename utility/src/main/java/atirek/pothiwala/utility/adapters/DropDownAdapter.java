package atirek.pothiwala.utility.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

public class DropDownAdapter extends BaseAdapter implements SpinnerAdapter {

    public interface AdapterListener {

        View getDropDownView(int position, View view, ViewGroup viewGroup);

        int getCount();

        Object getItem(int position);

        View getView(int position, View view, ViewGroup viewGroup);

        int getItemViewType(int position);

        int getViewTypeCount();
    }

    private AdapterListener listener;

    public DropDownAdapter(AdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return listener.getDropDownView(i, view, viewGroup);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return listener.getCount();
    }

    @Override
    public Object getItem(int i) {
        return listener.getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return listener.getView(i, view, viewGroup);
    }

    @Override
    public int getItemViewType(int i) {
        return listener.getItemViewType(i);
    }

    @Override
    public int getViewTypeCount() {
        return listener.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
