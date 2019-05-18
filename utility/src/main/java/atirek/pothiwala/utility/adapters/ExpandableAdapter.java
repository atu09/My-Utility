package atirek.pothiwala.utility.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/**
 * Created by Atirek Pothiwala on 12/6/2016.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {

    public interface AdapterListener {
        View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

        Object getGroup(int groupPosition);

        int getGroupCount();

        View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

        Object getChild(int groupPosition, int childPosition);

        int getChildrenCount(int groupPosition);

    }

    private AdapterListener listener;

    public ExpandableAdapter(AdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public int getGroupCount() {
        return listener.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listener.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listener.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listener.getChild(groupPosition, childPosition);
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return listener.getGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return listener.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
